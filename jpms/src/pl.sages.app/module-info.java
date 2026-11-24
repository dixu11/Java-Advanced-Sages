/**
 * Moduł aplikacji. Nie zna modułu pl.sages.greetings.polish: dostaje implementację przez ServiceLoader.
 */
module pl.sages.app {
    // TODO 4: wymagaj modułu z interfejsem Greeting
    // TODO 5: zadeklaruj, że aplikacja KORZYSTA z usługi pl.sages.greetings.api.Greeting (uses ...)
    requires pl.sages.greetings.api;
    
    uses pl.sages.greetings.api.Greeting;
}
