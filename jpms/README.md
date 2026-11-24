# Ćwiczenie 12: JPMS w konsoli (slajdy 178-188)

Trzy moduły, bez Mavena i bez IDE, tylko `javac` i `java` z JDK 25:

| Moduł | Co robi |
|---|---|
| `pl.sages.greetings.api` | interfejs usługi `Greeting` + ukryty pakiet `internal` |
| `pl.sages.greetings.polish` | implementacja `PolishGreeting` (dostawca usługi) |
| `pl.sages.app` | `Main`, który szuka implementacji przez `ServiceLoader` |

Pliki `module-info.java` mają `TODO 1-5`. Dopóki ich nie uzupełnisz, kompilacja się nie uda: czytaj komunikaty `javac`, one mówią, czego brakuje.

## Komendy

Uruchamiaj z tego folderu (`jpms`). Komendy są takie same w PowerShell (Windows), Bash (Linux) i Terminalu (Mac). Listę modułów piszemy w cudzysłowie, bo PowerShell traktuje przecinek jako tablicę.

Sprawdzenie, że to JDK 25:

```
java -version
```

Kompilacja wszystkich modułów naraz (`--module` kompiluje podane moduły i to, czego wymagają):

```
javac -d out --module-source-path src --module "pl.sages.app,pl.sages.greetings.polish"
```

Uruchomienie:

```
java --module-path out --module pl.sages.app/pl.sages.app.Main
```

Oczekiwany wynik:

```
[pl] Dzień dobry, Kursancie
Moduł aplikacji: pl.sages.app
```

Jeśli polskie znaki w konsoli Windows są „krzaczkami”, uruchom najpierw `chcp 65001`.

## Kroki dodatkowe

1. **Enkapsulacja:** w `Main` dodaj `import pl.sages.greetings.api.internal.Formatting;` i użyj `Formatting.exclaim(...)`. Skompiluj i przeczytaj błąd: klasa jest `public`, a i tak niewidoczna. Usuń import.
2. **Usługa jest opcjonalna:** skompiluj tylko aplikację, bez dostawcy: `javac -d out2 --module-source-path src --module pl.sages.app`, uruchom z `--module-path out2`. Aplikacja działa, ale nie znajduje implementacji.
3. **Lista modułów i zależności:** `java --module-path out --describe-module pl.sages.app` oraz `jdeps --module-path out -m pl.sages.app`.
4. **Własny, mały obraz JVM (slajd 188):**

```
jlink --module-path out --add-modules "pl.sages.app,pl.sages.greetings.polish" --output image --launcher greet=pl.sages.app/pl.sages.app.Main
```

Potem `image/bin/greet` (Linux/Mac) albo `image\bin\greet.bat` (Windows). Porównaj rozmiar folderu `image` z całym JDK.

Sprzątanie przed kolejną próbą: usuń foldery `out`, `out2`, `image` (jlink nie nadpisze istniejącego folderu).

## Rozwiązanie

W każdym pliku module-info.java odkomentuj linie pomiędzy nagłówkiem ROZWIĄZANIE a KONIEC ROZWIĄZANIA. Gotowe dyrektywy:

- `pl.sages.greetings.api`: `exports pl.sages.greetings.api;` (internal pozostaje ukryty).
- `pl.sages.greetings.polish`: `requires pl.sages.greetings.api;` oraz `provides pl.sages.greetings.api.Greeting with pl.sages.greetings.polish.PolishGreeting;`.
- `pl.sages.app`: `requires pl.sages.greetings.api;` oraz `uses pl.sages.greetings.api.Greeting;`.

Uruchom z folderu jpms po odkomentowaniu:

```text
javac --release 25 -encoding UTF-8 -d out --module-source-path src --module "pl.sages.app,pl.sages.greetings.polish"
java --module-path out --module pl.sages.app/pl.sages.app.Main
java --module-path out --describe-module pl.sages.app
javac --release 25 -encoding UTF-8 -d out2 --module-source-path src --module pl.sages.app
java --module-path out2 --module pl.sages.app/pl.sages.app.Main
```

Pierwsze uruchomienie znajduje polskie powitanie. Wariant out2 działa bez dostawcy i informuje o braku implementacji. Foldery wyjściowe muszą być nowe lub wcześniej usunięte, inaczej stara kompilacja może zafałszować wynik.
