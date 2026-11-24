// Slajd 149: Continuation, czyli mechanizm pod wątkami wirtualnymi. To API WEWNĘTRZNE JDK (jdk.internal.vm):
// bez dodatkowej flagi się nie skompiluje, a w kodzie produkcyjnym nie używamy go nigdy.
// Uruchomienie z folderu projektu (tryb "java Plik.java" kompiluje z --release i odrzuca --add-exports,
// dlatego najpierw javac, potem java):
//   javac --add-exports java.base/jdk.internal.vm=ALL-UNNAMED -d target-scripts scripts/ContinuationDemo.java
//   java --add-exports java.base/jdk.internal.vm=ALL-UNNAMED -cp target-scripts ContinuationDemo
import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

void main() {
    var scope = new ContinuationScope("hello");
    var continuation = new Continuation(scope, () -> {
        System.out.println("C1");
        Continuation.yield(scope);
        System.out.println("C2");
        Continuation.yield(scope);
    });
    System.out.println("start");
    continuation.run(); // wykonujemy kod w lambdzie do pierwszego yield
    System.out.println("came back");
    continuation.run(); // wykonujemy kod w lambdzie do drugiego yield
    System.out.println("back again");
}
