package pl.sages.jext.ch5.threads;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gotowe "zdalne usługi" do ćwiczenia 10 (nie zmieniaj). Każde wywołanie trwa ok. 300 ms.
 * Id ujemne oznacza błąd usługi.
 */
public class Services {

    public record User(long id, String name) {
    }

    public record Profile(User user, List<String> orders) {
    }

    /** Licznik przerwanych (anulowanych) wywołań: test sprawdza, czy wolniejsze zapytania zostały anulowane. */
    public static final AtomicInteger CANCELLED = new AtomicInteger();

    public static User fetchUser(long id) throws InterruptedException {
        pause(Duration.ofMillis(300));
        if (id < 0) {
            throw new IllegalStateException("Usługa użytkowników nie działa");
        }
        return new User(id, "Klient " + id);
    }

    public static List<String> fetchOrders(long id) throws InterruptedException {
        if (id < 0) {
            throw new IllegalStateException("Usługa zamówień nie działa");
        }
        pause(Duration.ofMillis(300));
        return List.of("Z-" + id + "-1", "Z-" + id + "-2");
    }

    /** Serwer lustrzany odpowiadający po podanym czasie. */
    public static String fetchFromServer(String name, Duration delay) throws InterruptedException {
        pause(delay);
        return name;
    }

    private static void pause(Duration delay) throws InterruptedException {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            CANCELLED.incrementAndGet();
            throw e;
        }
    }
}
