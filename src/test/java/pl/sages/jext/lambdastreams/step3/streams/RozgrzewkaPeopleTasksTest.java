package pl.sages.jext.lambdastreams.step3.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Rozgrzewka, krok 3 (slajdy 85-139): lambdy i strumienie.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.lambdastreams.step3.streams.PeopleTasks}
 * (src/main/java/pl/sages/jext/lambdastreams/step3/streams/PeopleTasks.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.lambdastreams.step3.streams.PeopleTasks} (src/main/java/pl/sages/jext/lambdastreams/step3/streams/PeopleTasks.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step3.streams.Person} (src/main/java/pl/sages/jext/lambdastreams/step3/streams/Person.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step3.streams.Pet} (src/main/java/pl/sages/jext/lambdastreams/step3/streams/Pet.java)</li>
 * </ol>
 */
@DisplayName("Lambdy i strumienie, krok 3: Person i Pet, map, flatMap, mapToInt")
class RozgrzewkaPeopleTasksTest {

    // Artur (3 zwierzęta: 7, 2, 10 lat), Anna (bez zwierząt), Adam (1 zwierzę: 3 lata)
    private final List<Person> people = PeopleTasks.samplePeople();

    @Test
    @DisplayName("1) map: imiona alfabetycznie")
    void names() {
        assertEquals(List.of("Adam", "Anna", "Artur"), PeopleTasks.namesSorted(people));
    }

    @Test
    @DisplayName("2) map: miesiące urodzenia")
    void months() {
        assertEquals(List.of(11, 2, 6), PeopleTasks.birthMonths(people), "getMonthValue(), w kolejności osób");
    }

    @Test
    @DisplayName("3) flatMap: liczba zwierząt")
    void countPets() {
        assertEquals(4, PeopleTasks.countPets(people));
    }

    @Test
    @DisplayName("4) flatMap: łączny wiek zwierząt")
    void totalPetsAge() {
        assertEquals(22, PeopleTasks.totalPetsAge(people));
    }

    @Test
    @DisplayName("5) Person.getAge() z daty urodzenia")
    void age() {
        LocalDate today = LocalDate.now();
        assertEquals(30, new Person("X", today.minusYears(30), List.of()).getAge());
        assertEquals(29, new Person("Y", today.minusYears(30).plusDays(1), List.of()).getAge(),
                "urodziny jutro: jeszcze nie skończył 30 lat");
    }

    @Test
    @DisplayName("6) mapToInt: średni wiek, -1 dla pustej listy")
    void averageAge() {
        LocalDate today = LocalDate.now();
        List<Person> group = List.of(
                new Person("A", today.minusYears(20), List.of()),
                new Person("B", today.minusYears(31), List.of()));
        assertEquals(25.5, PeopleTasks.averageAge(group), 1e-9);
        assertEquals(-1, PeopleTasks.averageAge(List.of()), 1e-9);
    }
}
