package pl.sages.jext.ch2.practice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Ćwiczenie 2b (slajdy 49-51): prosty walidator oparty na refleksji.
 */
public class Validator {

    /**
     * Zwróć listę błędów w formacie "nazwaPola: komunikat" dla każdego pola z @NotBlank,
     * którego wartość jest null albo pusta (isBlank()). Gdy wszystko OK, zwróć pustą listę.
     *
     * Kroki:
     * 1. object.getClass().getDeclaredFields() daje wszystkie pola,
     * 2. field.getAnnotation(NotBlank.class) zwraca adnotację albo null,
     * 3. field.setAccessible(true), a potem field.get(object) odczytuje wartość pola prywatnego.
     */
    public static List<String> validate(Object object) throws IllegalAccessException {
        List<String> errors = new ArrayList<>();
        // TODO
        for (Field field : object.getClass().getDeclaredFields()) {
            NotBlank annotation = field.getAnnotation(NotBlank.class);
            if (annotation == null) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(object);
            if (value == null || value.toString().isBlank()) {
                errors.add(field.getName() + ": " + annotation.message());
            }
        }
        return errors;
    }
}
