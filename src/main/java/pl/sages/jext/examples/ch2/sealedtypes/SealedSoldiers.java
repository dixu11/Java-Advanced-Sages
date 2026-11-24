package pl.sages.jext.examples.ch2.sealedtypes;

/**
 * Slajd 82: typy zapieczętowane: {@code sealed}, {@code permits}, {@code final}, {@code non-sealed}.
 * <p>
 * {@code Infantry} nie ma {@code permits}: wolno go pominąć, bo podklasy są w tym samym pliku
 * (kompilator wyznacza je sam). Linie, które się nie kompilują, są zakomentowane.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.sealedtypes.SealedSoldiers}
 */
public class SealedSoldiers {

    // wyłącznie klasy na liście permits mogą dziedziczyć po klasie zapieczętowanej
    sealed static class Soldier permits Infantry, Cavalry { }

    // po typie zapieczętowanym może dziedziczyć jedynie klasa final, sealed lub non-sealed
    sealed static class Infantry extends Soldier { }

    // po klasie finalnej nic nie może dziedziczyć
    final static class Archer extends Infantry { }

    // po typie non-sealed może istnieć dowolna liczba klas dziedziczących
    non-sealed static class Cavalry extends Soldier { }

    static class HeavyCavalry extends Cavalry { } // OK: Cavalry jest non-sealed

    // static class Navy extends Soldier { }        // błąd kompilacji: Navy nie ma na liście permits
    // static class Crossbowman extends Archer { }  // błąd kompilacji: Archer jest final

    static String describe(Soldier soldier) {
        // switch po typie sealed bez default: kompilator zna wszystkie podtypy.
        // Soldier nie jest abstrakcyjny, więc sam też jest przypadkiem (bez ostatniej linii: błąd kompilacji)
        return switch (soldier) {
            case Archer _ -> "łucznik (Infantry)";
            case Infantry _ -> "piechota";
            case Cavalry c -> "kawaleria: " + c.getClass().getSimpleName();
            case Soldier _ -> "sam Soldier";
        };
    }

    public static void main(String[] args) {
        for (Soldier s : new Soldier[]{new Infantry(), new Archer(), new Cavalry(), new HeavyCavalry()}) {
            System.out.println(describe(s));
        }
        System.out.println("Soldier dopuszcza: " + java.util.Arrays.toString(Soldier.class.getPermittedSubclasses()));
        System.out.println("Infantry dopuszcza (wyznaczone przez kompilator): " + java.util.Arrays.toString(Infantry.class.getPermittedSubclasses()));
    }
}
