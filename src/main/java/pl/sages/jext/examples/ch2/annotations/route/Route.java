package pl.sages.jext.examples.ch2.annotations.route;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Slajdy 60-63: adnotacja {@code Route} (na slajdzie {@code com.xyz.Route}), szukana przez ClassGraph i Jandex. */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {
    String value();
}
