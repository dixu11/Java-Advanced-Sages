package pl.sages.lecture;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadProblems {
    static void main() {

        Runnable runnable = new Runnable() {

            private int counter;
            private AtomicInteger counter2 = new AtomicInteger(0);
            @Override
            public void run() {
                for (int i = 0; i < 10_000; i++) {
                    counter2.incrementAndGet();
                    //increment();
                }
                System.out.println("Wątek " + Thread.currentThread().getName() +
                        " zakończył pracę z counterem: " + counter2.get());
            }

            synchronized void increment() {
                counter++;
            }
        };


        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        System.out.println(Thread.currentThread().getName());





//
//        8888 + 1
//               -> 8889
              //STOP
        // 8888
        // 8889
        // 12 000

        // counter = 8889

    }
}
