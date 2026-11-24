package pl.sages.jext.lambdastreams.step3.streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Krok 3: arkusz „Zadania zaawansowane”, sekcja „Stream”, zad. 2 (SDA-zaawansowane streamTask2).
 * Test: {@code PeopleTasksTest}.
 * <p>
 * Stwórz klasę Pet z polem name oraz age. Stwórz 4 instancje tej klasy (obiekty) w main. Stwórz prostą klasę
 * Person z polami name, birthday oraz listą zwierząt pets (List&lt;Pet&gt;). Stwórz 3 osoby, jedna z 3 zwierzętami
 * jedna z jednym a ostatnia bez zwierząt. Zbierz te osoby do listy.
 * <ol>
 *     <li>Wykorzystując metodę map wygeneruj listę imion osób w kolejności alfabetycznej</li>
 *     <li>Wykorzystując metodę map wygeneruj listę zawierającą miesiące (liczbowo) w których urodziły się te osoby</li>
 *     <li>Wykorzystaj metodę flatMap aby policzyć ilość zwierząt tych osób</li>
 *     <li>Wykorzystaj metodę flatMap aby wyliczyć łączny wiek wszystkich zwierząt</li>
 *     <li>Stwórz w klasie Person metodę która zwraca wiek osoby wyliczając go na podstawie daty
 *         (w tym projekcie: {@link Person#getAge()})</li>
 *     <li>Wykorzystaj metodę mapToInt i wylicz średni wiek osób na liście</li>
 * </ol>
 * nowe: klasy Pet i Person są gotowe, każdy punkt to metoda dostająca listę osób.
 */
public class PeopleTasks {

    /** 1) map: imiona osób w kolejności alfabetycznej */
    public static List<String> namesSorted(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 2) map: miesiące urodzenia (liczbowo, 1-12), w kolejności osób na liście */
    public static List<Integer> birthMonths(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 3) flatMap: ile zwierząt mają te osoby razem */
    public static long countPets(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 4) flatMap: łączny wiek wszystkich zwierząt */
    public static int totalPetsAge(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 6) mapToInt: średni wiek osób; dla pustej listy -1 */
    public static double averageAge(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public static List<Person> samplePeople() {
        Pet pet1 = new Pet("Puszek", 3);
        Pet pet2 = new Pet("Reksio", 7);
        Pet pet3 = new Pet("Gonzo", 2);
        Pet pet4 = new Pet("Fractal", 10);

        Person person1 = new Person("Anna", LocalDate.of(1999, 2, 3), new ArrayList<>());
        Person person2 = new Person("Adam", LocalDate.of(2010, 6, 3), List.of(pet1));
        Person person3 = new Person("Artur", LocalDate.of(2005, 11, 3), List.of(pet2, pet3, pet4));
        return List.of(person3, person1, person2);
    }

    public static void main(String[] args) {
        List<Person> people = samplePeople();
        System.out.println(namesSorted(people));
        System.out.println(birthMonths(people));
        System.out.println(countPets(people));
        System.out.println(totalPetsAge(people));
        System.out.printf("Średni wiek: %.2f%n", averageAge(people));
    }
}
