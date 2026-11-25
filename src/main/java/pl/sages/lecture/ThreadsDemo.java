package pl.sages.lecture;

public class ThreadsDemo {
    static void main() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Kod na wątku 1");
        });
        thread.start();

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Kod równoległy 2");
            }
        };
        thread2.start();
        thread.join();
        thread2.join();
        System.out.println("Koniec pracy obu");


    }
}
