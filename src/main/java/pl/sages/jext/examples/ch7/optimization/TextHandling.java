package pl.sages.jext.examples.ch7.optimization;

import java.text.BreakIterator;
import java.text.Collator;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Slajdy 176-177: {@code StringBuilder}, niuanse {@code String} (pytania ze slajdu 177 z odpowiedziami),
 * {@code Formattable}, tokenizacja ({@code split}, regex, {@code StringTokenizer}, {@code BreakIterator}),
 * {@code Collator} i {@code Normalizer}.
 * <p>
 * Poprawki (dziennik błędów): temat uzupełniający (bez slajdu) po {@code if}/{@code else if} zawsze dopisywał „Language not supported!”,
 * tu ostatnie {@code format} jest w {@code else}; temat uzupełniający (bez slajdu) {@code iterator.first()} to {@code sentenceIterator.first()};
 * temat uzupełniający (bez slajdu) brak średnika po {@code replaceAll}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch7.optimization.TextHandling}
 */
public class TextHandling {

    // --- slajd 177 ---
    static String A = "AAA\n";
    static String B = "BBB\n";
    static String C = "CCC";
    static String C1 = """
            CCC""";
    static String C2 = new String("CCC");
    static String AB = """
            AAA
            BBB
            """;
    static String AC = """
            AAA
            CCC
            """;

    // --- temat uzupełniający (bez slajdu) (poprawiony) ---
    public static class Soldier implements Formattable {
        private final String name = "Rambo";
        private final int healthPoints = 100;

        @Override
        public void formatTo(Formatter formatter, int flags, int width, int precision) {
            if (formatter.locale().equals(Locale.ENGLISH)) {
                formatter.format("A soldier with name: %s and health points: %d.", name, healthPoints);
            } else if (formatter.locale().equals(Locale.of("pl"))) {
                formatter.format("Żołnierz nazywa się: %s i posiada punkty życia: %d.", name, healthPoints);
            } else { // na slajdzie bez else
                formatter.format("Language not supported! this.toString() = %s", this.toString());
            }
        }

        static void logSoldier(Soldier s) { // helper method
            System.out.printf("%s", s); // "%s".formatted(s);
        }
    }

    // --- temat uzupełniający (bez slajdu) ---
    private static Pattern p = Pattern.compile("a*b"); // skompilowane wzorce trzymamy w polach statycznych

    public static boolean checkString(String inputString) {
        Matcher m = p.matcher(inputString);
        return m.matches();
    }

    static String[] tokenize(String sentence) { // dopisane: tokenize ze temat uzupełniający (bez slajdu)
        return sentence.split("[\\s\\p{Punct}]+");
    }

    public static void main(String[] args) {
        // --- slajd 176 ---
        var sb = new StringBuilder("Zaawansowana Java");
        sb.insert(0, ">> ").replace(3, 15, "Nowa").append('!');
        System.out.println("147: " + sb + " | reverse: " + new StringBuilder(sb).reverse() + " | setLength(7): " + sb.substring(0, 7));
        sb.setLength(25);
        System.out.println("147: po setLength(25) długość " + sb.length() + ", ostatni znak to \\u0000: " + (sb.charAt(24) == '\0'));

        // --- slajd 177 ---
        System.out.println("148: C equals C1: " + C.equals(C1) + ", C == C1: " + (C == C1));
        System.out.println("148: C1 equals C2: " + C1.equals(C2) + ", C1 == C2: " + (C1 == C2));
        System.out.println("148: A + B equals AB: " + (A + B).equals(AB));
        System.out.println("148: A + C equals AC: " + (A + C).equals(AC) + " (AC kończy się znakiem nowej linii, A + C nie)");

        // --- temat uzupełniający (bez slajdu) ---
        var soldier = new Soldier();
        System.out.println("149: " + String.format(Locale.ENGLISH, "%s", soldier));
        System.out.println("149: " + String.format(Locale.of("pl"), "%s", soldier));
        System.out.println("149: " + String.format(Locale.GERMAN, "%s", soldier).replaceAll("@.*", "@..."));

        // --- temat uzupełniający (bez slajdu) ---
        var sentence = "Witaj na szkoleniu z\n Javy!";
        String[] tokenizedSentence = tokenize(sentence);
        System.out.println("150: " + Arrays.toString(tokenizedSentence) + ", checkString(\"aaab\") = " + checkString("aaab"));

        // --- temat uzupełniający (bez slajdu) ---
        StringTokenizer stringTokenizer = new StringTokenizer("""
                Witaj   na szkoleniu\t\n
                Zaawansowane  aspekty języka  Java!
                """);
        var tokens = new StringBuilder();
        while (stringTokenizer.hasMoreTokens()) {
            tokens.append('[').append(stringTokenizer.nextToken()).append(']');
        }
        System.out.println("151: " + tokens);

        // --- temat uzupełniający (bez slajdu) ---
        var text = """
                Hello from Java Course!
                You are now learning
                how to parse advanced Strings.
                Can you see how easy you may parse sentences?
                """;
        var sentenceIterator = BreakIterator.getSentenceInstance();
        sentenceIterator.setText(text);
        int boundary = sentenceIterator.first(); // na slajdzie iterator.first()
        while (boundary != BreakIterator.DONE) {
            int start = sentenceIterator.current();
            boundary = sentenceIterator.next();
            if (boundary != BreakIterator.DONE) {
                System.out.println("153: [" + text.substring(start, boundary).strip().replace('\n', ' ') + "]");
            }
        }

        // --- temat uzupełniający (bez slajdu) ---
        String[] polishStrings = {"aą", "ab", "ac", "ać"};
        Arrays.sort(polishStrings);
        System.out.println("154: " + Arrays.toString(polishStrings)); // [ab, ac, aą, ać]
        Collator polishCollator = Collator.getInstance(Locale.of("pl", "PL"));
        Arrays.sort(polishStrings, polishCollator);
        System.out.println("154: " + Arrays.toString(polishStrings)); // [aą, ab, ac, ać]
        String stripped = Normalizer.normalize("ĄĘĆŻ", Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", ""); // na slajdzie brak średnika
        System.out.println("154: " + stripped); // AECZ
        String strippedCity = Normalizer.normalize("Łódź", Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        System.out.println("154: Łódź -> " + strippedCity + " (Ł nie ma znaku łączącego, więc zostaje)");
    }
}
