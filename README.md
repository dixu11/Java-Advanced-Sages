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
| Klasa z metodą `main` | `mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch4.streams.RandomNumbersDemo` |
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
| 5 | Dla chętnych: łączenie `Predicate` i `Function`, funkcje wyższego rzędu, memoizacja (omówione na slajdach) | 86-95 | `ch3.functional.Functions` | `FunctionsTest` |
| 6 | Sealed + rekordy + switch: wynik płatności w sklepie (wzorce rekordów, `when`, `_`); e) dla chętnych: raport odmów | 98-115 | `ch3.functional.PaymentResult`, `Payments` | `PaymentsTest` |
| 7 | `Files.lines`, `groupingBy`, `partitioningBy`, `teeing` na CSV zamówień | 130-140 | `ch4.streams.Reports` | `ReportsTest` |
| 8 | Gatherers: `windowFixed`, `windowSliding`, `scan`; d) dla chętnych: własny Gatherer | 142-146 | `ch4.streams.Windows` | `WindowsTest` |

**Lambdy i strumienie krok po kroku** (pakiet `pl.sages.jext.lambdastreams`): rozgrzewka przed ćwiczeniami 5-8 i zadania dla chętnych. Kolejność jest w numerach pakietów:

| Krok | Temat | Pakiet (`pl.sages.jext.lambdastreams`) | Test |
|---|---|---|---|
| 1 | Od klasy do lambdy: przycisk (Sposób I-V), równanie, forEach; zadania Operation i Printer | `step1.lambda` | `LambdaTasksTest` |
| 2 | 5 funkcji gotowymi interfejsami, dzielenie z wyjątkiem, `Optional` | `step2.functional` | `FiveFunctionsTest`, `EquationTasksTest`, `PersonServiceTest` |
| 3 | Strumienie: 10 liczb; osoby i zwierzęta (`map`, `flatMap`, `mapToInt`) | `step3.streams` | `NumbersTasksTest`, `PeopleTasksTest` |
| 4 | StreamChallange P1: `MyStreamService` i `MyPerson` | `step4.challenge1` | `MyStreamServiceTest` |
| 5 | StreamChallange P2: sklep, klienci, zamówienia, dwie mapy „trudne” | `step5.challenge2` | `ShopTest` |

Dane do ćwiczeń 5 i 7: rekord `data.Order` i plik `src/main/resources/orders.csv` (24 zamówienia, separator `;`). Gotowa lista: `Orders.all()`.

**Ćwiczenie 1** nie ma kodu: to quiz na slajdach.


## 5. Przykłady ze slajdów

Kod ze slajdów w wersji, która się kompiluje i działa: trener uruchamia go na żywo, a ty możesz go potem zmieniać. Pakiet `pl.sages.jext.examples`, podpakiet na rozdział, jedna klasa z `main` na slajd albo grupę slajdów; numer slajdu jest w Javadoc klasy. Gdzie slajd miał błąd, kod jest poprawiony, a komentarz mówi co. Linie, które według slajdu się nie kompilują, są zakomentowane: odkomentuj jedną i zobacz komunikat kompilatora.

Uruchomienie: zielona strzałka przy `main` w IntelliJ albo `mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.UnnamedVariables` (pełna nazwa klasy z tabeli).

