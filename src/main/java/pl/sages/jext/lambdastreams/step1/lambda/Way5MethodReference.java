package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * nowe: Krok 1, Sposób V: referencja do metody. W starych materiałach była tylko w rozwiązaniach
 * ({@code Person::getCash}), tu jako osobny, ostatni krok rozkładu (slajdy 85-88).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.Way5MethodReference}
 */
public class Way5MethodReference {

    private int counter;

    void start() {
        System.out.println("Start (metoda obiektu)");
    }

    static void close() {
        System.out.println("Zamknij (metoda statyczna)");
    }

    void count() {
        counter++;
        System.out.println("Kliknięto " + counter + " raz(y)");
    }

    public static void main(String[] args) {
        Way5MethodReference app = new Way5MethodReference();
        Button button1 = new Button("Start");
        Button button2 = new Button("Koniec");
        Button button3 = new Button("Licznik");

        // lambda, która tylko woła jedną gotową metodę...
        button1.setAction(() -> app.start());
        // ...może być zastąpiona referencją do tej metody: obiekt::metoda
        button1.setAction(app::start);
        // metoda statyczna: Klasa::metoda
        button2.setAction(Way5MethodReference::close);
        // metoda zmienia pole obiektu, więc nie potrzebujemy zmiennej lokalnej w lambdzie
        button3.setAction(app::count);

        button1.click();
        button2.click();
        button3.click();
        button3.click();

        // cztery rodzaje referencji (slajd 88):
        //  Klasa::metodaStatyczna    Integer::parseInt      s -> Integer.parseInt(s)
        //  obiekt::metoda            System.out::println    x -> System.out.println(x)
        //  Klasa::metodaInstancji    String::toUpperCase    s -> s.toUpperCase()
        //  Klasa::new                ArrayList::new         () -> new ArrayList<>()
    }
}
