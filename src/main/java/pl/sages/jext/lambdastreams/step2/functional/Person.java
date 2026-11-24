package pl.sages.jext.lambdastreams.step2.functional;

/** Prosty obiekt Person z polem name i metodą hello() (arkusz, „Lambda, optional”, zad. 3). */
public class Person {

    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String hello() {
        String message = "Cześć, jestem " + name;
        System.out.println(message);
        return message; // nowe: zwraca komunikat, żeby test mógł go sprawdzić
    }
}
