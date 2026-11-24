package pl.sages.jext.examples.ch2.generics;

/**
 * Slajd 74: wielokrotne ograniczenie {@code <T extends Number & Comparable<T>>}.
 * <p>
 * Poprawka względem slajdu: {@code createContainer} zwraca {@code new NumberContainer<>(object)},
 * a zmienna w użyciu ma typ {@code NumberContainer<Integer>}; na slajdzie stoi {@code Container}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.generics.MultipleBounds}
 */
public class MultipleBounds {

    static class NumberContainer<T extends Number & Comparable<T>> {
        private final T object;

        public NumberContainer(T object) {
            this.object = object;
        }

        public boolean isHigherThan(T other) {
            return this.object.compareTo(other) > 0; // Typ Number nie definiuje compareTo - pochodzi z Comparable<T>
        }

        static <S extends Number & Comparable<S>> NumberContainer<S> createContainer(S object) {
            return new NumberContainer<>(object); // metody statyczne również mogą definiować wielokrotne ograniczenia
        }
    }

    public static void main(String[] args) {
        NumberContainer<Integer> intContainer = NumberContainer.createContainer(1);
        // String implementuje Comparable, ale nie Number
        // NumberContainer<String> stringContainer = NumberContainer.createContainer("abc");
        // Number nie implementuje Comparable
        // var expliciteNumberContainer = NumberContainer.<Number>createContainer(1.2);
        System.out.println("1 > 0: " + intContainer.isHigherThan(0));
        System.out.println("1 > 5: " + intContainer.isHigherThan(5));
        System.out.println("2.5 > 2.4: " + NumberContainer.createContainer(2.5).isHigherThan(2.4));
    }
}
