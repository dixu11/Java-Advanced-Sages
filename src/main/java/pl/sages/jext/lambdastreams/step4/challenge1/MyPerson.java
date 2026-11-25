package pl.sages.jext.lambdastreams.step4.challenge1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Rozgrzewka, krok 4. Sprawdzenie: test {@code RozgrzewkaMyStreamServiceTest}.
 *
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
        return RANDOM.nextInt(101);
    }

    //aktualna data cofnięta losową ilość lat: od 0 do 100
    @Override
    public LocalDate getRandomBirthDate() {
        // TODO
        return LocalDate.now().minusYears(RANDOM.nextInt(101));
    }

    //wiek osoby w latach na podstawie jej daty urodzenia
    @Override
    public int getAge() {
        // TODO
        return (int) getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS);
    }
}
