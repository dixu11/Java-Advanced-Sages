package pl.sages.jext.examples.ch2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/**
 * Slajdy 46-48 (oraz slajd 47): {@code @Retention} i {@code @Target}.
 * <p>
 * Trzy adnotacje z różnym {@code @Retention} na jednej klasie: refleksja widzi tylko {@code RUNTIME}.
 * Podgląd bajtkodu: {@code javap -v -cp target/classes 'pl.sages.jext.examples.ch2.annotations.RetentionTarget$Annotated'}
 * pokaże też adnotację {@code CLASS} (RuntimeInvisibleAnnotations), a {@code SOURCE} już nie.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.RetentionTarget}
 */
public class RetentionTarget {

    // --- slajd 48 (z @Retention ze slajdu 46) ---
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Exercise {
        int hoursPerDay();
        int startHour() default 6;
    }

    @Retention(RetentionPolicy.SOURCE) @interface SourceOnly {}
    @Retention(RetentionPolicy.CLASS) @interface InBytecode {}   // CLASS to wartość domyślna
    @Retention(RetentionPolicy.RUNTIME) @interface AtRuntime {}

    @SourceOnly
    @InBytecode
    @AtRuntime
    @Exercise(hoursPerDay = 2)
    static class Annotated {
        @Exercise(hoursPerDay = 1) void method() {}
        // @Exercise(hoursPerDay = 1) int pole;  // błąd kompilacji: @Target nie obejmuje FIELD
    }

    public static void main(String[] args) {
        System.out.println("Refleksja widzi: " + Arrays.toString(Annotated.class.getAnnotations()));
        System.out.println("SOURCE:  " + Annotated.class.isAnnotationPresent(SourceOnly.class));
        System.out.println("CLASS:   " + Annotated.class.isAnnotationPresent(InBytecode.class));
        System.out.println("RUNTIME: " + Annotated.class.isAnnotationPresent(AtRuntime.class));
    }
}
