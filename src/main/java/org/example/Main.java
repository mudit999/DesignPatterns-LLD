package org.example;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
//        PaymentMethod paymentMethod = new UPIPayment();
//        Order order = new Order();
//        order.total = 90.9;
//
//        OrderService orderService = new OrderService(paymentMethod);
//        orderService.checkout(order);

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1,11);
        map.put(2,22);
        map.put(3,33);
        map.put(4,44);

        System.out.print(map.get(4));

        // freq of char in string
    }
}