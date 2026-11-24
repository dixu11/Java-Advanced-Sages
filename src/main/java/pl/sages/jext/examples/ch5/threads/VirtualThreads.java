package pl.sages.jext.examples.ch5.threads;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Slajdy 143, 152: start wątku wirtualnego i {@code synchronized} z blokowaniem w środku.
 * <p>
 * Slajd 152 (punkt 1): do Javy 23 {@code synchronized} przypinał wątek wirtualny do wątku nośnego (pinning),
 * od Javy 24 (JEP 491) już nie. Przykład uruchamia 1000 wątków wirtualnych, każdy śpi 100 ms w bloku
 * {@code synchronized} na własnym monitorze: na JDK 25 całość trwa ok. 0,1-0,3 s, a na JDK 21 trwałaby
 * ok. (1000 / liczba rdzeni) x 100 ms. Flaga {@code -Djdk.tracePinnedThreads} ze slajdu nie działa od JDK 24;
 * przypięcia pokazuje zdarzenie JFR {@code jdk.VirtualThreadPinned} (dziennik błędów).
 * <p>
 * Continuation ze slajdu 149 to API wewnętrzne: osobny skrypt {@code scripts/ContinuationDemo.java}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch5.threads.VirtualThreads}
 */
public class VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        // --- slajd 143 ---
        var vthread = Thread.ofVirtual().start(() -> {
            System.out.println("Virtual Thread details: " + Thread.currentThread());
        });
        vthread.join();

        // --- slajd 152: synchronized + sleep ---
        int cores = Runtime.getRuntime().availableProcessors();
        long start = System.nanoTime();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var monitors = new ArrayList<Object>();
            for (int i = 0; i < 1000; i++) {
                var monitor = new Object();
                monitors.add(monitor);
                executor.submit(() -> {
                    aMethodThatPinTheCarrierThreadInJava21(monitor);
                    return null;
                });
            }
        }
        long ms = (System.nanoTime() - start) / 1_000_000;
        System.out.println("113: 1000 x sleep(100 ms) w synchronized: " + ms + " ms (rdzeni: " + cores
                + "; z przypinaniem jak w Javie 21: ok. " + (1000 / cores) * 100 + " ms)");
    }

    // nazwa jak na slajdzie; od Javy 24 metoda już nie przypina wątku nośnego
    static void aMethodThatPinTheCarrierThreadInJava21(Object monitor) throws InterruptedException {
        synchronized (monitor) {
            Thread.sleep(Duration.ofMillis(100));
        }
    }
}
