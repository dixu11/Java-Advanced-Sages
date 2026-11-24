package pl.sages.jext.ch0.setup;

import pl.sages.jext.ch2.practice.Account;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Sprawdzenie środowiska. Uruchom: mvn compile exec:exec
 * (albo zielona strzałka w IntelliJ).
 */
public class Start {

    void main() {  //todo sprawdzić dokładny mechanizm
        System.out.println("Java: " + Runtime.version());
        System.out.println("Vendor: " + System.getProperty("java.vendor"));
        System.out.println("Środowisko gotowe, można zaczynać!");
        test();

        var myInt = 10;
        long myLong = 100_100_300_400_500L;


        var lista = new ArrayList<String>();
        lista.add("abc");
        lista.add("abc");
        lista.add("abc");
       // lista.add(100);

        for (String element : lista) {
            System.out.println(element.toUpperCase());
        }

        int _ = 10;
        int _ = 20;


        for (String _ : lista) {
            System.out.println("Pojedyncze wykonanie pętli");
        }


        lista.stream()
                .forEach(element -> System.out.println(element.toUpperCase()));
        Consumer<String> myLambda = element -> System.out.println(element.toUpperCase());

    }

    static void test() {
        System.out.println("wywołanie dodatkowe");
    }
}
