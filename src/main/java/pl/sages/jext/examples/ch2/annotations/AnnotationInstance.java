package pl.sages.jext.examples.ch2.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.List;

/**
 * Slajdy 49-51 (oraz slajd 52): odczyt adnotacji z klasy i własna instancja adnotacji jako rekord.
 * <p>
 * {@code ExerciseImplFromSlide} to kod ze slajdu 51: rekordowy {@code hashCode} łamie kontrakt
 * {@code Annotation}, więc {@code HashSet} nie znajduje równego obiektu. {@code ExerciseImpl} to poprawka
 * ze slajdu 52.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.AnnotationInstance}
 */
public class AnnotationInstance {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Exercise {
        int hoursPerDay();
        int startHour() default 6;
    }

    // --- slajd 49 ---
    @Exercise(hoursPerDay = 4)
    static class Soldier {
        // ...
    }

    static class AnnotationProcessor {
        public void testAnnotationPresence() {
            Exercise exercise = Soldier.class.getAnnotation(Exercise.class);
            System.out.println("Exercise hours: " + exercise.hoursPerDay());
        }
    }

    // --- slajd 51, kod jak na slajdzie ---
    record ExerciseImplFromSlide(int hoursPerDay, int startHour) implements Exercise {
        @Override // wymagana implementacja
        public Class<? extends Annotation> annotationType() { return Exercise.class; }

        @Override // nadpisujemy equals aby bazować na interfejsie adnotacji
        public boolean equals(Object obj) {
            return this == obj ||
                    obj instanceof Exercise ex && ex.hoursPerDay() == hoursPerDay && ex.startHour() == startHour;
        }
    }

    // --- slajd 52, poprawka: hashCode według kontraktu Annotation ---
    record ExerciseImpl(int hoursPerDay, int startHour) implements Exercise {
        public Class<? extends Annotation> annotationType() { return Exercise.class; }

        public boolean equals(Object obj) {
            return this == obj || obj instanceof Exercise ex
                    && ex.hoursPerDay() == hoursPerDay && ex.startHour() == startHour;
        }

        public int hashCode() { // wzór z dokumentacji Annotation.hashCode()
            return ((127 * "hoursPerDay".hashCode()) ^ Integer.hashCode(hoursPerDay))
                    + ((127 * "startHour".hashCode()) ^ Integer.hashCode(startHour));
        }
    }

    public static void main(String[] args) {
        new AnnotationProcessor().testAnnotationPresence();

        Exercise fromClass = Soldier.class.getAnnotation(Exercise.class); // @Exercise(hoursPerDay = 4)
        Exercise fromSlide = new ExerciseImplFromSlide(4, 6);
        Exercise made = new ExerciseImpl(4, 6);

        System.out.println("slajd 51:  equals w obie strony = " + (fromSlide.equals(fromClass) && fromClass.equals(fromSlide))
                + ", hashCode " + fromSlide.hashCode() + " vs " + fromClass.hashCode()
                + ", HashSet.contains = " + new HashSet<>(List.of(fromClass)).contains(fromSlide));
        System.out.println("slajd 52: equals w obie strony = " + (made.equals(fromClass) && fromClass.equals(made))
                + ", hashCode " + made.hashCode() + " vs " + fromClass.hashCode()
                + ", HashSet.contains = " + new HashSet<>(List.of(fromClass)).contains(made));
    }
}
