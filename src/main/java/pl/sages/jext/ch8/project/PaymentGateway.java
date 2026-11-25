package pl.sages.jext.ch8.project;

import java.time.Duration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ćwiczenie 13. Sprawdzenie: test {@code Zad13MiniShopTest}.
 *
 * Gotowa zaślepka zewnętrznego API do etapu c. Zadanie: wywołuj ją z MiniShop.process
 * tylko dla zaakceptowanych zamówień. Ograniczenie i finally należą do ćwiczenia.
 * Nie łączymy się z prawdziwą bramką. Domyślnie jedna płatność trwa 50 ms, limit to 20.
 */
public final class PaymentGateway {
    private final Duration delay;
    private final Semaphore permits;
    private final AtomicInteger active = new AtomicInteger();
    private final AtomicInteger maximum = new AtomicInteger();
    private final AtomicInteger calls = new AtomicInteger();
    private final AtomicInteger virtualCalls = new AtomicInteger();

    public PaymentGateway() { this(Duration.ofMillis(50), 20); }

    public PaymentGateway(Duration delay, int limit) {
        if (delay.isNegative() || limit <= 0) throw new IllegalArgumentException("Niepoprawne parametry bramki");
        this.delay = delay;
        this.permits = new Semaphore(limit);
    }

    public Semaphore permits() { return permits; }
    public int maxConcurrentCalls() { return maximum.get(); }
    public int calls() { return calls.get(); }
    public int virtualCalls() { return virtualCalls.get(); }

    public void pay(Order order) throws InterruptedException {
        int concurrent = active.incrementAndGet();
        maximum.accumulateAndGet(concurrent, Math::max);
        calls.incrementAndGet();
        if (Thread.currentThread().isVirtual()) virtualCalls.incrementAndGet();
        try {
            Thread.sleep(delay);
        } finally {
            active.decrementAndGet();
        }
    }
}
