package pl.sages.jext.lambdastreams.step2.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Rozgrzewka, krok 2 (slajdy 85-139): lambdy i strumienie.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.lambdastreams.step2.functional.PersonService}
 * (src/main/java/pl/sages/jext/lambdastreams/step2/functional/PersonService.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.lambdastreams.step2.functional.PersonService} (src/main/java/pl/sages/jext/lambdastreams/step2/functional/PersonService.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step2.functional.Person} (src/main/java/pl/sages/jext/lambdastreams/step2/functional/Person.java)</li>
 * </ol>
 */
@DisplayName("Lambdy i strumienie, krok 2: Optional w PersonService")
class RozgrzewkaPersonServiceTest {

    @Test
    @DisplayName("pusty serwis daje pusty Optional, a nie null")
    void empty() {
        Optional<Person> result = new PersonService().findPersonWithShortestName();
        assertTrue(result != null && result.isEmpty(), "zwróć Optional.empty() (albo wynik min(...)), nie null");
    }

    @Test
    @DisplayName("osoba z najkrótszym imieniem")
    void shortest() {
        PersonService service = new PersonService();
        service.add(new Person("Marcin"));
        service.add(new Person("Iza"));
        service.add(new Person("Zbyszek"));
        assertEquals("Iza", service.findPersonWithShortestName().orElseThrow().getName());
    }
}
