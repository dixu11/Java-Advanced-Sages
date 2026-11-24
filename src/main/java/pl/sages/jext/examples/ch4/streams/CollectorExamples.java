package pl.sages.jext.examples.ch4.streams;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

/**
 * Slajd 136: strumień równoległy.
 * Wszystkie zaawansowane kolektory w tym pliku: dla chętnych, bez slajdu, poza kursem.
 * <p>
 * Poprawki (dziennik błędów): temat uzupełniający (bez slajdu) brak nazwy zmiennej i mieszane {@code User::group} z {@code user.getGroup()};
 * temat uzupełniający (bez slajdu) {@code reducing} z wartością początkową zwraca {@code Integer}, nie {@code Optional<Integer>}.
 * Rekord {@code User} i dane są dopisane.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch4.streams.CollectorExamples}
 */
public class CollectorExamples {

    record User(UUID id, String name, String group, List<String> logEntries, LocalDate lastLogin) {
        @Override
        public String toString() { return name; }
    }

    static UUID id(int n) { return new UUID(0, n); }

    static final LocalDate TODAY = LocalDate.of(2026, 11, 23);

    static final List<User> users = List.of(
            new User(id(1), "ala", "admin", List.of("login", "zmiana hasła"), TODAY),
            new User(id(2), "ola", "dev", List.of("login"), TODAY.minusDays(3)),
            new User(id(3), "ela", "dev", List.of(), null),
            new User(id(4), "jan", "admin", List.of("login", "backup", "logout"), TODAY),
            new User(id(5), "ewa", "qa", List.of(), null));

    // --- slajd 136 ---
    boolean isEligible(long n) { return n % 7 == 0; }

    long score(long n) { return n % 100; }

    public static void main(String[] args) {
        // --- temat uzupełniający (bez slajdu) ---
        Map<String, Set<UUID>> lookup = users.stream().collect(Collectors.groupingBy(
                User::group,
                Collectors.mapping(User::id, toSet())
        ));
        Map<Boolean, Set<UUID>> admins = users.stream().collect(Collectors.partitioningBy( // w dawnym przykładzie bez nazwy
                user -> "admin".equals(user.group()),                                     // w dawnym przykładzie getGroup()
                Collectors.mapping(User::id, toSet())
        ));
        System.out.println("Dla chętnych: lookup = " + lookup);
        System.out.println("Dla chętnych: admins = " + admins);

        // --- temat uzupełniający (bez slajdu) ---
        Integer logsSum = users.stream().collect( // w dawnym przykładzie Optional<Integer>
                Collectors.reducing(
                        0, // wartość początkowa
                        (User user) -> user.logEntries().size(), // ekstraktor
                        Integer::sum) // operacja agregująca
        );
        System.out.println("Dla chętnych: logsSum = " + logsSum + ", joining = " + users.stream().map(User::name).collect(Collectors.joining(", "))
                + ", counting = " + users.stream().collect(Collectors.counting()));

        // --- temat uzupełniający (bez slajdu) ---
        Map<String, List<String>> result = users.stream()
                .collect(Collectors.groupingBy(
                        User::group,
                        Collectors.flatMapping(
                                (User user) -> user.logEntries().stream(),
                                Collectors.toList())
                ));
        System.out.println("Dla chętnych: " + result);

        // --- temat uzupełniający (bez slajdu) ---
        Predicate<User> loggedTodayPredicate = user -> TODAY.equals(user.lastLogin());
        Map<String, Set<UUID>> todayLoggedByUserGroup = users.stream()
                .filter(loggedTodayPredicate)
                .collect(Collectors.groupingBy(User::group, Collectors.mapping(User::id, toSet())));
        Map<String, Set<UUID>> todayLoggedByUserGroup2 = users.stream()
                .collect(groupingBy(
                        User::group,
                        Collectors.filtering(
                                loggedTodayPredicate,
                                Collectors.mapping(
                                        User::id,
                                        toSet()
                                )
                        )
                ));
        System.out.println("Dla chętnych: filter przed grupowaniem = " + todayLoggedByUserGroup);
        System.out.println("Dla chętnych: filtering w kolektorze   = " + todayLoggedByUserGroup2 + " (grupy bez dopasowań zostają)");

        // --- temat uzupełniający (bez slajdu) ---
        record UserStats(long total, long neverLoggedIn) {
        }
        UserStats stats = users.stream().collect(Collectors.teeing(
                Collectors.counting(),               // zliczamy wszystkich userów
                Collectors.filtering(                // zliczamy tylko tych, którzy nigdy się nie zalogowali
                        user -> user.lastLogin() == null,
                        Collectors.counting()),
                UserStats::new                       // konstruktor specjalizowanego typu lub po prostu Map.entry
        ));
        String words = List.of("Java", "Kotlin", "Scala").stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.counting(), count -> "Total words: " + count));
        System.out.println("Dla chętnych: " + stats + ", " + words);

        // --- slajd 136 ---
        var self = new CollectorExamples();
        long t0 = System.nanoTime();
        long sequential = LongStream.rangeClosed(1, 10_000_000).filter(self::isEligible).map(self::score).sum();
        long t1 = System.nanoTime();
        long total = LongStream
                .rangeClosed(1, 10_000_000)
                .parallel()
                .filter(self::isEligible)
                .map(self::score)
                .sum();
        long t2 = System.nanoTime();
        System.out.println("Parallel: sekwencyjnie " + sequential + " w " + (t1 - t0) / 1_000_000 + " ms, równolegle " + total
                + " w " + (t2 - t1) / 1_000_000 + " ms (pierwszy pomiar bez rozgrzewki, wiarygodnie mierzy JMH)");
    }
}
