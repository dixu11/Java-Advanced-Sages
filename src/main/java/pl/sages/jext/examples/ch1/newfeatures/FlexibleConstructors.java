package pl.sages.jext.examples.ch1.newfeatures;

import java.math.BigInteger;

/**
 * Slajd 20 (oraz slajdy 21-22): elastyczne ciała konstruktorów, finalne w Javie 25 (JEP 513).
 * <p>
 * W klasie {@code A} linie z błędem kompilacji są zakomentowane: odkomentuj jedną i zobacz komunikat.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.FlexibleConstructors}
 */
public class FlexibleConstructors {

    /** Slajd 20, lewa strona: walidacja przed super(). */
    static class PositiveBigInteger extends BigInteger {
        public PositiveBigInteger(long value) {
            // błąd kompilacji przed JDK 25 (w 22-24 tylko jako preview)
            if (value <= 0) {
                throw new IllegalArgumentException("wartość musi być dodatnia: " + value);
            }
            super(Long.toString(value));
        }
    }

    /** Slajd 20, prawa strona: co wolno przed super(). */
    static class A {
        int i;

        A() {
            // this.i++;               // odwołanie do pola: błąd kompilacji
            // this.hashCode();        // metoda instancji (także z Object): błąd kompilacji
            // System.out.println(this); // przekazanie this: błąd kompilacji
            this.i = 10;               // dozwolone: zapis własnego pola
            super();
        }
    }

    /** Slajd 22: po co przypisanie przed super(). */
    static class Base {
        Base() { show(); } // nadklasa woła metodę nadpisaną w podklasie
        void show() {}
    }

    static class B extends Base {
        private final String name;

        B(String name) {
            this.name = name; // Java 25: dozwolone przed super()
            super();
        }

        @Override
        void show() { System.out.println("B.show(): " + name); }
    }

    /** Dla porównania: to samo z przypisaniem po super(). */
    static class C extends Base {
        private final String name;

        C(String name) {
            super();
            this.name = name;
        }

        @Override
        void show() { System.out.println("C.show(): " + name); }
    }

    public static void main(String[] args) {
        System.out.println(new PositiveBigInteger(42));
        try {
            new PositiveBigInteger(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("wyjątek przed super(): " + e.getMessage());
        }
        System.out.println("A.i = " + new A().i);
        new B("Ala"); // "Ala": pole ustawione, zanim nadklasa wywołała show()
        new C("Ala"); // "null": nadklasa wywołała show() przed przypisaniem
    }
}
