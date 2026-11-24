package pl.sages.jext.examples.ch4.streams;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Slajdy 126-131: Optional w łańcuchu, Optional jako strumień, wyjątki w lambdach: wrapper {@code tryFunction},
 * {@code Either} i monada {@code Try}.
 * <p>
 * Dopisane, czego slajdy nie pokazują: {@code Content}, {@code loadFromDB}, typy ze slajdu 127 oraz pominięte
 * na slajdzie 131 konstruktory i {@code Try.of}. {@code Files::readString} rzuca {@code IOException}, więc
 * {@code Try.of} przyjmuje {@code TryFunction} i zamienia wyjątek sprawdzany na {@code UncheckedIOException}.
 * {@code log.error(e)} ze slajdu 129 zastępuje {@code System.err.println}, bo projekt nie ma loggera.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch4.streams.OptionalAndExceptions}
 */
public class OptionalAndExceptions {

    // --- slajd 126 ---
    record Content(String id, boolean isPublished) {}

    static final Map<String, Content> DATABASE = Map.of("news", new Content("news", true), "draft", new Content("draft", false));

    static Optional<Content> loadFromDB(String id) {
        System.out.println("   loadFromDB(" + id + ")");
        return Optional.ofNullable(id).map(DATABASE::get);
    }

    public static Optional<Content> get(String contentId) {
        return Optional.ofNullable(contentId)
                .filter(Predicate.not(String::isBlank))
                .map(String::toLowerCase)
                .flatMap(id -> Optional.ofNullable(DATABASE.get(id))) // dopisane: odczyt z pamięci podręcznej
                .or(() -> loadFromDB(contentId)) // backup plan
                .filter(Content::isPublished);
    }

    // --- slajd 127 ---
    record User(String name, boolean isActive) {}
    record Group(Optional<User> admin) {}
    record Permissions(Group group, boolean isEmpty) {}

    // --- slajd 129 ---
    @FunctionalInterface
    public interface TryFunction<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface TrySupplier<T> {
        T get() throws Exception;
    }

    public static <T, R> Function<T, Optional<R>> tryFunction(TryFunction<T, R> fn) {
        return t -> {
            try {
                // jeśli sukces - Optional z wynikiem funkcji
                return Optional.ofNullable(fn.apply(t));
                // można też łapać najbardziej ogólny Throwable
            } catch (Exception e) {
                System.err.println("   log.error: " + e); // na slajdzie log.error(e)
                // jeśli błąd - zwracamy pusty Optional
                return Optional.empty();
            }
        };
    }

    // --- slajd 130 ---
    public sealed interface Either<L, R> permits Left, Right {}
    public record Left<L, R>(L value) implements Either<L, R> {}
    public record Right<L, R>(R value) implements Either<L, R> {}

    static Either<String, Integer> safeParse(String s) {
        try {
            return new Right<>(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return new Left<>("Nieprawidłowy format liczby: " + s);
        }
    }

    // --- slajd 131 ---
    public static class Try<T, R> implements Function<T, Optional<R>> {
        private final Function<T, R> fn;
        private final Function<RuntimeException, R> failureFn;

        // pominięta na slajdzie definicja prywatnych konstruktorów i metody of (dopisane)
        private Try(Function<T, R> fn, Function<RuntimeException, R> failureFn) {
            this.fn = fn;
            this.failureFn = failureFn;
        }

        public static <T, R> Try<T, R> of(TryFunction<T, R> fn) {
            return new Try<>(t -> {
                try {
                    return fn.apply(t);
                } catch (RuntimeException e) {
                    throw e;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, null);
        }

        public Try<T, R> success(Function<R, R> successFn) {
            return new Try<>(this.fn.andThen(successFn), this.failureFn);
        }

        public Try<T, R> failure(Function<RuntimeException, R> failureFn) {
            return new Try<>(this.fn, failureFn);
        }

        @Override
        // nadpisujemy działanie standardowej funkcji
        public Optional<R> apply(T value) {
            try {
                return Optional.ofNullable(this.fn.apply(value));
            } catch (RuntimeException e) {  // jeśli wystąpi wyjątek
                // weryfikujemy czy została zdeklarowana funkcja dla sytuacji wyjątkowej
                if (this.failureFn != null) {
                    var result = this.failureFn.apply(e); // wykonujemy ją
                    return Optional.ofNullable(result);
                }
            }
            // w przypadku braku funkcji zwracamy pusty Optional
            return Optional.empty();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("82: get(\"NEWS\") = " + get("NEWS"));
        System.out.println("82: get(\"draft\") = " + get("draft") + " (nieopublikowany)");
        System.out.println("82: get(\" \") = " + get(" ") + " (puste id: filtr przepuszcza do planu B)");

        var ala = new User("ala", true);
        var ola = new User("ola", false);
        var permissions = List.of(new Permissions(new Group(Optional.of(ala)), false),
                new Permissions(new Group(Optional.of(ola)), false),
                new Permissions(new Group(Optional.empty()), false),
                new Permissions(new Group(Optional.of(ala)), true));
        List<User> activeUsers = permissions.stream()
                .filter(Predicate.not(Permissions::isEmpty))
                .map(Permissions::group)
                .map(Group::admin)
                .flatMap(Optional::stream)  // zwykle konwersja następuje w ramach metody flatMap
                .filter(User::isActive)
                .toList();
        System.out.println("83: " + activeUsers + ", requireNonNullElse(null, \"domyślny\") = "
                + Objects.requireNonNullElse(null, "domyślny"));

        Function<String, Optional<Integer>> safeParse = tryFunction(Integer::parseInt);
        Optional<Integer> ok = safeParse.apply("123");      // optional będzie zawierał liczbę
        Optional<Integer> fail = safeParse.apply("blabla"); // optional empty
        System.out.println("85: " + ok + ", " + fail);

        Either<String, Integer> result = safeParse("abc");
        for (var r0 : List.of(result, safeParse("42"))) {
            switch (r0) {
                case Right r -> System.out.println("86: Liczba: " + r.value()); // typ surowy jak na slajdzie
                case Left l -> System.out.println("86: Błąd: " + l.value());
            }
        }

        Path path1 = Files.writeString(Files.createTempFile("jext", ".txt"), "pierwszy");
        Path path2 = Path.of("nie-ma-takiego-pliku.txt");
        Path path3 = Files.writeString(Files.createTempFile("jext", ".txt"), "trzeci");
        var fileLoader =
                Try.<Path, String>of(Files::readString)
                        .success(String::toUpperCase)
                        .failure(str -> null);
        List<String> fileContents =
                Stream.of(path1, path2, path3)
                        .map(fileLoader)
                        .flatMap(Optional::stream) // pomijamy błędy
                        .toList();
        System.out.println("87: " + fileContents + " (brakujący plik pominięty)");
        Files.delete(path1);
        Files.delete(path3);
    }
}
