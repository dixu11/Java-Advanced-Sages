package pl.sages.app;

import pl.sages.greetings.api.Greeting;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        ServiceLoader<Greeting> services = ServiceLoader.load(Greeting.class);
        int count = 0;
        for (Greeting greeting : services) {
            System.out.println("[" + greeting.language() + "] " + greeting.greet("Kursancie"));
            count++;
        }
        if (count == 0) {
            System.out.println("Brak implementacji usługi Greeting: sprawdź provides/uses w module-info.java");
        }
        System.out.println("Moduł aplikacji: " + Main.class.getModule().getName());
    }
}
