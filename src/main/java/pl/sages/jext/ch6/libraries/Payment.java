package pl.sages.jext.ch6.libraries;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Ćwiczenie 11a (slajdy 166-168): hierarchia sealed + rekordy w JSON (Jackson).
 *
 * TODO: Jackson przy odczycie nie wie, czy {"amount":...} to Transfer, czy Card.
 * Dodaj nad interfejsem dwie adnotacje (slajd 167):
 * - @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
 * - @JsonSubTypes z dwoma @JsonSubTypes.Type: Transfer o nazwie "transfer" i Card o nazwie "card".
 * W JSON pojawi się wtedy pole "type".
 */
// ROZWIĄZANIE (odkomentuj i usuń throw):
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
// @JsonSubTypes({
//         @JsonSubTypes.Type(value = Payment.Transfer.class, name = "transfer"),
//         @JsonSubTypes.Type(value = Payment.Card.class, name = "card")
// })
// KONIEC ROZWIĄZANIA
public sealed interface Payment permits Payment.Transfer, Payment.Card {

    double amount();

    record Transfer(String iban, double amount) implements Payment {
    }

    record Card(String lastDigits, double amount) implements Payment {
    }
}
