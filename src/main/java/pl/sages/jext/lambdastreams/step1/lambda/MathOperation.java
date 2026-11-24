package pl.sages.jext.lambdastreams.step1.lambda;

//interfejs funkcyjny to interfejs z dokładnie jedną metodą abstrakcyjną
//  możemy go implementować za pomocą wyrażeń lambda
@FunctionalInterface // opcjonalna adnotacja sprawdzająca
public interface MathOperation {
    int execute(int number1, int number2);
}
