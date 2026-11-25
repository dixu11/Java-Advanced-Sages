package pl.sages.jext.lambdastreams.step2.functional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Rozgrzewka, krok 2. Sprawdzenie: test {@code RozgrzewkaPersonServiceTest}.
 *
 * Arkusz „Zadania zaawansowane”, sekcja „Lambda, optional”, zad. 3 (SDA-zaawansowane optionalTask).
 * Test: {@code RozgrzewkaPersonServiceTest}.
 * <p>
 * Stwórz prosty obiekt Person zawierający pole name oraz prostą metodę hello() wyświetlającą komunikat
 * z imieniem. Stwórz klasę PersonService która będzie przechowywać osoby, zaimplementuj w tym celu metodę
 * add(Person). Dodaj kilka osób a następnie zaimplementuj metodę findPersonWithShortestName().
 * Metoda ma dostarczać obiekt Person. Ponieważ chcemy uniknąć zwracania wartości null - zastosuj typ
 * zwracany Optional&lt;Person&gt;. W mainie przetestuj dwa scenariusze, w jednym otrzymałeś pusty optional
 * a w drugim faktyczny obiekt. Wywołaj metodę hello() zabezpieczając się w dowolny sposób przed błędem
 * spowodowanym zwróceniem pustego optionala.
 */
public class PersonService {

    private final List<Person> people = new ArrayList<>();

    public void add(Person person) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Optional<Person> findPersonWithShortestName() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public static void main(String[] args) {
        PersonService empty = new PersonService();
        empty.findPersonWithShortestName()
                .ifPresentOrElse(Person::hello, () -> System.out.println("Nikogo nie ma"));

        PersonService service = new PersonService();
        service.add(new Person("Marcin"));
        service.add(new Person("Iza"));
        service.add(new Person("Zbyszek"));
        service.findPersonWithShortestName()
                .ifPresentOrElse(Person::hello, () -> System.out.println("Nikogo nie ma"));
    }
}
