package pl.sages.jext.ch0.setup;

/**
 * Ćwiczenie 0. Sprawdzenie: test {@code Zad00EnvironmentTest}.
 *
 * Sprawdzenie środowiska. Uruchom: mvn compile exec:exec
 * (albo zielona strzałka w IntelliJ).
 */
public class Start {

    void main() {
        System.out.println("Java: " + Runtime.version());
        System.out.println("Vendor: " + System.getProperty("java.vendor"));
        System.out.println("Środowisko gotowe, można zaczynać!");
    }
}
