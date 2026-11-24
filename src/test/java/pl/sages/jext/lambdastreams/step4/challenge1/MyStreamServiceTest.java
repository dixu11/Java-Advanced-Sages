package pl.sages.jext.lambdastreams.step4.challenge1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.lambdastreams.ConsoleCapture;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Lambdy i strumienie, krok 4: StreamChallange P1 (MyStreamService i MyPerson)")
class MyStreamServiceTest {

    private static final List<String> NAMES = List.of("Marcin", "Zbyszek", "Kasia", "Zosia", "Kasia", "Magda", "Iza");
    private static final int[] PRIME_NUMBERS = {2, 79, 83, 41, 43, 47, 53, 59, 13, 17, 83, 31,
            37, 61, 67, 89, 3, 5, 7, 7, 11, 71, 73, 97, 19, 19, 23, 29};

    private final StreamService service = new MyStreamService();

    /**
     * Osoba ze stałą gotówką i datą urodzenia, żeby wyniki były przewidywalne. Pola ustawiamy przed super()
     * (elastyczne konstruktory, Java 25), bo konstruktor Person woła getRandomCash() i getRandomBirthDate().
     */
    static class FixedPerson extends Person {
        private final int fixedCash;
        private final LocalDate fixedBirthDate;

        FixedPerson(String name, int cash, int age) {
            fixedCash = cash;
            fixedBirthDate = LocalDate.now().minusYears(age);
            super(name);
        }

        @Override
        protected int getRandomCash() {
            return fixedCash;
        }

        @Override
        public LocalDate getRandomBirthDate() {
            return fixedBirthDate;
        }

        @Override
        public int getAge() {
            return (int) getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS);
        }
    }

    @Test
    @DisplayName("MyPerson: gotówka 0-100, data urodzenia 0-100 lat wstecz, wiek z daty")
    void myPerson() {
        List<MyPerson> people = IntStream.range(0, 500).mapToObj(i -> new MyPerson("Osoba" + i)).toList();
        LocalDate today = LocalDate.now();
        for (MyPerson person : people) {
            assertTrue(person.getCash() >= 0 && person.getCash() <= 100, "gotówka poza 0-100: " + person);
            assertFalse(person.getBirthDate().isAfter(today), "data urodzenia w przyszłości: " + person);
            assertFalse(person.getBirthDate().isBefore(today.minusYears(101)), "ponad 100 lat wstecz: " + person);
            assertEquals((int) ChronoUnit.YEARS.between(person.getBirthDate(), today), person.getAge(),
                    "getAge() ma liczyć pełne lata od daty urodzenia: " + person);
        }
        assertTrue(people.stream().mapToDouble(Person::getCash).distinct().count() > 20, "gotówka ma być losowa");
        assertTrue(people.stream().map(Person::getBirthDate).distinct().count() > 20, "data urodzenia ma być losowa");
    }

    @Test
    @DisplayName("sortAndPrint: wyświetla imiona posortowane")
    void sortAndPrint() {
        assertEquals(List.of("Iza", "Kasia", "Kasia", "Magda", "Marcin", "Zbyszek", "Zosia"),
                ConsoleCapture.lines(() -> service.sortAndPrint(NAMES)));
    }

    @Test
    @DisplayName("distinctAndSumNumbers: bez powtórzeń i suma")
    void distinctAndSum() {
        assertEquals(1060, service.distinctAndSumNumbers(PRIME_NUMBERS), "83, 7 i 19 powtarzają się, liczymy je raz");
    }

    @Test
    @DisplayName("computeFemaleNames: bez imion męskich")
    void femaleNames() {
        assertEquals(List.of("Kasia", "Zosia", "Kasia", "Magda", "Iza"), service.computeFemaleNames(NAMES),
                "w tych danych imię żeńskie kończy się na „a”");
    }

    @Test
    @DisplayName("printNumbersOfRange: tylko liczby z zakresu")
    void range() {
        assertEquals(List.of("13", "17", "11", "19", "19"),
                ConsoleCapture.lines(() -> service.printNumbersOfRange(PRIME_NUMBERS, 10, 20)));
    }

    @Test
    @DisplayName("computeNamesLength: długości imion")
    void namesLength() {
        assertEquals(List.of(6, 7, 5, 5, 5, 5, 3), service.computeNamesLength(NAMES));
    }

    @Test
    @DisplayName("buildPeopleWithNames: osoba MyPerson dla każdego imienia")
    void build() {
        List<Person> people = service.buildPeopleWithNames(NAMES);
        assertEquals(NAMES, people.stream().map(Person::getName).toList());
        assertTrue(people.stream().allMatch(p -> p instanceof MyPerson), "użyj własnej klasy MyPerson");
    }

    @Test
    @DisplayName("findPeopleOfIdGreaterThan: ID powyżej zadanej wartości")
    void idGreaterThan() {
        Person first = new FixedPerson("A", 10, 30);
        Person second = new FixedPerson("B", 20, 40);
        Person third = new FixedPerson("C", 30, 50);
        List<Person> people = List.of(first, second, third);
        assertEquals(List.of(second, third), service.findPeopleOfIdGreaterThan(people, first.getId()),
                "„powyżej” to większe, bez równego");
    }

    @Test
    @DisplayName("hasSenior: ktoś powyżej 60 lat")
    void senior() {
        assertTrue(service.hasSenior(List.of(new FixedPerson("A", 0, 30), new FixedPerson("B", 0, 61))));
        assertFalse(service.hasSenior(List.of(new FixedPerson("A", 0, 30), new FixedPerson("B", 0, 60))),
                "60 lat to jeszcze nie „powyżej 60”");
    }

    @Test
    @DisplayName("sumTotalCash: łączna gotówka")
    void totalCash() {
        assertEquals(60.0, service.sumTotalCash(List.of(
                new FixedPerson("A", 10, 30), new FixedPerson("B", 20, 30), new FixedPerson("C", 30, 30))), 1e-9);
    }

    @Test
    @DisplayName("findRichestPerson: najbogatsza osoba, null dla pustej listy")
    void richest() {
        Person rich = new FixedPerson("B", 90, 30);
        assertSame(rich, service.findRichestPerson(List.of(new FixedPerson("A", 10, 30), rich, new FixedPerson("C", 50, 30))));
        assertNull(service.findRichestPerson(List.of()));
    }

    @Test
    @DisplayName("computeAverageAge: średni wiek, -1 dla pustej listy")
    void averageAge() {
        assertEquals(35.0, service.computeAverageAge(List.of(new FixedPerson("A", 0, 30), new FixedPerson("B", 0, 40))), 1e-9);
        assertEquals(-1, service.computeAverageAge(List.of()), 1e-9);
    }
}
