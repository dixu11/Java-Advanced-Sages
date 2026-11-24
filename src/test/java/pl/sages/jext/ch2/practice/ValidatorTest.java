package pl.sages.jext.ch2.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Ćw. 2b-2c: walidator refleksją i ClassGraph")
class ValidatorTest {

    @Test
    @DisplayName("b) poprawny obiekt nie ma błędów")
    void valid() throws Exception {
        assertEquals(List.of(), Validator.validate(new User("jan", "jan@firma.pl", "")));
    }

    @Test
    @DisplayName("b) puste i null pola z @NotBlank dają błędy z komunikatem")
    void errors() throws Exception {
        List<String> errors = Validator.validate(new User("   ", null, null));
        assertEquals(Set.of("login: nie może być puste", "email: podaj e-mail"), new HashSet<>(errors));
    }

    @Test
    @DisplayName("c) dla chętnych: ClassGraph znajduje klasy z @Entity")
    void scanner() {
        assertEquals(List.of("pl.sages.jext.ch2.practice.model.Customer", "pl.sages.jext.ch2.practice.model.Product"),
                EntityScanner.findEntityClasses("pl.sages.jext.ch2.practice.model"));
    }
}
