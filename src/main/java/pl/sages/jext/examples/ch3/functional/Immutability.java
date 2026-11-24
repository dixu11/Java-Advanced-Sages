package pl.sages.jext.examples.ch3.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Slajdy 101-103, 112: typ niemutowalny bez rekordu, rekordy, konstruktor kompaktowy, własne {@code equals}.
 * <p>
 * Slajd 103 nazywa {@code Soldier { ... }} „konstruktorem bezargumentowym”, a to konstruktor kompaktowy:
 * ma te same argumenty co kanoniczny, tylko ich nie wypisujemy (dziennik). Na pytanie ze slajdu 102
 * („czy Weapon jest niemutowalny?”) odpowiada {@code main}: rekord z mutowalnym komponentem zmienia się z zewnątrz.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.Immutability}
 */
public class Immutability {

    // --- slajd 101 ---
    static final class Immutable {
        private final Integer number;
        private final String[] array;
        private final List<Double> doubleList;

        public Immutable(Integer number, String[] array,
                         List<Double> doubleList) {
            this.number = number;
            this.array = Arrays.copyOf(array, array.length);
            this.doubleList = List.copyOf(doubleList);
        }

        public Integer getNumber() { return number; }

        public String[] getArray() {
            return Arrays.copyOf(array, array.length);
        }

        public List<Double> getDoubleList() {
            return doubleList; // bezpieczne dzięki List.copyOf
        }
    }

    // --- slajdy 102-103 ---
    static class Weapon { // zwykła, mutowalna klasa
        int damage;

        Weapon(int damage) { this.damage = damage; }

        @Override
        public String toString() { return "Weapon[damage=" + damage + "]"; }
    }

    record Soldier(String name, int healthPoints, Weapon mainWeapon) {
        private final static int DEFAULT_HEALTH_POINTS = 100;

        Soldier { // konstruktor kompaktowy (na slajdzie: „bezargumentowy”)
            if (healthPoints < 0) {
                throw new IllegalStateException("Health points must be >= 0!");
            }
        }

        Soldier(String name) { // konstruktor niekanoniczny: musi wywołać kanoniczny
            this(name, DEFAULT_HEALTH_POINTS, new Weapon(1));
        }
    }

    // --- slajd 112 ---
    public record TeamDTO(String key, String name, List<String> memberLogins, String chairmanLogin) {
        @Override
        public boolean equals(Object o) {
            return this == o || o instanceof TeamDTO otherTeam
                    && Objects.equals(key, otherTeam.key)
                    && Objects.equals(name, otherTeam.name)
                    && Objects.equals(memberLogins, otherTeam.memberLogins)
                    && Objects.equals(chairmanLogin, otherTeam.chairmanLogin);
        }
    }

    public static void main(String[] args) {
        String[] sourceArray = {"a", "b"};
        var sourceList = new ArrayList<>(List.of(1.0, 2.0));
        var immutable = new Immutable(1, sourceArray, sourceList);
        sourceArray[0] = "ZMIANA";
        sourceList.add(3.0);
        immutable.getArray()[1] = "ZMIANA";
        System.out.println("62: " + Arrays.toString(immutable.getArray()) + ", " + immutable.getDoubleList()
                + " (zmiany z zewnątrz nie przeszły)");

        var weapon = new Weapon(10);
        var soldier = new Soldier("Ala", 100, weapon);
        weapon.damage = 999;
        System.out.println("63: " + soldier + "  <- rekord „niemutowalny”, a broń się zmieniła");
        System.out.println("64: " + new Soldier("Ola"));
        try {
            new Soldier("Ela", -1, weapon);
        } catch (IllegalStateException e) {
            System.out.println("64: konstruktor kompaktowy: " + e.getMessage());
        }

        var t1 = new TeamDTO("k", "Best", List.of("ala"), "ala");
        var t2 = new TeamDTO("k", "Best", new ArrayList<>(List.of("ala")), "ala");
        System.out.println("68: equals = " + t1.equals(t2) + ", hashCode równe = " + (t1.hashCode() == t2.hashCode()));
    }
}
