package pl.sages.jext.examples.ch2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Slajdy 66-67: pytanie kontrolne „na ilu wątkach?”, czyli {@code @Async} i wywołanie przez {@code this}.
 * <p>
 * Bez Springa: własne {@code @Async} i proxy JDK, które przenosi wywołanie oznaczonej metody na pulę wątków,
 * tak jak robi to Spring. {@code importAll} woła {@code importFile} przez {@code this}, więc omija proxy:
 * wszystko idzie na wątku main, po kolei. Poprawka ze slajdu 67: importer w osobnym obiekcie, wołany przez proxy.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.AsyncSelfInvocationTrap}
 */
public class AsyncSelfInvocationTrap {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Async {}

    interface ImportService {
        @Async
        void importFile(String file);

        void importAll(List<String> files);
    }

    static class ImportServiceImpl implements ImportService {
        public void importFile(String file) { heavyWork(file); }

        public void importAll(List<String> files) {
            for (var file : files) {
                importFile(file); // w praktyce this.importFile(file): proxy pominięte
            }
        }
    }

    // --- poprawka ze slajdu 67: osobny obiekt, wołany przez wstrzykniętą referencję (proxy) ---
    interface FileImporter {
        @Async
        void importFile(String file);
    }

    record FixedImportService(FileImporter importer) {
        void importAll(List<String> files) {
            for (var file : files) {
                importer.importFile(file); // przez proxy: @Async działa
            }
        }
    }

    static final ExecutorService POOL = Executors.newFixedThreadPool(5);

    /** Proxy, które czyta @Async jak Spring: oznaczoną metodę wysyła do puli i od razu wraca. */
    @SuppressWarnings("unchecked")
    static <T> T asyncProxy(Class<T> type, Object target) {
        InvocationHandler handler = (_, method, arguments) -> {
            if (method.isAnnotationPresent(Async.class)) {
                POOL.submit(() -> {
                    try {
                        return method.invoke(target, arguments);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getCause());
                    }
                });
                return null;
            }
            try {
                return method.invoke(target, arguments);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        };
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, handler);
    }

    static void heavyWork(String file) {
        System.out.println("  import " + file + " na wątku " + Thread.currentThread().getName());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var files = List.of("a.csv", "b.csv", "c.csv", "d.csv", "e.csv");

        System.out.println("slajd 66: importAll przez proxy, importFile przez this");
        ImportService service = asyncProxy(ImportService.class, new ImportServiceImpl());
        long start = System.nanoTime();
        service.importAll(files);
        System.out.println("importAll wrócił po " + (System.nanoTime() - start) / 1_000_000 + " ms (5 x 300 ms po kolei)");

        System.out.println("slajd 67: importer w osobnym obiekcie, wołany przez proxy");
        var fixed = new FixedImportService(asyncProxy(FileImporter.class, new FileImporter() {
            public void importFile(String file) { heavyWork(file); }
        }));
        start = System.nanoTime();
        fixed.importAll(files);
        System.out.println("importAll wrócił po " + (System.nanoTime() - start) / 1_000_000 + " ms (zadania poszły do puli)");

        POOL.shutdown();
        POOL.awaitTermination(5, TimeUnit.SECONDS);
    }
}
