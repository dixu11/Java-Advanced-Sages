package pl.sages.jext.ch2.practice;

/**
 * Ćwiczenie 2. Sprawdzenie: test {@code Zad02ValidatorTest}.
 *
 * Klasa do walidacji w ćwiczeniu 2 (gotowa, nie zmieniaj).
 */
public class User {

    @NotBlank
    private final String login;

    @NotBlank(message = "podaj e-mail")
    private final String email;

    private final String description; // bez adnotacji: może być puste

    public User(String login, String email, String description) {
        this.login = login;
        this.email = email;
        this.description = description;
    }
}
