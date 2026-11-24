package pl.sages.jext.examples.ch6.libraries.fluent;

/**
 * Slajd 163: Lombok {@code @Accessors(fluent = true)} i {@code @With}. {@code assert} ze slajdu działa tylko z flagą
 * {@code -ea}, więc wyniki są też wypisane.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch6.libraries.fluent.AccessorsWithDemo}
 */
public class AccessorsWithDemo {
    public static void main(String[] args) {
        Person person = new Person()
                .name("John")
                .age(30)
                .email("john@example.com");
        System.out.println(person.name());
        System.out.println(person.age());
        System.out.println(person.email());

        Person updated = person.withAge(31);
        assert updated != person;
        assert updated.age() == 31;
        assert person.age() == 30;
        System.out.println("updated = " + updated + ", person = " + person + ", różne obiekty: " + (updated != person));
        System.out.println("withAge(30) przy tym samym wieku zwraca this: " + (person.withAge(30) == person));
    }
}
