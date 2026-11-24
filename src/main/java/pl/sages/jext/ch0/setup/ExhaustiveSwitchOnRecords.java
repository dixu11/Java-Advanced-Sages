package pl.sages.jext.ch0.setup;


import java.io.FileNotFoundException;
import java.math.BigDecimal;

sealed interface PaymentResult permits Success,Declined,Failure, Pending{}

record Success(String transactionId, double amount) implements PaymentResult {}
record Declined(String reason, double amount) implements PaymentResult  {}
record Failure(Exception e, double amount)  implements PaymentResult {}
record Pending( double amount)  implements PaymentResult {}


public class ExhaustiveSwitchOnRecords {


     void main() {
        PaymentResult payment = new Success("1",1000);
        PaymentResult payment2 = new Declined("Klient nie opłacił wcześniejszego",200);
        PaymentResult payment3 = new Failure(new FileNotFoundException("Nie znaleziono potrzebnego pliku"),2000);
         PaymentResult payment4 = new Pending(500);

        System.out.println(messageFor(payment));
         System.out.println(messageFor(payment2));
         System.out.println(messageFor(payment3));
         System.out.println(messageFor(payment4));
    }

    String messageFor(PaymentResult payment){
        return switch (payment){
            case Success(_, double amount) when amount>=1000 -> "Zapłacono " + amount
                    + "zł , należy zaksięgować dużą sumę";
            case Success success -> {
                BigDecimal bigDecimal = BigDecimal.valueOf(success.amount());
                yield "Zapłacono " + bigDecimal + "zł";
            }
            case Declined declined -> String.format("Odrzucono: %s.",declined.reason());
            case Failure failure when failure.amount()>=500 -> "Należy pilnie zgłosić. Nazwa błędu: " + failure.e().getClass().getName();
            case Failure failure -> "Spróbuj ponownie później. Nazwa błędu: " + failure.e().getClass().getName();
            case Pending _ -> "Jest w trakcie wykonywania";
        };
    }
}
