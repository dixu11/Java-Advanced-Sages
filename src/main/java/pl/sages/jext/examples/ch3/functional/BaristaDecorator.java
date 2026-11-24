package pl.sages.jext.examples.ch3.functional;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Slajd 121: dekorator z kawą: pojedyncza dekoracja i wiele dekoracji złożonych przez {@code reduce}.
 * <p>
 * Poprawka: w {@code multiDecorate} zmienna nazywa się {@code dec}, a zwracane jest
 * {@code reducedDecorations}; tu jedna nazwa. Typy {@code CoffeeMaker}, {@code BlackCoffeeMaker},
 * {@code AddMilkDecorator}, {@code AddSugarCoffeeMaker} i {@code MilkCarton} nie są na slajdzie pokazane, więc są dopisane.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.BaristaDecorator}
 */
public class BaristaDecorator {

    // --- dopisane: typy, których slajd używa ---
    interface CoffeeMaker {
        String makeCoffee();
    }

    static class BlackCoffeeMaker implements CoffeeMaker {
        public String makeCoffee() { return "czarna kawa"; }
    }

    static class MilkCarton {}

    record AddMilkDecorator(CoffeeMaker coffeeMaker, MilkCarton carton) implements CoffeeMaker {
        public String makeCoffee() { return coffeeMaker.makeCoffee() + " + mleko"; }
    }

    record AddSugarCoffeeMaker(CoffeeMaker coffeeMaker) implements CoffeeMaker {
        public String makeCoffee() { return coffeeMaker.makeCoffee() + " + cukier"; }
    }

    // --- slajd 121 ---
    public interface Barista {
        // aplikuje pojedynczą dekorację
        static CoffeeMaker decorate(
                CoffeeMaker coffeeMaker, // fabryka kaw
                Function<CoffeeMaker, CoffeeMaker> decorator) {
            return decorator.apply(coffeeMaker);
        }

        // realizuje wiele dekoracji w formie funkcyjnej
        @SafeVarargs
        static CoffeeMaker multiDecorate(
                CoffeeMaker coffeeMaker,
                Function<CoffeeMaker, CoffeeMaker>... decorators) {
            Function<CoffeeMaker, CoffeeMaker> reducedDecorations
                    = Arrays.stream(decorators)
                    .reduce(Function.identity(),
                            Function::andThen);
            return reducedDecorations.apply(coffeeMaker);
        }
    }

    // interfejs definiuje funkcje dekorujące
    // zwracające Function (Higher Order Function)
    public interface CoffeeDecorations {
        static Function<CoffeeMaker, CoffeeMaker> addMilk(
                MilkCarton carton) {
            return coffeeMaker ->
                    new AddMilkDecorator(coffeeMaker, carton);
        }

        static Function<CoffeeMaker, CoffeeMaker> addSugar() {
            return AddSugarCoffeeMaker::new;
        }
        // inne dekoratory...
    }

    public static void main(String[] args) {
        // Przykłady wykorzystania predefiniowanej dekoracji
        CoffeeMaker milkCoffeeMaker = Barista.decorate(
                new BlackCoffeeMaker(), // bazowa fabryka
                CoffeeDecorations.addMilk(new MilkCarton())
        );
        CoffeeMaker sugarCoffeeMaker = Barista.decorate(
                new BlackCoffeeMaker(),
                CoffeeDecorations.addSugar());
        CoffeeMaker milkSugarCoffeeMaker
                = Barista.multiDecorate(
                new BlackCoffeeMaker(),
                CoffeeDecorations.addMilk(new MilkCarton()),
                CoffeeDecorations.addSugar()
        );
        System.out.println("79: " + milkCoffeeMaker.makeCoffee());
        System.out.println("79: " + sugarCoffeeMaker.makeCoffee());
        System.out.println("79: " + milkSugarCoffeeMaker.makeCoffee());
    }
}
