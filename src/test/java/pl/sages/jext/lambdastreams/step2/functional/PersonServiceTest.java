package pl.sages.jext.lambdastreams.step2.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Lambdy i strumienie, krok 2: Optional w PersonService")
class PersonServiceTest {

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
