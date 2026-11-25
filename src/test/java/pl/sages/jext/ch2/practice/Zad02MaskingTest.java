package pl.sages.jext.ch2.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Ćwiczenie 2 (slajdy 42-48): własna adnotacja @Mask.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch2.practice.Masking}
 * (src/main/java/pl/sages/jext/ch2/practice/Masking.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch2.practice.Masking} (src/main/java/pl/sages/jext/ch2/practice/Masking.java)</li>
 * <li>{@code pl.sages.jext.ch2.practice.Mask} (src/main/java/pl/sages/jext/ch2/practice/Mask.java)</li>
 * <li>{@code pl.sages.jext.ch2.practice.Account} (src/main/java/pl/sages/jext/ch2/practice/Account.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 2a: własna adnotacja @Mask")
class Zad02MaskingTest {

    private static final String MASK = "pl.sages.jext.ch2.practice.Mask";

    @Test
    @DisplayName("adnotacja Mask: tylko na polach, widoczna w czasie działania")
    void annotation() {
        Class<?> mask;
        try {
            mask = Class.forName(MASK);
        } catch (ClassNotFoundException e) {
            fail("Nie ma adnotacji Mask: utwórz ją w pakiecie pl.sages.jext.ch2.practice (krok 2)");
            return;
        }
        assertTrue(mask.isAnnotation(), "Mask ma być adnotacją (@interface), a nie klasą ani interfejsem");
        Target target = mask.getAnnotation(Target.class);
        assertNotNull(target, "Mask ma sens tylko na polu: dodaj @Target");
        assertArrayEquals(new ElementType[]{ElementType.FIELD}, target.value(), "@Target: tylko pola");
        Retention retention = mask.getAnnotation(Retention.class);
        assertNotNull(retention, "Bez @Retention adnotacja nie dożyje czasu działania programu");
        assertEquals(RetentionPolicy.RUNTIME, retention.value());
    }

    @Test
    @DisplayName("opis konta do logów: hasło i karta zamaskowane, reszta bez zmian")
    void description() throws Exception {
        String description = Masking.describe(new Account("jan", "tajne123", "1111222233334444", 100.0));
        assertTrue(description.startsWith("Account[") && description.endsWith("]"),
                "Odkomentuj gotowy kod metody describe() i usuń zaślepkę (krok 1). Wynik: " + description);

        Map<String, String> fields = new HashMap<>();
        for (String pair : description.substring("Account[".length(), description.length() - 1).split(", ")) {
            String[] kv = pair.split("=", 2);
            fields.put(kv[0], kv[1]);
        }
        assertEquals("jan", fields.get("login"), "login ma zostać widoczny");
        assertEquals("100.0", fields.get("balance"), "saldo ma zostać widoczne");
        assertEquals("****", fields.get("password"),
                "hasło ma być ukryte w całości. Jeśli adnotacja stoi na polu, a nie działa: czy refleksja ją widzi?");
        assertEquals("****4444", fields.get("cardNumber"), "z numeru karty mają zostać widoczne 4 ostatnie cyfry");
    }
}
