package pl.sages.jext.ch0.setup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.data.Orders;
import pl.sages.jext.ch6.libraries.Employee;
import pl.sages.jext.ch6.libraries.EmployeeMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ćwiczenie 0 (slajdy 7-8): sprawdzenie środowiska.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch0.setup.Start}
 * (src/main/java/pl/sages/jext/ch0/setup/Start.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch0.setup.Start} (src/main/java/pl/sages/jext/ch0/setup/Start.java)</li>
 * </ol>
 *
 * Ćwiczenie 0: ten test ma być ZIELONY od razu po otwarciu projektu.
 * Jeśli jest czerwony, środowisko nie jest gotowe (patrz README, sekcja "Gdy coś nie działa").
 */
@DisplayName("Ćw. 0: środowisko")
class Zad00EnvironmentTest {

    @Test
    @DisplayName("Java w wersji 25 lub nowszej")
    void java25() {
        assertTrue(Runtime.version().feature() >= 25,
                "Projekt wymaga JDK 25 lub nowszego, a uruchomiono " + Runtime.version());
    }

    @Test
    @DisplayName("Lombok działa (procesor adnotacji)")
    void lombok() {
        Employee p = Employee.builder().firstName("Ala").lastName("Nowak").department("IT").build();
        assertEquals("HR", p.withDepartment("HR").getDepartment());
    }

    @Test
    @DisplayName("MapStruct działa (procesor adnotacji)")
    void mapstruct() {
        Employee p = Employee.builder().firstName("Ala").lastName("Nowak").department("IT").build();
        assertEquals("Ala Nowak", EmployeeMapper.INSTANCE.toDto(p).fullName());
    }

    @Test
    @DisplayName("Dane zamówień wczytują się z CSV")
    void data() {
        assertEquals(24, Orders.all().size());
    }
}
