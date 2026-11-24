package pl.sages.jext.lambdastreams.step1.lambda;

public class ThreatDemo {
    static void main() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Spam thread 1");
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread thread2 = new Thread(() -> {
            while (true) {
                System.out.println("Spam thread 2");
            }
        }
        );
        thread2.start();
    }
}
