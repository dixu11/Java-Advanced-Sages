package pl.sages.jext.lambdastreams.step1.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Krok 3: pętla → anonimowy Consumer → lambda z typem i klamrą → lambda skrócona
 * (grupa 120, LambdaForEachDemo). Na koniec pułapka: zmienna lokalna w lambdzie i obejście obiektem-licznikiem.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.LambdaForEachDemo}
 */
public class LambdaForEachDemo {

    static class Counter {
        int value;
    }

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Ada");
        names.add("Ala");
        names.add("Adam");

        //wyświetla imiona z listy z ilością znaków = 3
        for (String name : names) {
            if (name.length() == 3) {
                System.out.println(name);
            }
        }

        names.forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                if (name.length() == 3) {
                    System.out.println(name);
                }
            }
        });

        names.forEach(
                (String name) -> {
                    if (name.length() == 3) {
                        System.out.println(name);
                    }
                });

        names.forEach(
                name -> {
                    if (name.length() == 3) {
                        System.out.println(name);
                    }
                });

        // pułapka: zmienna lokalna użyta w lambdzie musi być efektywnie finalna
        int count = 0;
        // names.forEach(name -> count++);   // odkomentuj: błąd kompilacji
        Counter counter = new Counter();
        names.forEach(name -> counter.value++); // obejście: zmieniamy pole obiektu, a nie zmienną
        System.out.println("Imion: " + counter.value + " (zmienna count dalej " + count + ")");

        // nowe: referencja do metody zamiast lambdy, która tylko przekazuje argument dalej
        names.forEach(System.out::println);
    }
}
