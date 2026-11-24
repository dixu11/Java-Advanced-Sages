// Slajd 29: kompaktowy plik źródłowy i metoda main instancji (JEP 512, finalne w Javie 25).
// Bez deklaracji klasy, bez public static i bez String[] args; java.base importuje się sam.
// Uruchomienie bez kompilacji, z folderu projektu:  java scripts/Hello.java
void main() {
    var name = IO.readln("Jak masz na imię? ");
    IO.println("Cześć, " + name + "! Dziś " + LocalDate.now());
}
