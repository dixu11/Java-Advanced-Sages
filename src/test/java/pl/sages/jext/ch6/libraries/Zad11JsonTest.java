package pl.sages.jext.ch6.libraries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ćwiczenie 11 (slajdy 166-168): sealed i rekordy w JSON.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch6.libraries.Payment}
 * (src/main/java/pl/sages/jext/ch6/libraries/Payment.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch6.libraries.Payment} (src/main/java/pl/sages/jext/ch6/libraries/Payment.java)</li>
 * <li>{@code pl.sages.jext.ch6.libraries.Json} (src/main/java/pl/sages/jext/ch6/libraries/Json.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 11: sealed + rekordy w JSON (Jackson)")
class Zad11JsonTest {

    @Test
    @DisplayName("zapis zawiera pole type")
    void write() throws Exception {
        String json = Json.toJson(new Payment.Card("1234", 99.5));
        assertTrue(json.contains("\"type\":\"card\""), "brak pola type w: " + json);
        assertTrue(json.contains("\"lastDigits\":\"1234\""), json);
    }

    @Test
    @DisplayName("odczyt rozpoznaje właściwy rekord")
    void read() throws Exception {
        Payment p = Json.fromJson("{\"type\":\"transfer\",\"iban\":\"PL61109010140000071219812874\",\"amount\":250.0}");
        assertEquals(new Payment.Transfer("PL61109010140000071219812874", 250.0), p);
    }

    @Test
    @DisplayName("tam i z powrotem")
    void roundTrip() throws Exception {
        Payment original = new Payment.Card("9876", 12.0);
        assertEquals(original, Json.fromJson(Json.toJson(original)));
        assertEquals("karta *9876 12.0", Json.describe(original));
    }
}
