package pl.sages.jext.examples.ch6.libraries.extensions;

/** Slajd 161: dwa wywołania operacji z obiektu przekazanego jako pierwszy parametr. */
public class CalculatorExtensions {
    public static int twice(Calculator c, int a, int b) {
        return c.calculate(c.calculate(a, b), b);
    }
}
