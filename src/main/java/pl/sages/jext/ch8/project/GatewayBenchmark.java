package pl.sages.jext.ch8.project;

import java.math.BigDecimal;
import java.util.stream.IntStream;

/**
 * Etap c: uruchom po rozwiązaniu validate i process. To ręczny pomiar, poza mvn test.
 * 10 000 poprawnych płatności, każda 50 ms, Semaphore(20). Dwa przebiegi: około 75 s.
 * Pula 10 ogranicza dodatkowo współbieżność do 10. Nie zakładamy z góry przyspieszenia.
 */
public class GatewayBenchmark {
    public static void main(String[] args) throws Exception {
        var orders = IntStream.range(0, 10_000).mapToObj(i ->
            new Order("BENCH-" + i, "Klient-" + (i % 10), new BigDecimal("10.00"),
                new Payment.Blik("123456"))).toList();
        for (boolean virtual : new boolean[]{false, true}) {
            var gateway = new PaymentGateway();
            long start = System.nanoTime();
            var results = MiniShop.process(orders, virtual, gateway);
            double seconds = (System.nanoTime() - start) / 1_000_000_000.0;
            if (results.size() != 10_000 || gateway.calls() != 10_000)
                throw new IllegalStateException("Nie wszystkie płatności obsłużono");
            int expectedLimit = virtual ? 20 : 10;
            if (gateway.maxConcurrentCalls() > expectedLimit || gateway.permits().availablePermits() != 20)
                throw new IllegalStateException("Naruszony limit lub utracone pozwolenie");
            System.out.printf("%s: %.3f s, zamówienia=%d, płatności=%d, maksimum bramki=%d%n",
                virtual ? "Wątki wirtualne" : "Pula 10", seconds, results.size(), gateway.calls(), gateway.maxConcurrentCalls());
        }
    }
}
