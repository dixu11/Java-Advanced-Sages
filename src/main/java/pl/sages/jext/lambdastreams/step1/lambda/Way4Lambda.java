package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * Krok 1, Sposób IV: współczesny - wyrażenia lambda i interfejsy funkcyjne (Comarch 2025, FunkcyjneDemo).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.Way4Lambda}
 */
public class Way4Lambda {

    public static void main(String[] args) {
        //Sposób IV współczesny - wyrazenia lambda i interfejsy funkcyjne
        Button button1 = new Button("Start");
        Button button2 = new Button("Koniec");
        ButtonAction action1 = new ButtonAction() {
            @Override
            public void clickAction() {
                System.out.println("startuj");
            }
        };

        //powyższy kod zredukowany do wyrażenia lambda
        ButtonAction action2 = () -> System.out.println("Zamknij");

        // button1.setAction(action1);
        button1.setAction(() -> System.out.println("Start"));
        button2.setAction(action2);
        button1.click();
        button2.click();
    }
}
