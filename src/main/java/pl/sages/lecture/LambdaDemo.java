package pl.sages.lecture;

import java.math.BigDecimal;

public class LambdaDemo {

    static int number2 = 34;

    static void main() {
        final int number = 1;
        Runnable testowaLabda = () -> System.out.println(number2++);
        testowaLabda.run();

        record Money(BigDecimal amount, String currency) {
           Money{
               if (amount.signum() < 0)
                   throw new IllegalArgumentException("amount < 0");
           }

            Money plus(Money other) {
                return new Money(amount.add(other.amount()), currency);
            }

        }

        Money money = new Money(BigDecimal.valueOf(100),"PLN");
        Money money2 = new Money(BigDecimal.valueOf(50),"PLN");
        money = money.plus(money2);
        //Money suma = money.plus(money2);
        System.out.println(money);
        System.out.println(money2);
      //  System.out.println(suma);


        /*
        * record Money(BigDecimal amount, String currency) {
 8        Money {                    ЃѮ konstruktor kompaktowy: walidacja, bez przepisywania pól
 9            if (amount.signum() < 0) throw new IėėegalArgumentException("amount < 0");
10        }
11        Money plus(Money other) { return new Money(amount.add(other.amount()), currency); }
        * */

    }
}
