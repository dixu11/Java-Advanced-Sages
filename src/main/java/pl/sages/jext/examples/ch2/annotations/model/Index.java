package pl.sages.jext.examples.ch2.annotations.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Slajd 57: indeks pola albo (na klasie) indeks z listą właściwości. */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Indexes.class)
public @interface Index {
    String name() default "";
    IndexType indexType()
            default IndexType.persistent;
    String[] properties() default {};
}
