package pl.sages.jext.ch2.practice;

/** Klasa Account do ćwiczenia 2a. Krok 3: dopisz @Mask na polach, których nie wolno pokazać w logach. */
public class Account {

    private final String login;

    // ROZWIĄZANIE (odkomentuj i usuń throw):
    // @Mask
    // KONIEC ROZWIĄZANIA
    private final String password;

    // ROZWIĄZANIE (odkomentuj i usuń throw):
    // @Mask(keepLast = 4)
    // KONIEC ROZWIĄZANIA
    private final String cardNumber;

    private final double balance;

    public Account(String login, String password, String cardNumber, double balance) {
        this.login = login;
        this.password = password;
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
}
