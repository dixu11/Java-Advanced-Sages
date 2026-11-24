package pl.sages.greetings.polish;

import pl.sages.greetings.api.Greeting;

public class PolishGreeting implements Greeting {

    @Override
    public String language() {
        return "pl";
    }

    @Override
    public String greet(String name) {
        return "Dzień dobry, " + name;
    }
}
