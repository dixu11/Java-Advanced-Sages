package pl.sages.jext.examples.ch2.annotations.model;

/** Slajd 57: encja z dwoma indeksami na klasie i jednym na polu. */
@Entity
@Index(name = "byName", properties = "name")
@Index(name = "byCity", properties = "city")
public class Customer {
    String name;
    String city;
    @Index(name = "byEmail")
    String email;
}
