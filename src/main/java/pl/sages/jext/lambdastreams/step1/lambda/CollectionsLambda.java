package pl.sages.jext.lambdastreams.step1.lambda;

import java.util.List;

public class CollectionsLambda {
    static void main() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        for (Integer number : numbers) {
            System.out.println(number);
        }
        numbers.forEach(  number -> System.out.println(number)  );
        numbers.forEach(System.out::println);

    }
}
