package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * Krok 2: równanie od klasy anonimowej do lambdy.
 * Kolejność: klasa anonimowa → lambda z typami i klamrą → lambda skrócona → lambda przekazana do metody
 * → (nowe) referencja do metody.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step1.lambda.EquationDemo}
 */
public class EquationDemo {

    public static void main(String[] args) {
        MathOperation addition = new MathOperation() {
            @Override
            public int execute(int number1, int number2) {
                return number1 + number2;
            }
        };

        System.out.println(addition.execute(1, 2));

        MathOperation subtraction = (int number1, int number2) -> {
            return number1 - number2;
        };
        //pominięto zbędne fragmenty plus strzałka: () -> {}
        System.out.println(subtraction.execute(1, 2));

        //można pominąć typy
        //jeśli implementacja zajmuje jedną linijkę możemy pominąć klamrę i return
        //jeśli 1 parametr metody można pominąć nawias
        MathOperation subtraction2 = (number1, number2) -> number1 - number2;
        MathOperation multiplication = (num1, num2) -> num1 * num2;
        System.out.println(multiplication.execute(10, 20));

        useOperation(subtraction2);

        useOperation((number1, number2) -> number1 - number2);

        // nowe: gdy lambda tylko woła gotową metodę o pasującej sygnaturze, wystarczy referencja do metody
        MathOperation addition2 = Integer::sum;      // (a, b) -> Integer.sum(a, b)
        useOperation(addition2);
        useOperation(Math::max);                     // (a, b) -> Math.max(a, b)
    }

    public static void useOperation(MathOperation operation) {
        System.out.println("Testing:");
        System.out.println(operation.execute(1, 2));
    }
}
