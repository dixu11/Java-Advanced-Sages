package pl.sages.jext.ch2.practice;

import java.util.function.DoubleBinaryOperator;

/**
 * Ćwiczenie 4 (slajdy 76-80): enum jako fabryka i strategia.
 * Każda stała ma symbol i lambdę, która wykonuje działanie.
 */
public enum Operation {

    // TODO: zamień null na lambdy, np. (a, b) -> a + b
    // SZKIELET:START (usuń razem z zaślepką)
    ADD("+", (a, b) -> { throw new UnsupportedOperationException("TODO"); }),
    SUBTRACT("-", (a, b) -> { throw new UnsupportedOperationException("TODO"); }),
    MULTIPLY("*", (a, b) -> { throw new UnsupportedOperationException("TODO"); }),
    DIVIDE("/", (a, b) -> { throw new UnsupportedOperationException("TODO"); });
    // SZKIELET:KONIEC
    // ROZWIĄZANIE (odkomentuj i usuń throw):
    // ADD("+", (a, b) -> a + b),
    // SUBTRACT("-", (a, b) -> a - b),
    // MULTIPLY("*", (a, b) -> a * b),
    // DIVIDE("/", (a, b) -> a / b);
    // KONIEC ROZWIĄZANIA

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
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // for (Operation o : values()) {
        //     if (o.symbol.equals(symbol)) {
        //         return o;
        //     }
        // }
        // throw new IllegalArgumentException("Nieznany symbol: " + symbol);
        // KONIEC ROZWIĄZANIA
    }

    /** Gotowe: liczy proste wyrażenie, np. "2 * 3", przy użyciu fabryki. */
    public static double evaluate(String expression) {
        String[] parts = expression.trim().split("\\s+");
        return fromSymbol(parts[1]).apply(Double.parseDouble(parts[0]), Double.parseDouble(parts[2]));
    }
}
