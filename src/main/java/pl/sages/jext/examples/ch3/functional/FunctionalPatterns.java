package pl.sages.jext.examples.ch3.functional;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Slajdy 117-120, 122: wzorce projektowe w formie funkcyjnej: strategia (Predicate i własny interfejs),
 * fabryka jako enum z referencjami do konstruktorów, dekorator przez {@code andThen}, łańcuch odpowiedzialności.
 * <p>
 * Poprawki: na slajdzie 122 {@code checkLetters} nie ma {@code return true;} w gałęzi obsłużonej.
 * Kod z {@code andThen}/{@code compose} ze slajdu 122 to kompozycja funkcji, nie łańcuch odpowiedzialności,
 * więc jest osobno, na typach przykładowych. Klasy kształtów ze slajdu 119 są dopisane.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.FunctionalPatterns}
 */
public class FunctionalPatterns {

    // --- slajd 118 ---
    @FunctionalInterface
    public interface LearningStrategy {
        // jedna metoda abstrakcyjna, jeśli więcej to potrzebne byłyby klasy anonimowe
        Integer getGainedSkillsStrength();

        static LearningStrategy getKataLearningStrategy(Integer hours) {
            return () -> 100 * hours;
        }

        static LearningStrategy getLectureLearningStrategy(Integer hours) {
            return () -> 5 * hours;
        }

        static LearningStrategy getWorkshopLearningStrategy(Integer tasksCount, Integer difficultyLevel) {
            return () -> tasksCount * difficultyLevel;
        }
    }

    // --- slajd 119 ---
    enum Color {RED, GREEN}

    interface Shape {}
    record Circle(Color color) implements Shape {}
    record Triangle(Color color) implements Shape {}
    record Square(Color color) implements Shape {}
    record Pentagon(Color color) implements Shape {}

    public enum ShapeType {
        CIRCLE(Circle::new),
        TRIANGLE(Triangle::new),
        SQUARE(Square::new),
        PENTAGON(Pentagon::new);

        private final Function<Color, Shape> factory; // jeśli istnieje potrzeba dodajemy dodatkowe funkcje

        ShapeType(Function<Color, Shape> factory) {
            this.factory = factory;
        }

        public Shape newInstance(Color color) {
            return this.factory.apply(color);
        }
    }

    // --- slajd 122: kompozycja funkcji, na typach przykładowych ---
    record A(int v) {}
    record B(int v) {}
    record C(int v) {}
    record D(int v) {}

    public static void main(String[] args) {
        // --- slajd 117 ---
        Predicate<String> isNumber = s -> s.matches("\\d+");
        Predicate<String> isLowercase = s -> s.equals(s.toLowerCase());
        System.out.println("75: " + isNumber.test("1234"));   // true
        System.out.println("75: " + isLowercase.test("Hello")); // false

        System.out.println("76: kata 2 h = " + LearningStrategy.getKataLearningStrategy(2).getGainedSkillsStrength()
                + ", wykład 2 h = " + LearningStrategy.getLectureLearningStrategy(2).getGainedSkillsStrength()
                + ", warsztat 4 zadania x 3 = " + LearningStrategy.getWorkshopLearningStrategy(4, 3).getGainedSkillsStrength());

        // zastosowanie fabryki do stworzenia obiektu określonego typu
        var redCircle = ShapeType.CIRCLE.newInstance(Color.RED);
        System.out.println("77: " + redCircle + ", " + ShapeType.PENTAGON.newInstance(Color.GREEN));

        // --- slajd 120 ---
        Function<String, String> exclaim = s -> s + "!";
        Function<String, String> decorate = exclaim.andThen(s -> "[" + s + "]");
        System.out.println("78: " + decorate.apply("Hello"));  // [Hello!]

        // --- slajd 122: kompozycja ---
        Function<A, B> first = a -> new B(a.v() + 1);
        Function<B, C> second = b -> new C(b.v() * 10);
        Function<C, D> third = c -> new D(c.v() - 3);
        System.out.println("80: andThen = " + first.andThen(second).andThen(third).apply(new A(1))
                + ", compose = " + third.compose(second.compose(first)).apply(new A(1)));

        // --- slajd 122: łańcuch odpowiedzialności (poprawiony) ---
        Predicate<String> checkDigits = s -> {
            if (s.matches("\\d+")) {
                System.out.println("80: Same cyfry");
                return true;
            }
            return false;
        };
        Predicate<String> checkLetters = s -> {
            if (s.matches("\\p{Alpha}+")) {
                System.out.println("80: Same litery");
                return true; // na slajdzie brak
            }
            return false;
        };
        for (String input : List.of("12345", "abc", "a1")) {
            boolean handled = false;
            for (Predicate<String> handler :
                    List.of(checkDigits, checkLetters)) {
                if (handler.test(input)) {
                    handled = true;
                    // przerwanie łańcucha po obsłużeniu
                    break;
                }
            }
            if (!handled) {
                System.out.println("80: \"" + input + "\" nikt nie obsłużył");
            }
        }
    }
}
