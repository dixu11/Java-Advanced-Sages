package pl.sages.jext.examples.ch1.newfeatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Slajdy 23-26 (oraz slajd 24): fabryki kolekcji {@code List.of}, {@code copyOf},
 * {@code Arrays.asList}, {@code Set.of}, {@code Map.of}, {@code Map.ofEntries}.
 * <p>
 * Slajd 23 w wersji poprawionej: zmienna {@code stringList}, a {@code remove} na
 * {@code Arrays.asList} rzuca wyjątek (dziennik błędów).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.CollectionFactories}
 */
public class CollectionFactories {

    public static void main(String[] args) {
        // --- slajd 23 (poprawiony) ---
        List<String> stringList = Arrays.asList("First", "Second");
        stringList.set(0, "First overwrite"); // OK: zapis do tablicy
        attempt("stringList.remove(\"Second\")", () -> stringList.remove("Second")); // stały rozmiar
        List<Integer> immutableNumberList = List.of(1, 2, 3);
        List<String> immutableStringList = List.copyOf(stringList);
        System.out.println("stringList = " + stringList + ", immutableNumberList = " + immutableNumberList
                + ", immutableStringList = " + immutableStringList);
        attempt("immutableNumberList.add(4)", () -> immutableNumberList.add(4));

        // --- slajd 25 ---
        List<String> stringListWithDuplicates = Arrays.asList("First", "Second", "First");
        Set<Integer> immutableNumberSet = Set.of(1, 2, 3);
        Set<String> immutableStringSet = Set.copyOf(stringListWithDuplicates); // tylko "First" i "Second"
        System.out.println("immutableNumberSet = " + immutableNumberSet + ", immutableStringSet = " + immutableStringSet);
        attempt("Set.of(1, 1)", () -> Set.of(1, 1)); // Set.of nie przyjmuje duplikatów, copyOf je usuwa

        // --- slajd 26 ---
        Map<String, Integer> emptyMap = Map.of();
        Map<String, Integer> twoEntriesMap = Map.of("one", 1, "two", 2);
        Map<String, Integer> anotherTwoEntriesMap = Map.ofEntries(Map.entry("one", 1), Map.entry("two", 2));
        Map<String, Integer> copy = Map.copyOf(twoEntriesMap);
        System.out.println("emptyMap = " + emptyMap + ", twoEntriesMap równa anotherTwoEntriesMap: "
                + twoEntriesMap.equals(anotherTwoEntriesMap) + ", copy = " + copy);

        // --- slajd 24: trzy rodzaje list ---
        List<String> mutable = new ArrayList<>(List.of("a", "b", "c"));
        mutable.add("d"); // OK
        String[] arr = {"a", "b", "c"};
        List<String> view = Arrays.asList(arr);
        view.set(0, "x"); // OK, arr[0] to teraz "x"
        System.out.println("mutable = " + mutable + ", arr[0] po view.set = " + arr[0]);
        attempt("view.add(\"d\")", () -> view.add("d"));
        List<String> fixed = List.copyOf(mutable);
        attempt("fixed.set(0, \"x\")", () -> fixed.set(0, "x"));
        attempt("List.of(\"a\", null)", () -> List.of("a", null));

        // pułapka ze slajdu 24: unmodifiableList to widok, nie kopia
        List<String> unmodifiable = Collections.unmodifiableList(mutable);
        mutable.add("e");
        System.out.println("unmodifiable po mutable.add(\"e\") = " + unmodifiable);
    }

    static void attempt(String description, Runnable action) {
        try {
            action.run();
            System.out.println(description + ": OK");
        } catch (RuntimeException e) {
            System.out.println(description + ": " + e.getClass().getSimpleName());
        }
    }
}
