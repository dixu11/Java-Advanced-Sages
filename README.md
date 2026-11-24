# Zaawansowane aspekty języka Java: projekt z ćwiczeniami

Projekt Maven na **Javę 25 lub nowszą**, bez funkcji preview: uczymy tego, co jest stabilne w Javie 25. Każde ćwiczenie to klasa z komentarzami `TODO` i test JUnit, który zmieni się na zielony, gdy rozwiążesz zadanie. Bez Dockera, bez bazy danych, bez internetu po pierwszym pobraniu bibliotek.

## 1. Instalacja

### JDK 25 lub nowsze (np. Eclipse Temurin 25)

Potrzebna jest wersja **25 albo nowsza** (26, 27 też działają). Na 21 projekt się nie skompiluje. Jeśli masz już JDK 25+, nic nie instaluj.

- **Windows:** https://adoptium.net/temurin/releases/?version=25 → Windows x64 → `.msi`. W instalatorze zaznacz „Set JAVA_HOME variable” i „Add to PATH”. Bez uprawnień administratora: pobierz `.zip`, rozpakuj np. do `C:\java\jdk-25` i wskaż ten folder w IntelliJ (punkt 2).
- **Mac:** `brew install --cask temurin@25` albo `.pkg` z tej samej strony.
- **Linux:** `.tar.gz` z tej samej strony albo menedżer pakietów (np. SDKMAN: `sdk install java 25-tem`).

Sprawdzenie w nowym oknie terminala:

```
java -version
```

Ma się pojawić `openjdk version "25...` (albo wyższy numer).

### Maven (3.9 lub nowszy)

IntelliJ ma wbudowanego Mavena, więc do pracy w IDE instalacja nie jest konieczna. Do pracy w konsoli:

- **Windows:** https://maven.apache.org/download.cgi → „Binary zip archive”, rozpakuj np. do `C:\java\maven`, dodaj `C:\java\maven\bin` do zmiennej `PATH`.
- **Mac:** `brew install maven`.
- **Linux:** `sudo apt install maven` albo archiwum ze strony.

Sprawdzenie:

```
mvn -version
```

W wyniku `Java version: 25...` albo wyższa. Jeśli widać starszą wersję Javy, ustaw `JAVA_HOME` na folder JDK 25+.

## 2. IntelliJ IDEA

1. **File → Open** i wskaż folder projektu (ten z `pom.xml`). Zaufaj projektowi (Trust Project).
2. **File → Project Structure → Project:**
   - **SDK:** 25 lub nowsze (jeśli go nie ma: Add SDK → JDK i wskaż folder JDK, albo Download JDK → wersja 25, Eclipse Temurin);
   - **Language level:** `SDK default` albo `25` (bez „Preview”).
3. **Settings → Build, Execution, Deployment → Build Tools → Maven → Runner → JRE:** „Use Project JDK”.
4. W panelu Maven (prawa krawędź) kliknij **Reload All Maven Projects**.
5. Otwórz `src/test/java/pl/sages/jext/ch0/setup/EnvironmentTest.java` i uruchom go zieloną strzałką. **Wszystkie 4 testy muszą być zielone.** To jest ćwiczenie 0.

Procesory adnotacji (Lombok, MapStruct) są skonfigurowane w `pom.xml`. W IntelliJ nic nie trzeba włączać; jeśli pojawi się pytanie o „annotation processing”, kliknij Enable.

## 3. Uruchamianie

| Co | Komenda |
|---|---|
| Wszystkie testy | `mvn test` |
| Testy jednego ćwiczenia | `mvn test -Dtest=PaymentsTest` |
| Klasa z metodą `main` | `mvn compile exec:exec -Dmain=pl.sages.jext.ch5.threads.Threads` |
| Sprawdzenie środowiska | `mvn compile exec:exec` |

Na początku `mvn test` pokazuje dużo czerwonych testów i `BUILD FAILURE`. Tak ma być: zielony jest tylko test środowiska. Każde rozwiązane ćwiczenie zmienia swoje testy na zielone. W IntelliJ najwygodniej uruchamiać pojedynczy test strzałką przy klasie albo metodzie testu.

## 4. Ćwiczenia

Kod ćwiczeń: `src/main/java/pl/sages/jext/...`, testy: `src/test/java/pl/sages/jext/...`. W każdej klasie szukaj `TODO`, a zamiast `throw new UnsupportedOperationException("TODO")` wpisz rozwiązanie. Zadania oznaczone „dla chętnych” można pominąć. Nazwy pakietów, klas, metod i zmiennych są po angielsku, jak w kodzie produkcyjnym; instrukcje w komentarzach i komunikaty testów są po polsku.

