package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * Krok 1, Sposób II: anonimowe klasy.
 * Ten sam abstrakcyjny Button co w Sposobie I, ale bez osobnej klasy na każdy przycisk.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.Way2AnonymousClass}
 */
public class Way2AnonymousClass {

    abstract static class Button {

        private final String text;

        Button(String text) {
            this.text = text;
        }

        public abstract void click();
    }

    public static void main(String[] args) {
        //sposób II - anonimowe klasy
        Button button1 = new Button("Start") {
            @Override
            public void click() {
                System.out.println("Startujemy");
            }
        };
        Button button2 = new Button("Koniec") {
            @Override
            public void click() {
                System.out.println("Kończymy");
            }
        };
        button1.click();
        button2.click();
    }
}
