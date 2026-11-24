package pl.sages.jext.examples.ch3.functional;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Slajdy 94-97: funkcja wyższego rzędu, curryfikacja i memoizacja przez własny {@code CachedSupplier}.
 * <p>
 * Uwaga do slajdu 97 (dziennik): tekst mówi o „polu statycznym”, a {@code CachedSupplier} trzyma wynik w zwykłym
 * polu obiektu. Wynik {@code null} nie jest zapamiętywany, a przy wielu wątkach wyrażenie może się policzyć
 * więcej niż raz.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.HigherOrderAndCurrying}
 */
public class HigherOrderAndCurrying {

    // --- slajd 94 ---
    static <T, R> List<R> applyToAll(List<T> list, Function<T, R> fn) {
        return list.stream()
                .map(fn)
                .toList();
    }

    // --- slajd 97 ---
    public static class CachedSupplier<T> implements Supplier<T> {
        private final Supplier<T> expression;
        private T result; // rezultat powinien być niemutowalny dla bezpieczeństwa

        // prywatny konstruktor
        private CachedSupplier(Supplier<T> expression) {
            this.expression = expression;
        }

        @Override
        public T get() { // nadpisanie standardowej metody z interfejsu Supplier
            if (this.result == null) {
                this.result = this.expression.get();
            }
            return this.result; // niezależnie od liczby wywołań, zwracamy przykeszowany rezultat
        }

        // Metoda pozwalająca zastosować CachedSupplier w ramach istniejącej lambdy
        public static <T> CachedSupplier<T> of(Supplier<T> expression) {
            if (expression instanceof CachedSupplier<T> cachedSupplier) {
                return cachedSupplier;
            }
            return new CachedSupplier<T>(expression);
        }
    }

    public static void main(String[] args) {
        List<String> texts = List.of("a", "bb", "ccc");
        List<Integer> lengths = applyToAll(texts, String::length); // [1, 2, 3]
        System.out.println("59: " + lengths);

        // --- slajd 95 ---
        Function<Integer, Function<Integer, Integer>> add = a -> b -> a + b;
        int sum = add.apply(2).apply(3); // 5
        Function<Integer, Integer> plusTen = add.apply(10); // „przygotowana” funkcja
        System.out.println("60: " + sum + ", plusTen(5) = " + plusTen.apply(5));

        // --- slajd 97 ---
        Supplier<String> expensive = () -> {
            System.out.println("   61: liczę drogie wyrażenie...");
            return "wynik";
        };
        var cached = CachedSupplier.of(expensive);
        System.out.println("61: " + cached.get() + ", " + cached.get() + ", " + cached.get() + " (liczone raz)");
        System.out.println("61: of(cached) zwraca ten sam obiekt: " + (CachedSupplier.of(cached) == cached));
        var withNull = CachedSupplier.of(() -> {
            System.out.println("   61: liczę wyrażenie zwracające null...");
            return null;
        });
        withNull.get();
        withNull.get(); // null nie jest zapamiętywany: liczy się drugi raz
    }
}
