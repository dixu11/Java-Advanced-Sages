package pl.sages.jext.examples.ch6.libraries;

import lombok.Getter;
import lombok.SuperBuilder;

/**
 * Slajd 162: jeden builder obejmuje trzy poziomy hierarchii.
 * Zwykły {@code @Builder} na klasie zna tylko pola tej klasy.
 * {@code @SuperBuilder} na każdym poziomie generuje kod dla jego pól; budujesz raz.
 * {@code lombok.SuperBuilder} jest dostępny od Lomboka 1.18.48 (wersja z pom).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch6.libraries.SuperBuilderDemo}
 */
public class SuperBuilderDemo {
    @Getter @SuperBuilder(toBuilder = true)
    static class Person { private final String name; }
    @Getter @SuperBuilder(toBuilder = true)
    static class Worker extends Person { private final String company; }
    @Getter @SuperBuilder(toBuilder = true)
    static class Developer extends Worker { private final String language; }

    public static void main(String[] args) {
        var developer = Developer.builder()
            .name("Anna")       // Person
            .company("Sages")   // Worker
            .language("Java")   // Developer
            .build();           // jeden obiekt: Anna, Sages, Java
        System.out.println(developer.getName() + ", " + developer.getCompany()
                + ", " + developer.getLanguage()); // Anna, Sages, Java

        // toBuilder() = kopia z poprawką; oryginał pozostaje bez zmian.
        // toBuilder = true na każdej klasie, bo każda kopiuje swoje pola.
        var updated = developer.toBuilder().language("Kotlin").build();
        System.out.println(updated.getLanguage() + " / " + developer.getLanguage()); // Kotlin / Java
        System.out.println(updated.getName() + ", " + updated.getCompany()
                + ", " + updated.getLanguage()); // Anna, Sages, Kotlin
        System.out.println(updated != developer); // true: nowy obiekt
    }
}