| Nr | Temat | Slajdy | Klasa (pakiet `pl.sages.jext`) | Test |
|---|---|---|---|---|
| 0 | Sprawdzenie środowiska | 7-8 | `ch0.setup.Start` | `EnvironmentTest` |
| 1 | Nowości w Javie: quiz zamiast kodu (13 fragmentów, głosowanie A/B/C) | 11-38 | quiz prowadzi trener na slajdach | |
| 2 | a) własna adnotacja `@Mask` od zera do gotowego kodu; b) walidator `@NotBlank` refleksją; c) dla chętnych: ClassGraph | 42-60 | `ch2.practice.Masking`, `Account`, `Validator`, `EntityScanner` | `MaskingTest`, `ValidatorTest` |
| 3 | Generyki: `? extends`, `? super`, ograniczenia | 68-74 | `ch2.practice.Generics` | `GenericsTest` |
| 4 | Enum jako fabryka i strategia (lambdy w stałych) | 76-80 | `ch2.practice.Operation` | `OperationTest` |
| 5 | Dla chętnych: łączenie `Predicate` i `Function`, funkcje wyższego rzędu, memoizacja (omówione na slajdach) | 88-97 | `ch3.functional.Functions` | `FunctionsTest` |
| 6 | Sealed + rekordy + switch: wynik płatności w sklepie (wzorce rekordów, `when`, `_`); e) dla chętnych: raport odmów | 100-117 | `ch3.functional.PaymentResult`, `Payments` | `PaymentsTest` |
| 7 | Dla chętnych: `Files.lines`, `groupingBy`, `partitioningBy`, `teeing` na CSV zamówień | dla chętnych, bez slajdu | `ch4.streams.Reports` | `ReportsTest` |
| 8 | Gatherers: `windowFixed`, `windowSliding`, `scan`; d) dla chętnych: własny Gatherer | 137-139 | `ch4.streams.Windows` | `WindowsTest` |
| 9 | 10 000 zadań: pula wątków kontra wątki wirtualne | 140-152 | `ch5.threads.Threads` | `ThreadsTest` |
| 10 | Zadania równolegle na wątkach wirtualnych (`Future`, `invokeAny`: pierwszy wygrywa) i `ScopedValue` | 150-154 | `ch5.threads.StructuredTasks` | `StructuredTasksTest` |
| 11 | Sealed + rekordy do JSON i z powrotem (Jackson) | 166-168 | `ch6.libraries.Payment`, `Json` | `JsonTest` |
| 12 | JPMS w konsoli: trzy moduły i `ServiceLoader` | 178-188 | folder `jpms/` | instrukcja w [jpms/README.md](jpms/README.md) |
| 13 | Mini-sklep: od JSON-a do raportu, 60-90 minut | powtórka | `ch8.project.MiniShop`, `ReportMapper`, `GatewayBenchmark` | `MiniShopTest` |
| pokaz | FFM (`strlen`); pozostały kod pokazu dla chętnych | 173-177 | `ch7.optimization.Demos` | |

**Lambdy i strumienie krok po kroku** (pakiet `pl.sages.jext.lambdastreams`, slajdy 85-139, prowadzi trener): rozgrzewka przed ćwiczeniami 5-8 i zadania dla chętnych. Kolejność jest w numerach pakietów:

| Krok | Temat | Pakiet (`pl.sages.jext.lambdastreams`) | Test |
|---|---|---|---|
| 1 | Od klasy do lambdy: przycisk (Sposób I-V), równanie, forEach; zadania Operation i Printer | `step1.lambda` | `LambdaTasksTest` |
| 2 | 5 funkcji gotowymi interfejsami, dzielenie z wyjątkiem, `Optional` | `step2.functional` | `FiveFunctionsTest`, `EquationTasksTest`, `PersonServiceTest` |
| 3 | Strumienie: 10 liczb; osoby i zwierzęta (`map`, `flatMap`, `mapToInt`) | `step3.streams` | `NumbersTasksTest`, `PeopleTasksTest` |
| 4 | StreamChallange P1: `MyStreamService` i `MyPerson` | `step4.challenge1` | `MyStreamServiceTest` |
| 5 | StreamChallange P2: sklep, klienci, zamówienia, dwie mapy „trudne” | `step5.challenge2` | `ShopTest` |

Dane do ćwiczeń 5 i 7: rekord `data.Order` i plik `src/main/resources/orders.csv` (24 zamówienia, separator `;`). Gotowa lista: `Orders.all()`.

