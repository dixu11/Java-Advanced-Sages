package pl.sages.jext.ch6.libraries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Ćwiczenie 11b: zapis i odczyt płatności przez ObjectMapper.
 */
public class Json {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** Zamień płatność na JSON: MAPPER.writeValueAsString(...). */
    public static String toJson(Payment payment) throws JsonProcessingException {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return MAPPER.writeValueAsString(payment);
        // KONIEC ROZWIĄZANIA
    }

    /**
     * Odczytaj płatność z JSON: MAPPER.readValue(json, Payment.class).
     * Zadziała dopiero po dodaniu adnotacji w Payment (ćwiczenie 11a).
     */
    public static Payment fromJson(String json) throws JsonProcessingException {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return MAPPER.readValue(json, Payment.class);
        // KONIEC ROZWIĄZANIA
    }

    /** Gotowe: pattern matching na odczytanej płatności. */
    public static String describe(Payment payment) {
        return switch (payment) {
            case Payment.Transfer(String iban, double amount) -> "przelew " + amount + " z " + iban;
            case Payment.Card(String digits, double amount) -> "karta *" + digits + " " + amount;
        };
    }
}
