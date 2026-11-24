package pl.sages.jext.examples.ch2.generics;

import lombok.SneakyThrows;

/**
 * Slajd 75: rekurencyjny typ generyczny (fluent API) i sztuczka z varargs, która odczytuje typ w czasie działania.
 * <p>
 * Poprawki względem slajdu: typ elementu tablicy daje {@code componentType()}, a nie
 * {@code arrayType()} (ta zwraca tablicę tablic), a konstruktor przyjmuje obiekt, więc
 * {@code getDeclaredConstructor(Object.class)} zamiast bezargumentowego. Wersja ze slajdu rzuca
 * {@code NoSuchMethodException}: {@code main} pokazuje to na końcu.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.generics.FluentApi}
 */
public class FluentApi {

    abstract static class Container<
            SELF extends Container<SELF, O>, O> {
        // narzucenie weryfikacji poprawności typu
        abstract SELF someMethod();

        abstract O getObject(); // obiekt w kontenerze

        @SafeVarargs
        @SuppressWarnings("unchecked")
        @SneakyThrows // newInstance może rzucić wyjątek
        static <O, T extends Container<T, O>> T create(
                O o, T... t // typ varargs
        ) {
            return (T) t.getClass() // typ tablicy, np. GenericContainer[]
                    .componentType() // typ "przechowywany" w tablicy (na slajdzie: arrayType)
                    .getDeclaredConstructor(Object.class) // konstruktor z obiektem (na slajdzie: bez argumentów)
                    .newInstance(o); // refleksyjne utworzenie obiektu
        }

        /** Wersja ze slajdu, bez poprawek. */
        @SafeVarargs
        @SuppressWarnings("unchecked")
        @SneakyThrows
        static <O, T extends Container<T, O>> T createFromSlide(O o, T... t) {
            return (T) t.getClass()
                    .arrayType()
                    .getDeclaredConstructor()
                    .newInstance(o);
        }
    }

    static class GenericContainer<O>
            // w ramach typu generycznego
            // powtórzenie nazwy klasy:
            extends Container<GenericContainer<O>, O> {
        private final O object;

        public GenericContainer(O object) {
            this.object = object;
        }

        @Override
        O getObject() { return object; }

        // nadpisanie metody z określonym typem generycznym:
        @Override
        GenericContainer<O> someMethod() { return this; }

        // nowa metoda niedostępna w nadtypie:
        GenericContainer<O> newMethod() { return this; }
    }

    public static void main(String[] args) {
        // przykład użycia w ramach tzw. fluent API
        GenericContainer<Integer> newContainer
                = new GenericContainer<>(1)
                // dzięki rekurencyjnemu typowi generycznemu
                .someMethod()
                // można wywołać w dowolnej kolejności!!!
                .newMethod();
        System.out.println("fluent: " + newContainer.getObject());

        // referencja nadaje typ stworzonego kontenera:
        GenericContainer<Integer> otherContainer
                = Container.create(1);
        System.out.println("create(1): " + otherContainer.getClass().getSimpleName() + " z " + otherContainer.getObject());

        try {
            GenericContainer<Integer> fromSlide = Container.createFromSlide(1);
            System.out.println(fromSlide);
        } catch (Exception e) {
            System.out.println("wersja ze slajdu: " + e);
        }
    }
}