**Ćwiczenie 1** nie ma kodu ani rozwiązania w projekcie: quiz A/B/C prowadzi trener na slajdach 11-38.

**Ćwiczenie 7:** dla chętnych, bez slajdu. Zaawansowane kolektory są poza kursem, także punkt f) z `teeing`.

**ScopedValue (ćw. 10c):** slajdy 150-154 uzupełniają ThreadLocal i pokazują StructuredTaskScope; ScopedValue jest stabilne od Javy 25 (JEP 506).

### Rozwiązania przy zadaniach

Pod każdym zadaniem leży pełne rozwiązanie w komentarzach liniowych. Usuń odpowiedni `throw new UnsupportedOperationException("TODO")`, zaznacz linie między nagłówkiem `ROZWIĄZANIE` a `KONIEC ROZWIĄZANIA` i naciśnij Ctrl+/ w IntelliJ. Przy deklaracjach (np. sealed, stałe enuma, lambdy w polach) usuń cały fragment `SZKIELET:START` ... `SZKIELET:KONIEC`, zanim odkomentujesz rozwiązanie. Przy adnotacji Mask odkomentuj cały plik Mask.java. Importy i pomocnicze deklaracje, jeśli są potrzebne, także mają osobne bloki rozwiązań. Dla ćwiczenia 6d wykonaj ręcznie kroki z komentarza: to celowa demonstracja błędu kompilatora po dodaniu Pending, poza automatycznym przebiegiem testów.

### Ćwiczenie 13. Mini-sklep: od JSON-a do raportu

Pakiet `pl.sages.jext.ch8.project`. Wszystkie dane są fikcyjne. Pracuj kolejno, uruchamiając wybrany test: `mvn -o test -Dtest=MiniShopTest#loadOrders` (zmień nazwę metody według tabeli). Każdy etap zaczyna z czerwonym testem. Modele danych są gotowe, zadania są w MiniShop i ReportMapper. Używamy wyłącznie rekordów, sealed, switch ze wzorcami i when, Jacksona, strumieni, gathererów, wątków wirtualnych, Semaphore i MapStruct, znanych z kursu.

| Etap | Minuty | Zadanie i warunek ukończenia | Test |
|---|---:|---|---|
| a | 10 | Wczytaj `/mini-shop-orders.json` Jacksonem do List<Order>; rozpoznaj Card, Blik i Transfer po polu type. | loadOrders |
| b | 15 | Switch z wzorcami rekordów i when: BLIK ma dokładnie 6 cyfr ASCII, karta nie jest pusta, limit przelewu wynosi 1000.00 włącznie; kwota musi być dodatnia. Wynik to Accepted albo Rejected z powodem. | validateOrders, transferBoundary, invalidAmounts |
| c | 20 | Obsłuż równolegle zamówienia, zachowaj kolejność wyników. Bramka śpi 50 ms tylko dla zaakceptowanych płatności; Semaphore(20) ogranicza jednoczesne wywołania. Zwolnij pozwolenie w finally. Porównaj 10 000 poprawnych zamówień na wątkach wirtualnych i puli 10. | processOrders, gatewayLimit |
| d | 15 | groupingBy + reducing: suma kwot na klienta; groupingBy + counting: liczby odrzuceń; top 3 klientów malejąco po sumie, przy remisie alfabetycznie. | report, emptyReport |
| d+ (chętni) | 10 | Partie po 100 przez Gatherers.windowFixed; ostatnia partia może być krótsza. | batches |
| e (chętni) | 20 | MapStruct mapuje raport do DTO i zamówienie do bezpiecznego OrderDto; nie dodawaj pola Payment ani numeru karty. Zapisz DTO raportu Jacksonem. | safeDto, exportReport |

Wariant 60 minut: a-d. Wariant 70 minut: a-d oraz d+. Wariant 80 minut: a-d oraz e. Pełny wariant: 90 minut. Przy braku czasu zakończ po dowolnym etapie; pozostałe testy pozostają czerwone.

Po rozwiązaniu c uruchom `mvn -o compile exec:exec -Dmain=pl.sages.jext.ch8.project.GatewayBenchmark`. Program wykonuje oba przebiegi po 10 000 płatności z opóźnieniem 50 ms i wypisuje rzeczywiste czasy oraz maksimum wywołań bramki. Minimalny czas samego oczekiwania to około 25 s dla limitu 20 i około 50 s dla puli 10; dochodzi narzut systemu. Nie wpisuj oczekiwanego wyniku jako wyniku pomiaru. Zwykłe testy sprawdzają współbieżność na mniejszych próbkach, bez sztywnych progów czasu; benchmark zajmuje około 75 s lub więcej.

