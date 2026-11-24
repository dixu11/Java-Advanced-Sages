package pl.sages.jext.ch6.libraries;

import lombok.Builder;
import lombok.Value;
import lombok.With;

/**
 * Pokaz Lomboka (slajdy 162-166), gotowe. Test środowiska sprawdza, że Lombok działa,
 * czyli że procesor adnotacji jest poprawnie podpięty w pom.xml (annotationProcessorPaths).
 */
@Value
@Builder
@With
public class Employee {
    String firstName;
    String lastName;
    String department;
}
