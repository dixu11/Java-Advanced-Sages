package pl.sages.greetings.api.internal;

/**
 * Klasa publiczna, ale w pakiecie NIEeksportowanym: poza modułem pl.sages.greetings.api
 * nikt jej nie użyje, mimo modyfikatora public. Spróbuj zaimportować ją w Main (README, krok dodatkowy 1).
 */
public final class Formatting {

    private Formatting() {
    }

    public static String exclaim(String text) {
        return text + "!";
    }
}
