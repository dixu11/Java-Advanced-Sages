package pl.sages.jext.examples.ch2.enums;

/**
 * Slajdy 76-79: enum z polami i konstruktorem, metody abstrakcyjne w stałych, enum z interfejsem,
 * enum jako fabryka obiektów.
 * <p>
 * Każdy slajd definiuje własne {@code SoldierType}, więc są w osobnych klasach zagnieżdżonych
 * ({@code Slide43} ... {@code Slide46}). Klasa {@code Soldier} ze slajdu 76 nie jest na slajdzie pokazana,
 * więc jest dopisana.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.enums.EnumsAndFactories}
 */
public class EnumsAndFactories {

    static class Slide43 {
        record Soldier(SoldierType type) {} // dopisane: brak na slajdzie

        public enum SoldierType {
            INFANTRY("I"), CAVALRY("C"), NAVY("N");

            private final String denominator;

            SoldierType(String denominator) { // niejawny private
                this.denominator = denominator;
            }

            public String getDenominator() { // metoda każdej instancji
                return this.denominator;
            }

            public static Soldier createSoldierOfType(SoldierType type) { // metoda statyczna w ramach SoldierType
                return new Soldier(type);
            }
        }
    }

    static class Slide44 {
        enum SoldierType {
            INFANTRY {
                @Override
                public int getDefaultAttackStrength() {
                    return 50;
                }
            }, CAVALRY {
                @Override
                public int getDefaultAttackStrength() {
                    return 100;
                }
            }, NAVY {
                @Override
                public int getDefaultAttackStrength() {
                    return 200;
                }
            };

            public abstract int getDefaultAttackStrength();
        }
    }

    static class Slide45 {
        interface Rangeable {
            int getRangeAttackValue();
        }

        enum SoldierType implements Rangeable {
            FOOTMAN, // brak nadpisania domyślnej implementacji
            ARCHER {
                @Override // nadpisanie
                public int getRangeAttackValue() { return 10; }
            },
            NAVY {
                @Override // nadpisanie
                public int getRangeAttackValue() { return 100; }
            };

            @Override
            public int getRangeAttackValue() { return 0; } // domyślna, wspólna implementacja metody z interfejsu
        }
    }

    static class Slide46 {
        interface Soldier {}
        static class Footman implements Soldier {}
        static class Archer implements Soldier {}
        static class Navy implements Soldier {}

        enum SoldierFactory {
            FOOTMAN {
                @Override
                public Footman getInstance() {
                    return new Footman();
                }
            },
            ARCHER {
                @Override
                public Archer getInstance() {
                    return new Archer();
                }
            },
            NAVY {
                @Override
                public Navy getInstance() {
                    return new Navy();
                }
            };

            public abstract Soldier getInstance();
        }
    }

    public static void main(String[] args) {
        for (var type : Slide43.SoldierType.values()) {
            System.out.println("43: " + type + " -> " + type.getDenominator() + ", " + Slide43.SoldierType.createSoldierOfType(type));
        }
        for (var type : Slide44.SoldierType.values()) {
            System.out.println("44: " + type + " atak " + type.getDefaultAttackStrength()
                    + " (ciało stałej to anonimowa podklasa enuma: " + type.getClass().isAnonymousClass() + ")");
        }
        for (var type : Slide45.SoldierType.values()) {
            System.out.println("45: " + type + " zasięg " + type.getRangeAttackValue());
        }
        for (var factory : Slide46.SoldierFactory.values()) {
            Slide46.Soldier soldier = factory.getInstance();
            System.out.println("46: " + factory + " tworzy " + soldier.getClass().getSimpleName());
        }
        Slide46.Archer archer = (Slide46.Archer) Slide46.SoldierFactory.ARCHER.getInstance(); // typ ogólny: rzutowanie
        System.out.println("46: ARCHER przez typ ogólny wymaga rzutowania: " + archer.getClass().getSimpleName());
    }
}
