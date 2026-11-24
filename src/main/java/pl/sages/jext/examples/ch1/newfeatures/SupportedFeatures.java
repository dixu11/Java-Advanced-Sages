package pl.sages.jext.examples.ch1.newfeatures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Slajdy 13-14: {@code var}, czyli autodetekcja typów zmiennych lokalnych, i jego pułapki.
 * <p>
 * Linie, które się nie kompilują, są zakomentowane z dopiskiem „błąd kompilacji”: odkomentuj jedną
 * i zobacz komunikat {@code javac}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.SupportedFeatures}
 */
public class SupportedFeatures {

    public static void main(String[] args) {
        // --- slajd 13 ---
        String s1 = "AAA";
        var s2 = "AAA"; // dozwolone wyłącznie w ciałach metod lub blokach kodu klas
        System.out.println("s1 = " + s1 + ", s2 = " + s2 + " (typ " + ((Object) s2).getClass().getSimpleName() + ")");

        for (var i = 0; i < 5; ++i) {
            System.out.print(i + " ");
        }
        System.out.println();

        try (var inputStream = SupportedFeatures.class.getResourceAsStream("/remarks1.txt")) {
            var fileAsBytes = inputStream.readAllBytes();
            var fileAsString = new String(fileAsBytes);
            System.out.println("remarks1.txt: " + fileAsString.strip());
        } catch (IOException e) {
            System.out.println("Ooops! " + e);
        }

        // --- slajd 14: pułapki ---
        // var notInitialized;                 // 1. błąd kompilacji: brak wartości
        // var nullInitialized = null;         // 2. błąd kompilacji: null nie ma typu
        var nullInitializedWithCast = (String) null; // ale z rzutowaniem działa
        // var array = {"a", "b", "c"};        // 3. błąd kompilacji: inicjalizator tablicy wymaga typu
        var inferredFlags = 0;        // 4. int
        var inferredMask = 0x7fff;    //    int
        List<String> list = new ArrayList<String>();      // List<String>
        List<String> listWithDiamond = new ArrayList<>(); // List<String>
        var inferredList = new ArrayList<String>();       // ArrayList<String>
        var inferredAsObjectList = new ArrayList<>();     // 5. ArrayList<Object>
        // var lambda = () -> "nie działa";    // 6. błąd kompilacji: lambda potrzebuje typu docelowego

        inferredAsObjectList.add(1);
        inferredAsObjectList.add("tekst"); // wolno, bo to ArrayList<Object>
        System.out.println("nullInitializedWithCast = " + nullInitializedWithCast);
        System.out.println("inferredFlags + inferredMask = " + (inferredFlags + inferredMask)
                + " (int, bo literał bez L)");
        System.out.println("inferredAsObjectList = " + inferredAsObjectList + " (Object: przyjmie wszystko)");
        System.out.println("list, listWithDiamond, inferredList puste: "
                + (list.isEmpty() && listWithDiamond.isEmpty() && inferredList.isEmpty()));
    }
}
