package pl.sages.jext.ch2.practice.model;

/**
 * Ćwiczenie 2. Sprawdzenie: test {@code Zad02ValidatorTest}.
 */
@Entity
public record Product(long id, String name, double price) {
}
