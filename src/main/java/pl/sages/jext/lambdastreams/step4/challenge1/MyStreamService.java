package pl.sages.jext.lambdastreams.step4.challenge1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Twoja implementacja {@link StreamService}. Polecenia są przy metodach interfejsu.
 * nowe: szkielet klasy jest gotowy, żeby testy się kompilowały.
 */
public class MyStreamService implements StreamService {

    @Override
    public void sortAndPrint(List<String> names) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // names.stream()
        //         .sorted()
        //         .forEach(name -> System.out.println(name));
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public int distinctAndSumNumbers(int[] numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return Arrays.stream(numbers)
        //         .distinct()
        //         .sum();
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public List<String> computeFemaleNames(List<String> names) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return names.stream()
        //         .filter(name -> name.endsWith("a"))
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public void printNumbersOfRange(int[] numbers, int minValue, int maxValue) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // Arrays.stream(numbers)
        //         .filter(number -> number >= minValue && number <= maxValue)
        //         .forEach(number -> System.out.println(number));
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public List<Integer> computeNamesLength(List<String> names) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return names.stream()
        //         .map(name -> name.length())
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public List<Person> buildPeopleWithNames(List<String> names) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return names.stream()
        //         .<Person>map(name -> new MyPerson(name))
        //         .toList();
        // // Stream<MyPerson> nie wejdzie w List<Person>: rzutowanie, map z typem <Person> albo List<? extends Person>
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public List<Person> findPeopleOfIdGreaterThan(List<Person> people, int id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return people.stream()
        //         .filter(person -> person.getId() > id)
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public boolean hasSenior(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return people.stream()
        //         .anyMatch(person -> person.getAge() > 60);
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public double sumTotalCash(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return people.stream()
        //         .mapToDouble(Person::getCash)
        //         .sum();
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public Person findRichestPerson(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return people.stream()
        //         .sorted((p1, p2) -> Double.compare(p2.getCash(), p1.getCash()))
        //         .findFirst()
        //         .orElse(null);
        // // nowe, krócej: .max(Comparator.comparingDouble(Person::getCash)).orElse(null)
        // KONIEC ROZWIĄZANIA
    }

    @Override
    public double computeAverageAge(List<Person> people) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return people.stream()
        //         .mapToInt(person -> person.getAge())
        //         .average()
        //         .orElse(-1);
        // KONIEC ROZWIĄZANIA
    }
}
