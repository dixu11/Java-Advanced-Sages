package pl.sages.jext.lambdastreams.step3.streams;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Rozgrzewka, krok 3. Sprawdzenie: test {@code RozgrzewkaPeopleTasksTest}.
 *
 * Prosta klasa Person z polami name, birthdate oraz listą zwierząt pets (SDA-zaawansowane streamTask2).
 */
public class Person {

    private final String name;
    private final LocalDate birthdate;
    private final List<Pet> pets;

    public Person(String name, LocalDate birthdate, List<Pet> pets) {
        this.name = name;
        this.birthdate = birthdate;
        this.pets = pets;
    }

    /** 5) Stwórz w klasie Person metodę która zwraca wiek osoby wyliczając go na podstawie daty */
    public int getAge() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public List<Pet> getPets() {
        return pets;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pets=" + pets +
                '}';
    }
}
