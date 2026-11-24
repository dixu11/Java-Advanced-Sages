package pl.sages.jext.examples.ch3.functional;

import java.util.Arrays;
import java.util.List;

/**
 * Slajdy 103-108, 112-113, 115: pattern matching w {@code if} i {@code switch}, wyczerpujący switch (enum, sealed),
 * guardy {@code when}, wzorce rekordów (także zagnieżdżone) i przykład Data Oriented Programming.
 * <p>
 * Poprawki względem slajdów: brakujące średniki na slajdach 104 (po switch expression)
 * i 67 ({@code size = "not long or empty"}). Na slajdzie 112 switch z samym guardem nie jest wyczerpujący,
 * więc dopisany jest {@code default}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.PatternMatching}
 */
public class PatternMatching {

    // --- slajd 103 ---
    static String ifInstanceof(Object x) {
        if (x instanceof String string) {
            return string;
        } else if (x instanceof Integer integer) {
            return integer.toString();
        } else {
            return "unknown";
        }
    }

    static String switchPattern(Object x) {
        String maybeString = switch (x) {
            case String str -> str;
            case Integer integer -> integer.toString();
            case null, default -> "unknown";
        };
        return maybeString;
    }

    // --- slajd 104 ---
    enum Axis {POSITIVE, NEGATIVE, ZERO}

    sealed static class Soldier permits Infantry, Cavalry {}
    static final class Infantry extends Soldier {}
    static final class Cavalry extends Soldier {}

    static String axis(Axis a) {
        String translated = switch (a) {
            case POSITIVE -> "dodatni";
            case NEGATIVE -> "negatywny";
            case ZERO -> "zerowy";
        }; // na slajdzie brak średnika
        return translated;
    }

    static String soldier(Soldier s) {
        String translated = switch (s) {
            case Infantry _ -> "piechota";
            case Cavalry _ -> "kawaleria";
            // niepotrzebne, jeśli typ Soldier byłby abstrakcyjną klasą lub interfejsem:
            case Soldier _ -> "żołnierz";
            // opcjonalne, jeśli nie dostarczymy poleci NPE
            case null -> "oops";
        };
        return translated;
    }

    static void dominance(Object obj) {
        switch (obj) {
            case CharSequence cs -> System.out.println("   A sequence of length " + cs.length());
            // case String s -> System.out.println("A string: " + s); // błąd kompilacji: zdominowany przez CharSequence
            default -> { break; }
        }
    }

    // --- slajd 108 ---
    static String sizeIf(Object string) {
        String size;
        if (string instanceof String str && str.length() > 42) {
            size = "long";
        } else {
            size = "not long or empty"; // na slajdzie brak średnika
        }
        return size;
    }

    static String sizeSwitch(Object string) {
        String size = switch (string) {
            case String str when str.length() > 42 -> "long";
            case String str when str.length() > 19 -> "medium";
            case String str when str.length() > 1 -> "small";
            case null, default -> "empty"; // musimy dostarczyć default, exhaustive check nie zadziała
        };
        return size;
    }

    // --- slajd 112 ---
    sealed interface Team {
        String name();
    }

    record TeamWithChairman(String key, String name, List<String> member, String chairman) implements Team {}

    record TeamWithoutChairman(String name) implements Team {} // dopisane: „inne możliwe implementacje”

    static void team(Team someTeam) {
        if (someTeam instanceof TeamWithChairman(_, String name, _, _) && name.equalsIgnoreCase("best team ever")) {
            System.out.println("   if: " + name);
        }
        switch (someTeam) {
            case TeamWithChairman(_, String name, _, _)
                    when name.equalsIgnoreCase("best team ever") -> System.out.println("   switch: " + name);
            default -> System.out.println("   switch: inny zespół " + someTeam.name()); // dopisane, bez tego błąd kompilacji
        }
    }

    // --- slajd 113 ---
    record Point(double x, double y) {}
    enum Color {RED, GREEN, BLUE}
    record ColoredPoint(Point p, Color c) {}
    record ColoredRectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {}

    static void printXCoordOfUpperLeftPoint(ColoredRectangle r) {
        if (r instanceof ColoredRectangle(ColoredPoint(Point(var x, _), _), _)) {
            System.out.println("   Upper-left corner: " + x);
        }
    }

    static void printXCoordOfLowerRightPoint(ColoredRectangle r) {
        switch (r) {
            case ColoredRectangle(_, ColoredPoint(Point(_, var x), _))
                    -> System.out.println("   Lower-right corner: " + x); // uwaga: zmienna x, a to współrzędna y
            default -> System.out.println("   Default...");
        }
    }

    // --- slajd 115 ---
    sealed interface Delivery
            permits Courier, Pickup {}

    record Courier(String address)
            implements Delivery {}

    record Pickup(String pointId)
            implements Delivery {}

    static String label(Delivery delivery) {
        return switch (delivery) {
            case Courier(String address) ->
                    "Adres: " + address;
            case Pickup(String pointId) ->
                    "Punkt: " + pointId;
        };
    }

    public static void main(String[] args) {
        for (Object x : Arrays.asList("tekst", 42, 3.14, null)) {
            System.out.println("65: " + x + " -> if: " + (x == null ? "(pominięte: null)" : ifInstanceof(x)) + ", switch: " + switchPattern(x));
        }
        for (var a : Axis.values()) {
            System.out.println("66: " + a + " -> " + axis(a));
        }
        for (Soldier s : Arrays.asList(new Infantry(), new Cavalry(), new Soldier(), null)) {
            System.out.println("66: " + soldier(s));
        }
        dominance(new StringBuilder("abc"));
        for (var s : List.of("x", "ab", "dwadzieścia znaków!!", "x".repeat(50))) {
            System.out.println("67: długość " + s.length() + " -> if: " + sizeIf(s) + ", switch: " + sizeSwitch(s));
        }
        System.out.println("70:");
        team(new TeamWithChairman("k", "Best Team Ever", List.of(), "ala"));
        team(new TeamWithoutChairman("Rest"));
        System.out.println("71:");
        var rect = new ColoredRectangle(new ColoredPoint(new Point(1, 2), Color.RED), new ColoredPoint(new Point(3, 4), Color.BLUE));
        printXCoordOfUpperLeftPoint(rect);
        printXCoordOfLowerRightPoint(rect);
        String text = label(new Pickup("WAW-01"));
        System.out.println("73: " + text + ", " + label(new Courier("Szczecin, Wały Chrobrego 1")));
    }
}
