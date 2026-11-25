package pl.sages.jext.ch2.practice;

import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * Ćwiczenie 2. Sprawdzenie: test {@code Zad02MaskingTest}.
 *
 * Ćwiczenie 2a (slajdy 42-48): własna adnotacja od zera.
 *
 * Metoda describe() jest gotowa: robi z obiektu tekst do logów, np. Account[login=jan, password=****, ...],
 * i maskuje pola oznaczone adnotacją @Mask. Tej adnotacji jeszcze nie ma: zrób ją.
 *
 * 1. Usuń throw w describe() i odkomentuj rozwiązanie pod nim przez Ctrl+/.
 *    Kod przestanie się kompilować, bo nie ma jeszcze typu Mask (plik zawiera tylko komentarz).
 * 2. Przeczytaj metodę i utwórz w tym pakiecie adnotację Mask tak, żeby kod się skompilował
 *    (jaki element musi mieć i jakiego typu, skoro kod wywołuje mask.keepLast()?).
 * 3. W klasie Account oznacz pola, których nie wolno pokazać w logach: hasło w całości,
 *    a z numeru karty zostaw widoczne 4 ostatnie cyfry.
 * 4. Uruchom Zad02MaskingTest. Jeśli kod się kompiluje, a pola dalej nie są zamaskowane,
 *    pomyśl, co musi mieć adnotacja, żeby refleksja zobaczyła ją w czasie działania (slajd 46).
 */
public class Masking {

    public static String describe(Object object) throws IllegalAccessException {
        StringJoiner description = new StringJoiner(", ", object.getClass().getSimpleName() + "[", "]");
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String value = String.valueOf(field.get(object));
            Mask mask = field.getAnnotation(Mask.class);
            if (mask != null) {
                int keepLast = Math.min(mask.keepLast(), value.length());
                value = "****" + value.substring(value.length() - keepLast);
            }
            description.add(field.getName() + "=" + value);
        }
        return description.toString();
    }
}
