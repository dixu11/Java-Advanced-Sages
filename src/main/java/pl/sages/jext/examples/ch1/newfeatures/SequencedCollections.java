package pl.sages.jext.examples.ch1.newfeatures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.SequencedMap;

/**
 * Slajd 27 (oraz slajd 28): {@code SequencedCollection}, {@code SequencedSet},
 * {@code SequencedMap} i widok {@code reversed()}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.SequencedCollections}
 */
public class SequencedCollections {

    public static void main(String[] args) {
        // --- slajd 27: metody SequencedCollection ---
        SequencedCollection<String> sequence = new ArrayList<>(List.of("b", "c"));
        sequence.addFirst("a");
        sequence.addLast("d");
        System.out.println(sequence + ", getFirst = " + sequence.getFirst() + ", getLast = " + sequence.getLast());
        System.out.println("removeFirst = " + sequence.removeFirst() + ", removeLast = " + sequence.removeLast() + " -> " + sequence);

        var set = new LinkedHashSet<>(List.of(3, 1, 2)); // SequencedSet: kolejność wstawiania
        set.addFirst(2); // element już jest: przesuwa się na początek
        System.out.println("LinkedHashSet po addFirst(2): " + set + ", reversed = " + set.reversed());

        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.putFirst("zero", 0);
        System.out.println("SequencedMap: " + map + ", firstEntry = " + map.firstEntry()
                + ", pollLastEntry = " + map.pollLastEntry() + " -> " + map);

        // Domyślnie wyjątek rzucają tylko addFirst/addLast (np. lista niemutowalna, zbiór posortowany)
        try {
            new java.util.TreeSet<>(List.of(1, 2)).addFirst(0);
        } catch (UnsupportedOperationException e) {
            System.out.println("TreeSet.addFirst: UnsupportedOperationException (kolejność wyznacza sortowanie)");
        }

        // --- slajd 28: reversed() to widok, nie kopia ---
        var list = new ArrayList<>(List.of(1, 2, 3));
        var rev = list.reversed();
        System.out.println("list = " + list + ", rev = " + rev);
        list.add(4);
        System.out.println("po list.add(4): rev = " + rev + " (widok jest zawsze aktualny)");
        rev.addFirst(0);
        System.out.println("po rev.addFirst(0): list = " + list + " (początek widoku = koniec oryginału)");

        var copy = new ArrayList<>(list.reversed()); // odwrócona kopia
        Collections.reverse(list);                     // odwrócenie w miejscu
        System.out.println("copy = " + copy + ", list po Collections.reverse = " + list);
    }
}
