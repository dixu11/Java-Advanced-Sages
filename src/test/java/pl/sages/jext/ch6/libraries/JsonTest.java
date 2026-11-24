package pl.sages.jext.ch6.libraries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ćw. 11: sealed + rekordy w JSON (Jackson)")
class JsonTest {

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