## 5. Przykłady ze slajdów

Kod ze slajdów w wersji, która się kompiluje i działa: trener uruchamia go na żywo, a ty możesz go potem zmieniać. Pakiet `pl.sages.jext.examples`, podpakiet na rozdział, jedna klasa z `main` na slajd albo grupę slajdów; numer slajdu jest w Javadoc klasy. Gdzie slajd miał błąd, kod jest poprawiony, a komentarz mówi co. Linie, które według slajdu się nie kompilują, są zakomentowane: odkomentuj jedną i zobacz komunikat kompilatora.

Uruchomienie: zielona strzałka przy `main` w IntelliJ albo `mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.UnnamedVariables` (pełna nazwa klasy z tabeli).

<!-- PRZYKLADY:START -->
| Slajdy | Klasa (`pl.sages.jext.examples.`) | Co pokazuje |
|---|---|---|
| 13-14 | `ch1.newfeatures.SupportedFeatures` | `var` w pętli i try-with-resources, pułapki `var` |
| 15, 17-19 | `ch1.newfeatures.UnnamedVariables` | `_` w pętli, `catch`, try z zasobem, kolejce, lambdzie i wzorcach |
| 20-22 | `ch1.newfeatures.FlexibleConstructors` | kod przed `super()`: walidacja, przypisanie pola, co jest zabronione |
| 23-26 | `ch1.newfeatures.CollectionFactories` | `List.of`, `copyOf`, `Arrays.asList`, `Set.of`, `Map.ofEntries`: co rzuca wyjątek |
| 27-28 | `ch1.newfeatures.SequencedCollections` | `addFirst`, `getLast`, `SequencedMap`, `reversed()` jako widok |
| 29 | `scripts/Hello.java`, `scripts/ImportModule.java` | `void main()`, `IO` i `import module`: uruchom `java scripts/Hello.java` |
| 42 | `ch2.annotations.ApplyingAnnotations` | adnotacje na klasie, polu, konstruktorze, metodzie i typie, odczyt refleksją |
| 43-45 | `ch2.annotations.ValueAttribute` | atrybuty, `default`, skrót `value` |
| 46-48 | `ch2.annotations.RetentionTarget` | `SOURCE`, `CLASS`, `RUNTIME`: co widzi refleksja; `@Target` |
| 49-52 | `ch2.annotations.AnnotationInstance` | rekord udający adnotację; rekordowy `hashCode` psuje `HashSet` |
| 56 | `ch2.annotations.RepeatableInherited` | `@Repeatable` z kontenerem, `@Inherited` |
| 57-58 | `ch2.annotations.ArangoIndexInitializer` | ClassGraph znajduje `@Entity`, indeksy idą do zaślepki bazy (wypisuje `createIndex(...)`) |
| 60 | `ch2.annotations.ClassGraphRoute` | wyszukanie klas z `@Route` przez ClassGraph |
| 63 | `ch2.annotations.JandexRoute` | indeks Jandex z `META-INF/jandex.idx` (buduje go `mvn compile`) |
| 55, 64-65 | `ch2.annotations.TimedProxy` | aspekt `@Timed` przez proxy JDK; wywołanie obok proxy; adnotacja na implementacji |
| 66-67 | `ch2.annotations.AsyncSelfInvocationTrap` | pułapka `this.metoda()` przy `@Async`: 5 importów po kolei zamiast równolegle |
| 68-73 | `ch2.generics.Wildcards` | klasa generyczna, `<?>`, `? extends`, `? super` |
| 74 | `ch2.generics.MultipleBounds` | `<T extends Number & Comparable<T>>` |
| 75 | `ch2.generics.FluentApi` | rekurencyjny typ generyczny i typ z varargs w czasie działania |
| 76-79 | `ch2.enums.EnumsAndFactories` | enum z polami, metody abstrakcyjne w stałych, enum z interfejsem, enum-fabryka |
| 80-81 | `ch2.enums.SingletonFlyweight` | enum-singleton, Flyweight z pulą i ze stałymi enuma |
| 82 | `ch2.sealedtypes.SealedSoldiers` | `sealed`, `permits`, `final`, `non-sealed`, switch bez `default` |
| 87-90 | `ch3.functional.LambdasAndReferences` | czysta funkcja, lambdy, efektywnie finalne zmienne, referencje do metod |
| 91-93 | `ch3.functional.PredicateAndFunction` | `Predicate` (`not`, `and`, `or`), `Function.andThen`, lambdy w metodach kolekcji |
| 94-97 | `ch3.functional.HigherOrderAndCurrying` | funkcja wyższego rzędu, curryfikacja, memoizacja przez `CachedSupplier` |
| 101-103, 112 | `ch3.functional.Immutability` | typ niemutowalny bez rekordu, rekordy, konstruktor kompaktowy, rekord z mutowalnym komponentem |
| 105-110, 114-115, 117 | `ch3.functional.PatternMatching` | wzorce w `if` i `switch`, wyczerpujący switch, `when`, wzorce rekordów, Data Oriented Programming |
| 119-122, 124 | `ch3.functional.FunctionalPatterns` | strategia, fabryka-enum z referencjami do konstruktorów, dekorator, łańcuch odpowiedzialności |
| 123 | `ch3.functional.BaristaDecorator` | dekorator z kawą: jedna dekoracja i wiele złożonych przez `reduce` |
| 126-131 | `ch4.streams.OptionalAndExceptions` | `Optional` w łańcuchu i jako strumień, wyjątki w lambdach, `Either`, `Try` |
| 132-135 | `ch4.streams.PrimitiveStreamsAndNio` | strumienie prymitywne, NIO (`walk`, `find`, `lines`), dalsze operacje w kodzie jako uzupełnienie |
| 136 | `ch4.streams.CollectorExamples` | Strumień równoległy; wszystkie kolektory w tej klasie: dla chętnych, bez slajdu |
| 137-139 | `ch4.streams.GathererExamples` | `Gatherers` (`windowFixed`, `windowSliding`, `fold`, `scan`, `mapConcurrent`), własne gatherery |
| 143, 152 | `ch5.threads.VirtualThreads` | wątek wirtualny, 1000 wątków w `synchronized` bez przypinania (Java 24+) |
| 149 | `scripts/ContinuationDemo.java` | `Continuation` pod wątkami wirtualnymi (API wewnętrzne, polecenia w nagłówku) |
| 157-158 | schemat na slajdach | Lombok / MapStruct / Jackson: role i współpraca |
| 161 | `ch6.libraries.extensions.CalculatorTester` | Lombok `@ExtensionMethod` |
| 162 | `ch6.libraries.SuperBuilderDemo` | Lombok `@SuperBuilder` z dziedziczeniem i `toBuilder` |
| 163 | `ch6.libraries.fluent.AccessorsWithDemo` | Lombok `@Accessors(fluent = true)` i `@With` |
| 164-165 | `ch6.libraries.mapping.EntityDtoDemo` | MapStruct: encja -> DTO, nazwy pól, pominięcia i kolekcje |
| 167-168 | `ch6.libraries.JacksonHierarchy` | Jackson i hierarchia typów: `NAME` z polem i `DEDUCTION` |
| 172-175 | `ch7.optimization.ProcessesAndMemory` | `ProcessHandle`, FFM API: arena, segmenty, `strlen` i `qsort` z porównaniem w Javie |
| 176-177 | `ch7.optimization.TextHandling` | `StringBuilder` i quiz z niuansów String; dalszy kod to materiał uzupełniający |
<!-- PRZYKLADY:STOP -->

