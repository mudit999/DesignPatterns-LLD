package org.example;

public class UPIPayment implements PaymentMethod{
    @Override
    public boolean process(double amount) {
        return true;
    }
}
