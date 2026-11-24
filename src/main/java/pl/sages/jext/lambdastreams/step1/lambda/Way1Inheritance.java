package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * Krok 1, Sposób I: zwykłe dziedziczenie abstrakcyjnych metod (Comarch 2025, FunkcyjneDemo).
 * Różne klasy konkretnych przycisków różnie zdefiniują akcję kliknięcia.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.Way1Inheritance}
 */
public class Way1Inheritance {

    //Sposób I - zwykłe dziedziczenie abstrakcyjnych metod
    abstract static class Button {

        private final String text;

        Button(String text) {
            this.text = text;
        }

        public abstract void click();
    }

    static class StartButton extends Button {
        StartButton(String text) {
            super(text);
        }

        @Override
        public void click() {
            System.out.println("Startujemy");
        }
    }

    static class EndButton extends Button {
        EndButton(String text) {
            super(text);
        }

        @Override
        public void click() {
            System.out.println("Kończymy");
        }
    }

    public static void main(String[] args) {
        Button button1 = new StartButton("Start");
        Button button2 = new EndButton("Koniec");
        button1.click();
        button2.click();
        // pytanie do grupy: ile klas trzeba napisać, gdy przycisków będzie 20?
    }
}
