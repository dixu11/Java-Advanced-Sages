package pl.sages.jext.lambdastreams;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** Pomocnik testów: przechwytuje to, co kod wypisuje na System.out, i zwraca linie. */
public final class ConsoleCapture {

    private ConsoleCapture() {
    }

    public static List<String> lines(Runnable code) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            code.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8).lines().map(String::strip).toList();
    }
}
