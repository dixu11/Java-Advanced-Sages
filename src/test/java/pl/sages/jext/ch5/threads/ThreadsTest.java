package pl.sages.jext.ch5.threads;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@DisplayName("Ćw. 9: wątki wirtualne")
class ThreadsTest {

    @Test
    @DisplayName("pula platformowa: 200 zadań, żadne nie jest wirtualne")
    void platformThreads() throws Exception {
        assertEquals(0, Threads.runTasks(Threads.platformPool(), 200, Duration.ofMillis(10)));
    }

    @Test
    @DisplayName("wątki wirtualne: 10 000 zadań po 100 ms w mniej niż 5 s")
    void virtualThreads() {
        long virtualCount = assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> Threads.runTasks(Threads.virtualPool(), 10_000, Duration.ofMillis(100)),
                "na 100 wątkach platformowych trwałoby to ok. 10 s");
        assertEquals(10_000, virtualCount);
    }
}
