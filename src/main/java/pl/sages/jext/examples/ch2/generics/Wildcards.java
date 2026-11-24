package pl.sages.jext.examples.ch2.generics;

import java.math.BigDecimal;

/**
 * Slajdy 68-73: klasa i metoda generyczna, wildcard {@code <?>}, ograniczenie górne {@code extends}
 * i dolne {@code super}.
 * <p>
 * Slajd 68 pokazuje {@code Container} bez {@code getObject}/{@code setObject}, a slajdy 71-73 ich używają,
 * więc są dopisane. Linie, które się nie kompilują, są zakomentowane jak na slajdach.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.generics.Wildcards}
 */
public class Wildcards {

    // --- slajd 68: klasa z typem generycznym ---
    static class Container<T> {
        private T object; // pole generyczne

        public Container(T object) {
            this.object = object;
        }

        // metoda (statyczna) z typem generycznym
        static <T> Container<T> createContainer(
                T object) {
            return new Container<>(object);
        }

        public T getObject() { return object; }            // dopisane: używane na slajdach 71-73
        public void setObject(T object) { this.object = object; }

        @Override
        public String toString() { return "Container[" + object + "]"; }
    }

    // --- slajd 71: metoda może przyjmować lub zwracać typ wildcard ---
    static Container<?> createNewContainer(Container<?> anotherContainer) {
        return new Container<>(anotherContainer.getObject());
    }

    public static void main(String[] args) {
        // slajd 68: przykłady użycia
        Container<Integer> intCont = new Container<>(1);
        Container<String> stringCont
                = Container.createContainer("example");
        System.out.println("37: " + intCont + ", " + stringCont);

        // slajd 71
        Container<?> unboundedContainer = new Container<>(1);
        unboundedContainer = new Container<>("hmmm");
        // typ nie jest określony: możemy go przypisać tylko do referencji Object
        Object object = unboundedContainer.getObject();
        System.out.println("38: " + object + ", kopia: " + createNewContainer(unboundedContainer));

        // slajd 72: ograniczenie górne
        Container<? extends Number> numberContainer = new Container<>(1); // typ generyczny Integer
        numberContainer = new Container<>(1.2f); // typ generyczny Float
        Container<BigDecimal> decimalContainer = new Container<>(BigDecimal.ONE);
        numberContainer = decimalContainer; // BigDecimal extends Number
        // poniższe wyrażenie nie skompiluje się
        // numberContainer.setObject(1); UWAGA - to nie jest BigDecimal!
        Number num = numberContainer.getObject();
        System.out.println("39: " + num + " (" + num.getClass().getSimpleName() + ")");

        // slajd 73: ograniczenie dolne
        Container<? super Number> lowerBoundContainer = new Container<>(1); // niejawnie <Number>
        lowerBoundContainer.setObject(1.2f);
        lowerBoundContainer.setObject(BigDecimal.ONE);
        // lowerBoundContainer.setObject(new Object()); // nie jest to ustalony typ Number
        lowerBoundContainer = new Container<>(new Object()); // ale możemy przypisać nowy kontener
        // poniższe wyrażenie nie skompiluje się, bo Float extends Number
        // lowerBoundContainer = new Container<Float>(1.2f);
        Object referenceToObject = lowerBoundContainer.getObject();
        System.out.println("40: " + referenceToObject.getClass().getSimpleName());
    }
}
