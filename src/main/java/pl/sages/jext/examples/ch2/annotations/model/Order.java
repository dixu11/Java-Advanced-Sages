package pl.sages.jext.examples.ch2.annotations.model;

/** Dodatkowa encja (nie ze slajdu), żeby skaner znalazł więcej niż jedną klasę. */
@Entity
public class Order {
    @Index(name = "byCustomer")
    String customerId;
    @Index(name = "byPlace", indexType = IndexType.geo)
    String location;
}