<!-- PRZYKLADY:START -->
| Slajdy | Klasa (`pl.sages.jext.examples.`) | Co pokazuje |
|---|---|---|
| 13-14 | `ch1.newfeatures.SupportedFeatures` | `var` w pętli i try-with-resources, pułapki `var` |
| 15, 17, 18, 19 | `ch1.newfeatures.UnnamedVariables` | `_` w pętli, `catch`, try z zasobem, kolejce, lambdzie i wzorcach |
| 20, 21, 22 | `ch1.newfeatures.FlexibleConstructors` | kod przed `super()`: walidacja, przypisanie pola, co jest zabronione |
| 23-26, 24 | `ch1.newfeatures.CollectionFactories` | `List.of`, `copyOf`, `Arrays.asList`, `Set.of`, `Map.ofEntries`: co rzuca wyjątek |
| 27, 28 | `ch1.newfeatures.SequencedCollections` | `addFirst`, `getLast`, `SequencedMap`, `reversed()` jako widok |
| 29 | `scripts/Hello.java`, `scripts/ImportModule.java` | `void main()`, `IO` i `import module`: uruchom `java scripts/Hello.java` |
| 42 | `ch2.annotations.ApplyingAnnotations` | adnotacje na klasie, polu, konstruktorze, metodzie i typie, odczyt refleksją |
| 43-44, 45 | `ch2.annotations.ValueAttribute` | atrybuty, `default`, skrót `value` |
| 46-48, 47 | `ch2.annotations.RetentionTarget` | `SOURCE`, `CLASS`, `RUNTIME`: co widzi refleksja; `@Target` |
| 49-51, 52 | `ch2.annotations.AnnotationInstance` | rekord udający adnotację; rekordowy `hashCode` psuje `HashSet` |
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
| 85-88 | `ch3.functional.LambdasAndReferences` | czysta funkcja, lambdy, efektywnie finalne zmienne, referencje do metod |
| 89-91 | `ch3.functional.PredicateAndFunction` | `Predicate` (`not`, `and`, `or`), `Function.andThen`, lambdy w metodach kolekcji |
| 92-95 | `ch3.functional.HigherOrderAndCurrying` | funkcja wyższego rzędu, curryfikacja, memoizacja przez `CachedSupplier` |
| 99-101, 110 | `ch3.functional.Immutability` | typ niemutowalny bez rekordu, rekordy, konstruktor kompaktowy, rekord z mutowalnym komponentem |
| 103-108, 112-113, 115 | `ch3.functional.PatternMatching` | wzorce w `if` i `switch`, wyczerpujący switch, `when`, wzorce rekordów, Data Oriented Programming |
| 117-120, 122 | `ch3.functional.FunctionalPatterns` | strategia, fabryka-enum z referencjami do konstruktorów, dekorator, łańcuch odpowiedzialności |
| 121 | `ch3.functional.BaristaDecorator` | dekorator z kawą: jedna dekoracja i wiele złożonych przez `reduce` |
| 124-129 | `ch4.streams.OptionalAndExceptions` | `Optional` w łańcuchu i jako strumień, wyjątki w lambdach, `Either`, `Try` |
| 130-135 | `ch4.streams.PrimitiveStreamsAndNio` | strumienie prymitywne, NIO (`walk`, `find`, `lines`), `mapMulti`, nietypowe źródła strumieni |
| 136-141 | `ch4.streams.CollectorExamples` | `groupingBy`, `partitioningBy`, `reducing`, `flatMapping`, `filtering`, `teeing`, strumień równoległy |
| 142-146 | `ch4.streams.GathererExamples` | `Gatherers` (`windowFixed`, `windowSliding`, `fold`, `scan`, `mapConcurrent`), własne gatherery |
<!-- PRZYKLADY:STOP -->


## 6. Gdy coś nie działa

| Objaw | Przyczyna i rozwiązanie |
|---|---|
| `release version 25 not supported` | Maven albo IntelliJ używa starszej Javy. Ustaw `JAVA_HOME` na JDK 25+, w IntelliJ SDK 25+ (punkt 2) |
| `invalid source release 25 with --enable-preview` | W IntelliJ ustawiony Language level „25 (Preview)”. Zmień na `SDK default` albo `25` (punkt 2) i Reload All Maven Projects |
| `cannot find symbol` przy `builder()`, `getFirstName()` albo w `EmployeeMapper` | Nie działa procesor adnotacji. Reload All Maven Projects, potem Build → Rebuild Project |
| `UnsupportedClassVersionError ... compiled by a more recent version` przy uruchamianiu | Klasa uruchomiona na starszej Javie niż ta, którą ją skompilowano. W IntelliJ: Run → Edit Configurations → JRE: JDK 25+ |
| Maven nie pobiera bibliotek (sieć firmowa, proxy) | Ustaw proxy w `~/.m2/settings.xml` albo poproś trenera o paczkę z bibliotekami |
| Krzaczki zamiast polskich liter w konsoli Windows | Uruchom `chcp 65001` przed komendą |
