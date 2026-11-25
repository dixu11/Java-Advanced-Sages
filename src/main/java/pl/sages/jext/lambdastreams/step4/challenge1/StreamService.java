package pl.sages.jext.lambdastreams.step4.challenge1;

import java.util.List;

/**
 * Rozgrzewka, krok 4. Sprawdzenie: test {@code RozgrzewkaMyStreamServiceTest}.
 *
 * Paczka StreamChallange P1 (Dysk, 31.03.2020; nazwy metod z grupy 120). Tego interfejsu nie zmieniasz.
 * Test: {@code RozgrzewkaMyStreamServiceTest}.
 */
public interface StreamService {

    //Stwórz własną implementację tego interfejsu (MyStreamService) a w niej zaimplementuj poniższe metody. Przetestuj ich działanie

    //wyświetla imiona posortowane
    void sortAndPrint(List<String> names);

    //odsiewa powtarzające się wartości i zwraca sumę pozostałych
    int distinctAndSumNumbers(int[] numbers);

    // odfiltrowuje wszystkie imiona męskie i zbiera pozostałe do listy
    List<String> computeFemaleNames(List<String> names);

    //wyświetla tylko liczby z określonego zakresu
    void printNumbersOfRange(int[] numbers, int minValue, int maxValue);

    //Zwraca listę liczb odpowiadających ilością liter w imionach
    List<Integer> computeNamesLength(List<String> names);

    //Aby zaimplementować poniższe metody:
    //Stwórz klasę MyPerson dziedziczącą po Person, zaimplementuj i przetestuj abstrakcyjne
    // metody klasy Person, będą potrzebne później

    //dla każdego imienia z listy tworzy osobę i zwraca listę tych osób, wykorzystaj własną impl klasy Person
    List<Person> buildPeopleWithNames(List<String> names);

    //zwraca osoby o ID powyżej zadanej wartości
    List<Person> findPeopleOfIdGreaterThan(List<Person> people, int id);

    //sprawdza czy wśród tych osób jest ktoś powyżej 60 roku życia
    boolean hasSenior(List<Person> people);

    //sumuje łączną gotówkę wszystkich osób
    double sumTotalCash(List<Person> people);

    //sortuje listę według ilości pieniędzy i zwraca najbogatszą osobę, jeśli nie znajdzie nikogo może zwrócić null
    Person findRichestPerson(List<Person> people);

    //wyciąga średni wiek osób w latach jeśli nie ma żadnej osoby może zwrócić -1
    double computeAverageAge(List<Person> people);

}
