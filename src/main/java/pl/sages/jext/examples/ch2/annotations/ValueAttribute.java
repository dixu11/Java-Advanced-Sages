package pl.sages.jext.examples.ch2.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Slajdy 43-44 (oraz slajd 45): atrybuty adnotacji, wartości domyślne i specjalny atrybut {@code value}.
 * <p>
 * Definicji adnotacji ze slajdu 44 na slajdzie nie ma, więc są niżej. Użycia z błędem kompilacji
 * są zakomentowane: odkomentuj jedno i zobacz komunikat.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.ValueAttribute}
 */
public class ValueAttribute {

    // --- slajd 43 ---
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Exercise {
        int hoursPerDay();          // brak default: atrybut wymagany
        int startHour() default 6;  // default: atrybut opcjonalny
    }

    // --- slajd 44 ---
    @Retention(RetentionPolicy.RUNTIME) @interface AnnotationWithSimpleValue { String value(); }
    @Retention(RetentionPolicy.RUNTIME) @interface AnnotationWithArrayValue { String[] value(); }
    @Retention(RetentionPolicy.RUNTIME) @interface AnnotationWithOtherRequiredAttribute {
        String value();
        String otherRequired(); // drugi wymagany atrybut obok value: wolno
    }

    // --- slajd 45 ---
    @Retention(RetentionPolicy.RUNTIME) @interface Timed {} // brak atrybutów: adnotacja-znacznik
    @Retention(RetentionPolicy.RUNTIME) @interface Timeout {
        int value();                              // brak default = obowiązkowe
        TimeUnit unit() default TimeUnit.SECONDS; // default = opcjonalne
    }

    @Exercise(hoursPerDay = 4)
    @AnnotationWithSimpleValue("value")
    @AnnotationWithArrayValue({"firstValue", "secondValue"})
    @AnnotationWithOtherRequiredAttribute(value = "value", otherRequired = "argh")
    static class Training {}

    static class Usages {
        @Timed void a() {}                        // OK
        // @Timed("x") void b() {}                // błąd kompilacji: Timed nie ma value
        @Timeout(5) void c() {}                   // OK: skrót, bo atrybut nazywa się value
        // @Timeout void d() {}                   // błąd kompilacji: brak wartości dla value
        @Timeout(value = 500, unit = TimeUnit.MILLISECONDS) void e() {} // drugi atrybut: nazwa value wraca
        // @Timeout(500, unit = TimeUnit.MILLISECONDS) void f() {}      // błąd kompilacji
        // @Exercise void g() {}                  // błąd kompilacji: brak hoursPerDay
    }

    public static void main(String[] args) throws NoSuchMethodException {
        var exercise = Training.class.getAnnotation(Exercise.class);
        System.out.println("hoursPerDay = " + exercise.hoursPerDay() + ", startHour (domyślnie) = " + exercise.startHour());
        Arrays.stream(Training.class.getAnnotations()).forEach(a -> System.out.println("  " + a));
        for (var methodName : new String[]{"c", "e"}) {
            var t = Usages.class.getDeclaredMethod(methodName).getAnnotation(Timeout.class);
            System.out.println(methodName + "(): @Timeout value = " + t.value() + ", unit = " + t.unit());
        }
    }
}