StructuredTaskScope na slajdach 153-154 jest preview Javy 25; ćwiczenie 10 używa stabilnego ExecutorService. Frameworki (slajd 155) wymagają osobnej aplikacji. Schematy JPMS (slajdy 178-188) ćwiczysz w folderze jpms.

## 6. Gdy coś nie działa

| Objaw | Przyczyna i rozwiązanie |
|---|---|
| `release version 25 not supported` | Maven albo IntelliJ używa starszej Javy. Ustaw `JAVA_HOME` na JDK 25+, w IntelliJ SDK 25+ (punkt 2) |
| `invalid source release 25 with --enable-preview` | W IntelliJ ustawiony Language level „25 (Preview)”. Zmień na `SDK default` albo `25` (punkt 2) i Reload All Maven Projects |
| `cannot find symbol` przy `builder()`, `getFirstName()` albo w `EmployeeMapper` | Nie działa procesor adnotacji. Reload All Maven Projects, potem Build → Rebuild Project |
| `UnsupportedClassVersionError ... compiled by a more recent version` przy uruchamianiu | Klasa uruchomiona na starszej Javie niż ta, którą ją skompilowano. W IntelliJ: Run → Edit Configurations → JRE: JDK 25+ |
| Maven nie pobiera bibliotek (sieć firmowa, proxy) | Ustaw proxy w `~/.m2/settings.xml` albo poproś trenera o paczkę z bibliotekami |
| Krzaczki zamiast polskich liter w konsoli Windows | Uruchom `chcp 65001` przed komendą |
