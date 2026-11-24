/**
 * Moduł dostarczający implementację usługi Greeting.
 * Zwróć uwagę: NIE eksportuje żadnego pakietu, a i tak jego klasa zostanie użyta przez ServiceLoader.
 */
module pl.sages.greetings.polish {
    // TODO 2: ten moduł korzysta z interfejsu Greeting, więc musi wymagać modułu pl.sages.greetings.api
    // TODO 3: zadeklaruj, że dostarcza usługę pl.sages.greetings.api.Greeting
    //         z implementacją pl.sages.greetings.polish.PolishGreeting (provides ... with ...)
    requires pl.sages.greetings.api;
    
    provides pl.sages.greetings.api.Greeting with pl.sages.greetings.polish.PolishGreeting;
}
