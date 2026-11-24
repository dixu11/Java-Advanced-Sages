package pl.sages.jext.lambdastreams.step3.streams;

import java.util.Random;

/** Klasa Pet z polem name oraz age (SDA-zaawansowane streamTask2: wiek losowy 1-15). */
public class Pet {

    private final String name;
    private final int age;

    public Pet(String name) {
        this.name = name;
        age = new Random().nextInt(15) + 1;
    }

    /** nowe: konstruktor ze stałym wiekiem, żeby testy miały stałe wyniki */
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
