package pl.sages.jext.ch7.optimization;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.text.BreakIterator;
import java.text.Collator;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

/**
 * Rozdział 7: pokazy trenera (bez ćwiczeń). Uruchom:
 * mvn compile exec:exec -Dmain=pl.sages.jext.ch7.optimization.Demos
 */
public class Demos {

    // --- FFM API (slajdy 173-175): wywołanie strlen z biblioteki C, bez JNI ---

    static void ffm() throws Throwable {
        System.out.println("== FFM: strlen");
        Linker linker = Linker.nativeLinker();
        MethodHandle strlen = linker.downcallHandle(
                linker.defaultLookup().find("strlen").orElseThrow(),
                FunctionDescriptor.of(JAVA_LONG, ADDRESS));
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment cString = arena.allocateFrom("Zaawansowana Java");
            long length = (long) strlen.invokeExact(cString);
            System.out.println("strlen = " + length);
        }
    }

    // --- Tekst (temat uzupełniający (bez slajdu)): BreakIterator, Collator, Normalizer ---

    static void text() {
        System.out.println("== Collator i Normalizer");
        String[] words = {"żaba", "ćma", "zebra", "cebula", "ąb", "ab"};
        String[] plainSorted = words.clone();
        Arrays.sort(plainSorted);
        System.out.println("sort():   " + Arrays.toString(plainSorted));
        Arrays.sort(words, Collator.getInstance(Locale.of("pl", "PL")));
        System.out.println("Collator: " + Arrays.toString(words));
        System.out.println(Normalizer.normalize("ĄĘĆŻ", Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));

        System.out.println("== BreakIterator (zdania)");
        String text = "Witaj na szkoleniu! Dziś mamy np. strumienie. Czy to proste?";
        BreakIterator sentences = BreakIterator.getSentenceInstance(Locale.of("pl", "PL"));
        sentences.setText(text);
        int start = sentences.first();
        for (int end = sentences.next(); end != BreakIterator.DONE; start = end, end = sentences.next()) {
            System.out.println("[" + text.substring(start, end).trim() + "]");
        }
    }

    public static void main(String[] args) throws Throwable {
        ffm();
        text();
    }
}
