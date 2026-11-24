package pl.sages.jext.ch2.practice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Gotowa adnotacja do ćwiczenia 2b: pole oznaczone @NotBlank nie może być null ani puste. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotBlank {

    /** Komunikat błędu. */
    String message() default "nie może być puste";
}
