package org.example;

public class OrderService {
    private PaymentMethod paymentMethod;

    OrderService(PaymentMethod paymentMethod){
        paymentMethod = this.paymentMethod;
    }

    void checkout(Order order){
        paymentMethod.process(order.total);
    }
}
