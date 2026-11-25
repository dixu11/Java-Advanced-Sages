package pl.sages.jext.ch5.threads;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Ćwiczenie 9. Sprawdzenie: test {@code Zad09ThreadsTest}.
 *
 * Ćwiczenie 9 (slajdy 140-143, 146-147, 149, 152): 10 000 zadań na puli wątków platformowych i na wątkach wirtualnych.
 * Każde zadanie "czeka na I/O" (Thread.sleep), a potem zwraca, czy działało na wątku wirtualnym.
 */
public class Threads {

    /** a) Zwróć pulę 100 zwykłych (platformowych) wątków: Executors.newFixedThreadPool. */
    public static ExecutorService platformPool() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** b) Zwróć executor, który dla każdego zadania tworzy nowy wątek WIRTUALNY. */
    public static ExecutorService virtualPool() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * c) Wyślij do executora taskCount zadań. Każde zadanie: Thread.sleep(delay), potem zwraca
     * Thread.currentThread().isVirtual(). Zbierz Future z submit, a na końcu policz,
     * ile zadań działało na wątku wirtualnym (future.get()). Executor zamknij try-with-resources
     * (ExecutorService jest AutoCloseable od Javy 19).
     *
     * @return liczba zadań wykonanych na wątkach wirtualnych
     */
    public static long runTasks(ExecutorService executor, int taskCount, Duration delay)
            throws InterruptedException, ExecutionException {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Gotowe: porównanie czasów. Uruchom: mvn compile exec:exec -Dmain=pl.sages.jext.ch5.threads.Threads */
    public static void main(String[] args) throws Exception {
        int taskCount = 10_000;
        Duration delay = Duration.ofMillis(100);

        long start = System.nanoTime();
        runTasks(platformPool(), taskCount, delay);
        System.out.printf("Pula 100 wątków platformowych: %d ms%n", (System.nanoTime() - start) / 1_000_000);

        start = System.nanoTime();
        runTasks(virtualPool(), taskCount, delay);
        System.out.printf("Wątki wirtualne:               %d ms%n", (System.nanoTime() - start) / 1_000_000);
    }
}
