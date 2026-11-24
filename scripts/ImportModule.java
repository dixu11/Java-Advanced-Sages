// Slajd 29: import całego modułu (JEP 511, finalne w Javie 25).
// Uruchomienie bez kompilacji, z folderu projektu:  java scripts/ImportModule.java
import module java.base;     // List, Map, Path, Files, LocalDate, Stream...
import module java.desktop;  // uwaga: ma java.awt.List, a java.base ma java.util.List
import java.util.List;       // konflikt nazw rozstrzygasz zwykłym importem

void main() throws IOException {
    List<String> names = List.of("Path", "Files", "LocalDate");           // java.util.List dzięki jawnemu importowi
    IO.println("Z java.base bez importów pakietów: " + names);
    IO.println("Pliki w bieżącym folderze: " + Files.list(Path.of(".")).count()); // java.nio.file
    IO.println("Z java.desktop: " + new Point(3, 4) + ", odległość od (0,0) = " + new Point(3, 4).distance(0, 0));
    // Usuń linię „import java.util.List;”, a kompilator zgłosi: reference to List is ambiguous
}
