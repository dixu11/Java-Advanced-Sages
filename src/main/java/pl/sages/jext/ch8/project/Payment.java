package pl.sages.jext.ch8.project;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** Gotowy model do etapu a: pole type w JSON wybiera wariant płatności. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Payment.Card.class, name = "card"),
    @JsonSubTypes.Type(value = Payment.Blik.class, name = "blik"),
    @JsonSubTypes.Type(value = Payment.Transfer.class, name = "transfer")
})
public sealed interface Payment permits Payment.Card, Payment.Blik, Payment.Transfer {
    record Card(String number) implements Payment {}
    record Blik(String code) implements Payment {}
    record Transfer(String iban) implements Payment {}
}
