package pl.sages.jext.examples.ch6.libraries.fluent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;

/** Slajd 163: jedna definicja klasy z fluent API ({@code @Accessors}) i kopiowaniem ({@code @With}). */
@Data
@Accessors(fluent = true)
@With
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
    private String email;
}
