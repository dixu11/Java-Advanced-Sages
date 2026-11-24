// TODO: utwórz adnotację Mask według ćwiczenia 2a.
package pl.sages.jext.ch2.practice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Pole, którego wartość nie może trafić do logów. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mask {

    /** Ile ostatnich znaków zostawić widocznych (domyślnie żadnego). */
    int keepLast() default 0;
}
