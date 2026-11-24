package pl.sages.jext.ch2.practice;

import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * Ćwiczenie 2a (slajdy 42-48): własna adnotacja od zera.
 *
 * Metoda describe() jest gotowa: robi z obiektu tekst do logów, np. Account[login=jan, password=****, ...],
 * i maskuje pola oznaczone adnotacją @Mask. Tej adnotacji jeszcze nie ma: zrób ją.
 *
 * 1. Odkomentuj metodę describe(): usuń dwie linie ze znacznikami komentarza nad nią i pod nią,
 *    a potem zaślepkę na dole klasy. Kod przestanie się kompilować, bo nie ma typu Mask.
 * 2. Przeczytaj metodę i utwórz w tym pakiecie adnotację Mask tak, żeby kod się skompilował
 *    (jaki element musi mieć i jakiego typu, skoro kod wywołuje mask.keepLast()?).
 * 3. W klasie Account oznacz pola, których nie wolno pokazać w logach: hasło w całości,
 *    a z numeru karty zostaw widoczne 4 ostatnie cyfry.
 * 4. Uruchom MaskingTest. Jeśli kod się kompiluje, a pola dalej nie są zamaskowane,
 *    pomyśl, co musi mieć adnotacja, żeby refleksja zobaczyła ją w czasie działania (slajd 46).
 */
public class Masking {

    /*
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
    */
    
    // ZAŚLEPKA: usuń ją w kroku 1
    public static String describe(Object object) throws IllegalAccessException {
        return "TODO: krok 1";
    }
}
