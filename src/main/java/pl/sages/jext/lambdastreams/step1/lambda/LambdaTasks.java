package pl.sages.jext.lambdastreams.step1.lambda;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Rozgrzewka, krok 1. Sprawdzenie: test {@code RozgrzewkaLambdaTasksTest}.
 *
 * Zadania z arkusza „Zadania zaawansowane”, sekcja „Wyrażenia lambda”, zad. 1 i 2.
 * Test: {@code RozgrzewkaLambdaTasksTest}.
 * <p>
 * 1. Stwórz interfejs Operation, posiadający metodę abstrakcyjną przyjmującą 2 liczby i zwracającą liczbę.
 *    - za pomocą anonimowej klasy zaimplementuj dodawanie
 *    - za pomocą lambdy zaimplementuj odejmowanie
 * 2. Stwórz interfejs Printer, posiadający jedną metodę, print, przyjmującą argument typu String,
 *    wykorzystaj ten interfejs do zaimplementowania lambdą funkcji:
 *    - funkcja krzycząca, która wyświetla tekst wielkimi literami i dodaje wykrzyknik
 *    - funkcja wyświetlająca napis stworzony z pierwszej litery każdego słowa (trudne)
 * <p>
 * nowe: interfejsy Operation i Printer są już gotowe niżej (żeby test się kompilował), a każda funkcja
 * jest metodą, która ją zwraca. Ty wpisujesz implementację w miejsce {@code throw}.
 */
public class LambdaTasks {

    @FunctionalInterface
    public interface Operation {
        int calculate(int number1, int number2);
    }

    @FunctionalInterface
    public interface Printer {
        void print(String text);
    }

    /** 1a) dodawanie, za pomocą anonimowej klasy. */
    public static Operation addition() {
        // TODO
        return new Operation() {
            @Override
            public int calculate(int number1, int number2) {
                return number1 + number2;
            }
        };
    }

    /** 1b) odejmowanie, za pomocą lambdy. */
    public static Operation subtraction() {
        // TODO
        return (number1, number2) -> number1 - number2;
    }

    /** 2a) funkcja krzycząca: "Hello" wyświetla "HELLO!". */
    public static Printer shouter() {
        // TODO
        return text -> System.out.println(text.toUpperCase() + "!");
    }

    /** 2b) trudne: pierwsze litery słów, "Ala ma kota" wyświetla "Amk". */
    public static Printer firstLetters() {
        // TODO
        return text -> System.out.println(
                Arrays.stream(text.trim().split("\\s+"))
                        .map(word -> word.substring(0, 1))
                        .collect(Collectors.joining()));
    }

    public static void main(String[] args) {
        System.out.println(addition().calculate(10, 20));
        System.out.println(subtraction().calculate(10, 20));
        shouter().print("Hello");
        firstLetters().print("Ala ma kota");
    }
}
