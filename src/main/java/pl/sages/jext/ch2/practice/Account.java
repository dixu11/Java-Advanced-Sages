package pl.sages.jext.ch2.practice;

/**
 * Ćwiczenie 2. Sprawdzenie: test {@code Zad02MaskingTest}.
 *
 * Klasa Account do ćwiczenia 2a. Krok 3: dopisz @Mask na polach, których nie wolno pokazać w logach.
 */
public class Account {

    private final String login;

    private final String password;

    private final String cardNumber;

    private final double balance;

    public Account(String login, String password, String cardNumber, double balance) {
        this.login = login;
        this.password = password;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
}
