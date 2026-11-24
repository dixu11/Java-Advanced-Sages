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
        names.stream()
                .sorted()
                .forEach(name -> System.out.println(name));
    }

    @Override
    public int distinctAndSumNumbers(int[] numbers) {
        // TODO
        return Arrays.stream(numbers)
                .distinct()
                .sum();
    }

    @Override
    public List<String> computeFemaleNames(List<String> names) {
        // TODO
        return names.stream()
                .filter(name -> name.endsWith("a"))
                .toList();
    }

    @Override
    public void printNumbersOfRange(int[] numbers, int minValue, int maxValue) {
        // TODO
        Arrays.stream(numbers)
                .filter(number -> number >= minValue && number <= maxValue)
                .forEach(number -> System.out.println(number));
    }

    @Override
    public List<Integer> computeNamesLength(List<String> names) {
        // TODO
        return names.stream()
                .map(name -> name.length())
                .toList();
    }

    @Override
    public List<Person> buildPeopleWithNames(List<String> names) {
        // TODO
        return names.stream()
                .<Person>map(name -> new MyPerson(name))
                .toList();
        // Stream<MyPerson> nie wejdzie w List<Person>: rzutowanie, map z typem <Person> albo List<? extends Person>
    }

    @Override
    public List<Person> findPeopleOfIdGreaterThan(List<Person> people, int id) {
        // TODO
        return people.stream()
                .filter(person -> person.getId() > id)
                .toList();
    }

    @Override
    public boolean hasSenior(List<Person> people) {
        // TODO
        return people.stream()
                .anyMatch(person -> person.getAge() > 60);
    }

    @Override
    public double sumTotalCash(List<Person> people) {
        // TODO
        return people.stream()
                .mapToDouble(Person::getCash)
                .sum();
    }

    @Override
    public Person findRichestPerson(List<Person> people) {
        // TODO
        return people.stream()
                .sorted((p1, p2) -> Double.compare(p2.getCash(), p1.getCash()))
                .findFirst()
                .orElse(null);
        // nowe, krócej: .max(Comparator.comparingDouble(Person::getCash)).orElse(null)
    }

    @Override
    public double computeAverageAge(List<Person> people) {
        // TODO
        return people.stream()
                .mapToInt(person -> person.getAge())
                .average()
                .orElse(-1);
    }
}
