package pl.sages.jext.lambdastreams.step2.functional;

import java.util.Comparator;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Krok 2: gotowe interfejsy funkcyjne (SDA-36 Zad33, SDA-zaawansowane FunctionalInterfacesDemo, grupa 120).
 * Test: {@code FiveFunctionsTest}.
 * <p>
 * Stosując gotowe klasy interfejsów funkcyjnych (Function, Consumer, Supplier, Comparator, Predicate)
 * zaimplementuj i przetestuj 5 funkcji:
 * <ol>
 *     <li>funkcja przyjmująca tekst i zwracająca jego długość</li>
 *     <li>funkcja sprawdzająca czy przekazany tekst ma minimum 5 znaków</li>
 *     <li>funkcja dostarczająca losową liczbę z przedziału 1-10</li>
 *     <li>funkcja drukująca tekst wielkimi literami</li>
 *     <li>funkcja porównująca dwa teksty po ilości liter</li>
 * </ol>
 * Najpierw dobierz interfejs (ściąga niżej), potem napisz lambdę.
 * nowe: każda funkcja jest polem z gotowym typem, żeby test się kompilował; typ podpowiada interfejs,
 * więc ściągę grupa uzupełnia na tablicy, zanim otworzy ten plik.
 */
public class FiveFunctions {

    //Supplier - nic nie bierze coś zwraca
    //Consumer - coś bierze nic nie zwraca
    //Function - coś przyjmie coś zwraca (może być inny typ)
    //Operator - przyjmie to samo co zwraca
    //Predicate - coś przyjmie zwraca boolean
    //Comparator - przyjmie 2 rzeczy zwraca int
    //ActionListener - przyjme event, nic nie zwraca
    //Runnable - nic nie przyjmie, nic nie zwraca

    //Consumer :   (T) -> void
    //Supplier :    () -> T
    //Function :   (T) -> R
        //Operator :  (T) -> T
    //Predicate: (T) -> boolean
    //Runnable: () -> void
    //Comparator: (T,T) -> int
    //ActionListener: (ActionEvent) -> void

    private static final Random RANDOM = new Random();

    /** 1) funkcja przyjmująca tekst i zwracająca jego długość */
    public static final Function<String, Integer> TEXT_LENGTH =
            text -> { throw new UnsupportedOperationException("TODO"); }; // TODO

    /** 2) funkcja sprawdzająca czy przekazany tekst ma minimum 5 znaków */
    public static final Predicate<String> HAS_AT_LEAST_FIVE_CHARS =
            text -> { throw new UnsupportedOperationException("TODO"); }; // TODO

    /** 3) funkcja dostarczająca losową liczbę z przedziału 1-10 */
    public static final Supplier<Integer> RANDOM_ONE_TO_TEN =
            () -> { throw new UnsupportedOperationException("TODO"); }; // TODO

    /** 4) funkcja drukująca tekst wielkimi literami */
    public static final Consumer<String> PRINT_UPPER_CASE =
            text -> { throw new UnsupportedOperationException("TODO"); }; // TODO

    /** 5) funkcja porównująca dwa teksty po ilości liter */
    public static final Comparator<String> BY_LENGTH =
            (s1, s2) -> { throw new UnsupportedOperationException("TODO"); }; // TODO

    public static void main(String[] args) {
        System.out.println(TEXT_LENGTH.apply("abcd"));
        System.out.println(HAS_AT_LEAST_FIVE_CHARS.test("abcd"));
        System.out.println(RANDOM_ONE_TO_TEN.get());
        PRINT_UPPER_CASE.accept("hello");
        System.out.println(BY_LENGTH.compare("b", "aaaa"));
        // nowe: te same funkcje referencjami do metod: String::length, System.out::println,
        // Comparator.comparingInt(String::length)
    }
}
