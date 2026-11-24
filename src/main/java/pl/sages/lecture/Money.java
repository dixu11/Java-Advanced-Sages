package pl.sages.lecture;

import java.math.BigDecimal;

final public record Money(BigDecimal amount, String currency)  {
   public Money  {
        if (amount.signum() < 0)
            throw new IllegalArgumentException("amount < 0");
    }

    public Money(BigDecimal amount, String currency, int anotherParameter){
       this(amount.add(BigDecimal.valueOf(anotherParameter)),currency);
    }

    Money plus(Money other) {
        return new Money(amount.add(other.amount()), currency);
    }

}
