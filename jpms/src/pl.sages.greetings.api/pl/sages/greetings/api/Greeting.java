package pl.sages.greetings.api;

/** Interfejs usługi (SPI). Implementacje dostarczają inne moduły. */
public interface Greeting {

    String language();

    String greet(String name);
}
