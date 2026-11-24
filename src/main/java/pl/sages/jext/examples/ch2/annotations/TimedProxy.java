package pl.sages.jext.examples.ch2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Slajdy 64-65 (oraz slajd 55): aspekt pomiaru czasu przez adnotację {@code @Timed} i proxy JDK.
 * <p>
 * Pokazuje: wywołanie przez proxy (pomiar jest), wywołanie wprost na obiekcie docelowym (pomiaru nie ma),
 * {@code @Timed} na klasie implementującej (handler ze slajdu 65 go nie widzi) i handler ze slajdu 55, który
 * sprawdza też implementację, oraz propagację wyjątku przez {@code catch} w handlerze.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.TimedProxy}
 */
public class TimedProxy {

    // --- slajd 64: 1. definicja adnotacji, 2. użycie na interfejsie ---
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Timed {}

    interface Catalog {
        @Timed
        String load(String id);
    }

    // --- slajd 65: handler wywołania ---
    record TimedHandler(Object target) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
            boolean timed = method.isAnnotationPresent(Timed.class);
            long start = System.nanoTime();
            try {
                return method.invoke(target, arguments);
            } catch (InvocationTargetException e) {
                throw e.getCause(); // propagacja oryginalnego wyjątku
            } finally {
                if (timed) {
                    long elapsed = System.nanoTime() - start;
                    System.out.println(method.getName() + ": " + elapsed + " ns");
                }
            }
        }
    }

    // --- slajd 55: @Timed na implementacji ---
    interface Repo {
        String find(String id);
    }

    static class RepoImpl implements Repo {
        @Timed
        public String find(String id) { return "repo:" + id; } // handler ze slajdu 65 tego NIE zobaczy
    }

    /** Slajd 55: handler, który sprawdza adnotację na interfejsie i na implementacji. */
    record TimedHandlerWithImplementation(Object target) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
            boolean timed = method.isAnnotationPresent(Timed.class)                         // interfejs
                    || target.getClass().getMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(Timed.class);                                       // implementacja
            long start = System.nanoTime();
            try {
                return method.invoke(target, arguments);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            } finally {
                if (timed) {
                    System.out.println(method.getName() + ": " + (System.nanoTime() - start) + " ns");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, handler);
    }

    public static void main(String[] args) {
        // --- slajd 64: 3. obiekt docelowy i utworzenie proxy ---
        Catalog target = id -> "item:" + id;
        Catalog proxy = (Catalog)
                Proxy.newProxyInstance(
                        Catalog.class.getClassLoader(),
                        new Class<?>[] {Catalog.class},
                        new TimedHandler(target));
        String result = proxy.load("42");
        System.out.println("proxy.load(\"42\") = " + result + "   <- wynik + pomiar czasu linię wyżej");
        System.out.println("target.load(\"42\") = " + target.load("42") + "  <- prosto do celu: brak pomiaru");

        // --- slajd 55 ---
        Repo plain = proxy(Repo.class, new TimedHandler(new RepoImpl()));
        System.out.println("@Timed na implementacji, handler ze slajdu 65: " + plain.find("7") + " (brak pomiaru)");
        Repo fixed = proxy(Repo.class, new TimedHandlerWithImplementation(new RepoImpl()));
        System.out.println("handler ze slajdu 55: " + fixed.find("7"));

        // --- slajd 65: finally mierzy także przy wyjątku, catch rozpakowuje InvocationTargetException ---
        Catalog broken = proxy(Catalog.class, new TimedHandler((Catalog) id -> {
            throw new IllegalStateException("brak pozycji " + id);
        }));
        try {
            broken.load("13");
        } catch (IllegalStateException e) {
            System.out.println("złapany oryginalny wyjątek: " + e.getMessage());
        }
    }
}
