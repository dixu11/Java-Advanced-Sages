package pl.sages.lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ExecutorDemo {

    private static final int NUMBERS_TO_GENERATE = 25_000_000;
    static void main() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<List<Integer>> callable = new Callable() {
            @Override
            public List<Integer> call() {
                return new Random()
                        .ints(NUMBERS_TO_GENERATE, 1, 11)
                        .boxed()
                        .toList();
            }
        };
        List<Future<List<Integer>>> futures = new ArrayList<>();

        var future1 = executorService.submit(callable);
        var future2 = executorService.submit(() -> new Random()
                .ints(NUMBERS_TO_GENERATE, 1, 11)
                .boxed()
                .toList());
        System.out.println("Zadania pracują");
        futures.add(future1);
        futures.add(future2);
        List<List<Integer>> results = new ArrayList<>();
        for (Future<List<Integer>> future : futures) {
            System.out.println("Zabieram wynik z wątków");
            System.out.println("Czy już skończył?" + future.isDone());
            results.add(future.get());
            System.out.println("Czy już skończył?" + future.isDone());
        }
        long sum = results.stream()
                .flatMap(lista -> lista.stream())
                .mapToInt(number -> number)
                .sum();
        System.out.println(sum);
    }
}
