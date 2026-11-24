package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * Krok 1, Sposób III: stworzenie interfejsu jako nośniku dla kodu - ma jedną metodę abstrakcyjną
 *.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.Way3Interface}
 */
public class Way3Interface {

    public static void main(String[] args) {
        //Sposób III - stworzenie interfejsu jako nośniku dla kodu - ma jedną metodę abstrakcyjną
        Button button1 = new Button("Start");
        Button button2 = new Button("Koniec");
        Button button3 = new Button("Powitanie");

        ButtonAction action1 = new ButtonAction() {
            @Override
            public void clickAction() {
                System.out.println("startuj");
            }
        };

        ButtonAction action2 = new ButtonAction() {
            @Override
            public void clickAction() {
                System.out.println("Zamknij");
            }
        };

        button1.setAction(action1);
        button2.setAction(action2);
        button3.setAction(new HelloAction()); // klasa implementująca interfejs
        button1.click();
        button2.click();
        button3.click();
    }
}
