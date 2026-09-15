package org.example;

public class CreditCardPayment implements PaymentMethod{

    @Override
    public boolean process(double amount) {
        return true;
    }
}
