package pl.sages.jext.lambdastreams.step4.challenge1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Twoja implementacja klasy Person: zaimplementuj i przetestuj jej trzy abstrakcyjne metody.
 * nowe: szkielet klasy jest gotowy, żeby testy się kompilowały.
 */
public class MyPerson extends Person {

    private static final Random RANDOM = new Random();

    public MyPerson(String name) {
        super(name);
    }

    //liczba od 0 do 100
    @Override
    protected int getRandomCash() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return RANDOM.nextInt(101);
        // KONIEC ROZWIĄZANIA
    }

    //aktualna data cofnięta losową ilość lat: od 0 do 100
    @Override
    public LocalDate getRandomBirthDate() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return LocalDate.now().minusYears(RANDOM.nextInt(101));
        // KONIEC ROZWIĄZANIA
    }

    //wiek osoby w latach na podstawie jej daty urodzenia
    @Override
    public int getAge() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return (int) getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS);
        // KONIEC ROZWIĄZANIA
    }
}
