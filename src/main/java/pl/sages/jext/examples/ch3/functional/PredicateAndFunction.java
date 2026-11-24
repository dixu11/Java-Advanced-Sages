package pl.sages.jext.examples.ch3.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Slajdy 91-93: łączenie {@code Predicate} ({@code not}, {@code and}, {@code or}), {@code Function.andThen},
 * lambdy w metodach kolekcji.
 * <p>
 * Slajd 93 w wersji poprawionej (dziennik błędów): {@code removeIf(num -> num % 2 == 0)} na {@code ArrayList};
 * wersja ze slajdu się nie kompiluje, a na {@code Arrays.asList} {@code removeIf} rzuca wyjątek.
 * Klasy {@code Soldier} i {@code Army} ze slajdów 91-92 mają dopisane konstruktory i pola potrzebne do pokazu.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.PredicateAndFunction}
 */
public class PredicateAndFunction {

    static class Soldier {
        private final String name;
        private final int healthPoints;
        private final int attack;
        private Army army;

        Soldier(String name, int healthPoints, int attack) {
            this.name = name;
            this.healthPoints = healthPoints;
            this.attack = attack;
        }

        int getHealthPoints() { return healthPoints; }
        int getAttack() { return attack; }
        public Army getArmy() { return army; }

        @Override
        public String toString() { return name; }
    }

    static class Army {
        private final Soldier general;

        Army(Soldier general) { this.general = general; }

        public Soldier getGeneral() { return general; }
    }

    public static void main(String[] args) {
        // --- slajd 91 ---
        Predicate<Soldier> isDead = soldier -> soldier.getHealthPoints() <= 0;
        Predicate<Soldier> isAlive = Predicate.not(isDead);
        Predicate<Soldier> isZombie = isDead.and(soldier -> soldier.getAttack() > 0);
        Predicate<Soldier> isReadyToAttack = isAlive.or(isZombie);
        var alive = new Soldier("żywy", 50, 10);
        var zombie = new Soldier("zombie", 0, 5);
        var corpse = new Soldier("trup", 0, 0);
        for (var s : List.of(alive, zombie, corpse)) {
            System.out.println("56: " + s + ": alive=" + isAlive.test(s) + ", zombie=" + isZombie.test(s)
                    + ", readyToAttack=" + isReadyToAttack.test(s));
        }

        // --- slajd 92 ---
        var general = new Soldier("generał", 100, 50);
        alive.army = new Army(general);
        Function<Soldier, Army> getArmy = soldier -> soldier.getArmy();
        Function<Army, Soldier> getGeneral = army -> army.getGeneral();
        Function<Soldier, Soldier> getSoldierGeneral = getArmy.andThen(getGeneral);
        Function<Soldier, Soldier> sameViaCompose = getGeneral.compose(getArmy);
        System.out.println("57: generał żołnierza = " + getSoldierGeneral.apply(alive) + ", compose: " + sameViaCompose.apply(alive)
                + ", identity: " + Function.<Soldier>identity().apply(alive));

        // --- slajd 93 (poprawiony) ---
        var integers = new ArrayList<>(List.of(1, 5, 4, 2));
        integers.removeIf(num -> num % 2 == 0); // operacja usuwa parzyste liczby
        System.out.println("58: po removeIf = " + integers);
        try {
            Arrays.asList(1, 5, 4, 2).removeIf(num -> num % 2 == 0);
        } catch (UnsupportedOperationException e) {
            System.out.println("58: removeIf na Arrays.asList: UnsupportedOperationException");
        }

        var toSort = Arrays.asList(1, 5, 4, 2);
        toSort.sort((a, b) -> -a.compareTo(b)); // sortuje w kolejności odwróconej (sort na widoku tablicy wolno)
        System.out.println("58: po sort = " + toSort);

        var toPrint = Arrays.asList(1, 5, 4, 2);
        toPrint.forEach(num -> System.out.print(num + " "));
        System.out.println();
    }
}
