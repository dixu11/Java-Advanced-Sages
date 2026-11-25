package pl.sages.jext.ch2.practice;

import java.util.function.DoubleBinaryOperator;

/**
 * Ćwiczenie 4. Sprawdzenie: test {@code Zad04OperationTest}.
 *
 * Ćwiczenie 4 (slajdy 76-80): enum jako fabryka i strategia.
 * Każda stała ma symbol i lambdę, która wykonuje działanie.
 */
public enum Operation {

    // TODO: zamień null na lambdy, np. (a, b) -> a + b
    ADD("+", (a, b) -> { throw new UnsupportedOperationException("TODO"); }),
    SUBTRACT("-", (a, b) -> { throw new UnsupportedOperationException("TODO"); }),
    MULTIPLY("*", (a, b) -> { throw new UnsupportedOperationException("TODO"); }),
    DIVIDE("/", (a, b) -> { throw new UnsupportedOperationException("TODO"); });

    private final String symbol;
    private final DoubleBinaryOperator operator;

    Operation(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    public String symbol() {
        return symbol;
    }

    public double apply(double a, double b) {
        return operator.applyAsDouble(a, b);
    }

    /**
     * Fabryka: zwróć operację o podanym symbolu, np. "*" daje MULTIPLY.
     * Dla nieznanego symbolu rzuć IllegalArgumentException.
     * Podpowiedź: values() zwraca wszystkie stałe enuma.
     */
    public static Operation fromSymbol(String symbol) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Gotowe: liczy proste wyrażenie, np. "2 * 3", przy użyciu fabryki. */
    public static double evaluate(String expression) {
        String[] parts = expression.trim().split("\\s+");
        return fromSymbol(parts[1]).apply(Double.parseDouble(parts[0]), Double.parseDouble(parts[2]));
    }
}
