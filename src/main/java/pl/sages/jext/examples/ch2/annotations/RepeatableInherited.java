package pl.sages.jext.examples.ch2.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.util.Arrays;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Slajd 56: {@code @Repeatable} (kontener) i {@code @Inherited} (adnotacja klasy przechodzi na podklasy).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch2.annotations.RepeatableInherited}
 */
public class RepeatableInherited {

    @Repeatable(Indexes.class) @Retention(RUNTIME) @interface Index { String name(); }
    @Retention(RUNTIME) @interface Indexes { Index[] value(); } // kontener

    @Index(name = "byName") @Index(name = "byCity") static class Customer {}

    @Inherited @Retention(RUNTIME) @interface Audited {}
    @Audited static class AuditedCustomer {}
    static class VipCustomer extends AuditedCustomer {}

    public static void main(String[] args) {
        System.out.println("getAnnotation(Index):         " + Customer.class.getAnnotation(Index.class) + " (obie siedzą w @Indexes)");
        System.out.println("getAnnotation(Indexes):       " + Customer.class.getAnnotation(Indexes.class));
        System.out.println("getAnnotationsByType(Index):  " + Arrays.toString(Customer.class.getAnnotationsByType(Index.class)));
        System.out.println("VipCustomer getAnnotation:         " + VipCustomer.class.getAnnotation(Audited.class) + " (z nadklasy)");
        System.out.println("VipCustomer getDeclaredAnnotation: " + VipCustomer.class.getDeclaredAnnotation(Audited.class));
    }
}
