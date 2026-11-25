package pl.sages.jext.ch2.practice.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ćwiczenie 2. Sprawdzenie: test {@code Zad02ValidatorTest}.
 *
 * Znacznik klas encji do ćwiczenia 2c (ClassGraph). Gotowe.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
}
