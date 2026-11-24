package pl.sages.jext.ch5.threads;

import pl.sages.jext.ch5.threads.Services.Profile;
import pl.sages.jext.ch5.threads.Services.User;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Ćwiczenie 10 (slajdy 150-154): kilka zadań naraz na wątkach wirtualnych i ScopedValue,
 * tylko stabilnym API Javy 25. StructuredTaskScope ze slajdów 153-154 jest w Javie 25 jeszcze
 * w wersji preview, więc to samo robimy tu zwykłym ExecutorService.
 */
public class StructuredTasks {

    /**
     * a) Pobierz użytkownika i jego zamówienia RÓWNOLEGLE (Services.fetchUser
     * i Services.fetchOrders, każde ok. 300 ms), razem ma to trwać ok. 300 ms, a nie 600.
     *
     * Szablon:
     * try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
     *     Future<...> u = executor.submit(() -> ...);
     *     Future<...> z = executor.submit(() -> ...);
     *     return new Profile(u.get(), z.get());   // get() czeka na wynik; błąd zadania wraca jako ExecutionException
     * }
     */
    public static Profile fetchProfile(long id) throws InterruptedException, ExecutionException {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * b) Zapytaj trzy serwery lustrzane naraz i zwróć odpowiedź tego, który odpowie PIERWSZY;
     * pozostałe mają zostać anulowane. Zrobi to za ciebie executor.invokeAny(lista zadań).
     * Czasy odpowiedzi: "A" 500 ms, "B" 100 ms, "C" 300 ms (Services.fetchFromServer).
     */
    public static String fastestServer() throws InterruptedException, ExecutionException {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // ---------------------------------------------------------------------------------------------
    // c) ScopedValue: następca ThreadLocal dla wątków wirtualnych (brak slajdu, patrz README).

    /** "Kto teraz obsługuje żądanie": ustawiane tylko na czas wywołania, niezmienne w jego trakcie. */
    public static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();

    /** Gotowe: kod głęboko w aplikacji czyta wartość bez przekazywania jej parametrem. */
    static String greeting() {
        return "Cześć, " + (CURRENT_USER.isBound() ? CURRENT_USER.get() : "nieznajomy") + "!";
    }

    /**
     * c) Wywołaj greeting() tak, żeby w jego trakcie CURRENT_USER miał wartość user.
     * Jedna linijka: ScopedValue.where(CURRENT_USER, user).call(...).
     * Po wyjściu z call wartość znika sama (nie trzeba sprzątać jak w ThreadLocal.remove()).
     */
    public static String handleRequest(String user) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
