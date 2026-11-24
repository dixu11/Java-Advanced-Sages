package pl.sages.jext.examples.ch2.annotations.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Slajd 57: kontener dla powtórzonych {@link Index}; @Target, @Retention i @Inherited zgodne z Index. */
@Inherited
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Indexes {
    Index[] value();
}
