package pl.sages.lecture;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demo: podstawy wątków wirtualnych na przykładzie wysyłki SMS
 * (nie serwer i kelnerzy ze slajdów ani 10 000 zadań, profil i serwery z ćwiczeń).
 * Każdy krok to osobna metoda; zakomentuj w main te, których akurat nie pokazujesz.
 */
public class VirtualThreadsDemo {

    public static void main(String[] args) throws Exception {
        step1_createVirtualThread();
        step2_carrierChangesAfterWaiting();
        step3_manyThreadsCheap();
        step4_executorPerTask();
        step5_semaphoreLimitsGateway();
    }

    // 1. Jak uruchomić wątek wirtualny; po "@" w nazwie widać wątek nośny (carrier).
    static void step1_createVirtualThread() throws InterruptedException {
        Thread a = Thread.ofVirtual().name("sms-1").start(() -> System.out.println("1. " + Thread.currentThread()));
        Thread b = Thread.startVirtualThread(() -> System.out.println("1. wirtualny? " + Thread.currentThread().isVirtual()));
        Thread c = Thread.ofPlatform().start(() -> System.out.println("1. platformowy: " + Thread.currentThread()));
        a.join();
        b.join();
        c.join();
        // VirtualThread[#31,sms-1]/runnable@ForkJoinPool-1-worker-1
    }

    // 2. Po czekaniu wątek wirtualny bywa wznowiony na INNYM carrierze: dowód, że carrier był oddany.
    static void step2_carrierChangesAfterWaiting() throws InterruptedException {
        Runnable sms = () -> {
            String before = carrier();
            sleep(100);                                   // "czekam na odpowiedź bramki SMS"
            System.out.println("2. przed: " + before + "  po: " + carrier());
        };
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = Thread.ofVirtual().start(sms);
        }
        for (Thread t : threads) {
            t.join();
        }
    }

    // 3. 100 000 wątków naraz: dla wirtualnych to drobiazg, platformowych tyle system zwykle nie da.
    static void step3_manyThreadsCheap() throws InterruptedException {
        Instant start = Instant.now();
        Thread[] threads = new Thread[100_000];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = Thread.ofVirtual().start(() -> sleep(1000));
        }
        for (Thread t : threads) {
            t.join();
        }
        System.out.println("3. 100 000 wątków po 1 s czekania: " + millisSince(start) + " ms");
    }

    // 4. W praktyce nie tworzysz wątków ręcznie: executor "wątek na zadanie" w try-with-resources.
    static void step4_executorPerTask() {
        AtomicInteger sent = new AtomicInteger();
        Instant start = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(() -> {
                    sleep(200);                           // wysłanie jednego SMS trwa 200 ms
                    sent.incrementAndGet();
                });
            }
        }                                                 // close() czeka na wszystkie zadania
        System.out.println("4. wysłano " + sent + " SMS w " + millisSince(start) + " ms (po kolei byłoby 200 s)");
    }

    // 5. Bramka przyjmuje max 10 połączeń naraz: limit robi Semaphore, a nie mała pula wątków.
    static void step5_semaphoreLimitsGateway() {
        Semaphore gateway = new Semaphore(10);
        AtomicInteger inGateway = new AtomicInteger();
        AtomicInteger maxSeen = new AtomicInteger();
        Instant start = Instant.now();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    gateway.acquire();                    // czekam na wolne miejsce w bramce
                    try {
                        maxSeen.accumulateAndGet(inGateway.incrementAndGet(), Math::max);
                        sleep(100);
                        inGateway.decrementAndGet();
                    } finally {
                        gateway.release();
                    }
                    return null;
                });
            }
        }
        System.out.println("5. 100 SMS przez bramkę na 10 miejsc: " + millisSince(start)
                + " ms, najwięcej naraz: " + maxSeen);    // ok. 1000 ms, najwięcej naraz: 10
    }

    private static String carrier() {
        String name = Thread.currentThread().toString();
        return name.substring(name.indexOf('@') + 1);
    }

    private static long millisSince(Instant start) {
        return Duration.between(start, Instant.now()).toMillis();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
