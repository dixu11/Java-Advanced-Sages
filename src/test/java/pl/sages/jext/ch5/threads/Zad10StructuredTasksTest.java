package pl.sages.jext.ch5.threads;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * Ćwiczenie 10 (slajdy 150-154): zadania równoległe i ScopedValue (ScopedValue: slajdy 150-151).
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch5.threads.StructuredTasks}
 * (src/main/java/pl/sages/jext/ch5/threads/StructuredTasks.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch5.threads.StructuredTasks} (src/main/java/pl/sages/jext/ch5/threads/StructuredTasks.java)</li>
 * <li>{@code pl.sages.jext.ch5.threads.Services} (src/main/java/pl/sages/jext/ch5/threads/Services.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 10: zadania równolegle na wątkach wirtualnych i ScopedValue")
class Zad10StructuredTasksTest {

    @Test
    @DisplayName("a) profil pobrany równolegle (ok. 300 ms, nie 600)")
    void profile() {
        Services.Profile profile = assertTimeoutPreemptively(Duration.ofMillis(550),
                () -> StructuredTasks.fetchProfile(7),
                "czy oba zadania idą do executora, zanim wywołasz get()?");
        assertEquals("Klient 7", profile.user().name());
        assertEquals(List.of("Z-7-1", "Z-7-2"), profile.orders());
    }

    @Test
    @DisplayName("a) błąd usługi wraca jako ExecutionException z przyczyną")
    void failure() {
        var exception = assertThrows(ExecutionException.class, () -> StructuredTasks.fetchProfile(-1));
        assertInstanceOf(IllegalStateException.class, exception.getCause());
    }

    @Test
    @DisplayName("b) pierwszy serwer, który odpowie, wygrywa")
    void fastest() {
        int cancelledBefore = Services.CANCELLED.get();
        String result = assertTimeoutPreemptively(Duration.ofMillis(400), StructuredTasks::fastestServer);
        assertEquals("B", result);
        assertEquals(cancelledBefore + 2, Services.CANCELLED.get(), "wolniejsze serwery A i C miały zostać anulowane");
    }

    @Test
    @DisplayName("c) ScopedValue widoczne tylko w trakcie wywołania")
    void scopedValue() {
        assertEquals("Cześć, Ala!", StructuredTasks.handleRequest("Ala"));
        assertEquals("Cześć, nieznajomy!", StructuredTasks.greeting(), "po wyjściu z call wartość ma zniknąć");
    }
}
