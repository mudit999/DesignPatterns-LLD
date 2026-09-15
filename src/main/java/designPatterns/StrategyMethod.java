package designPatterns;

interface PaymentStrategy{
    boolean pay(double amount);
}

class CreditCardPayment implements PaymentStrategy{
    private String cardNumber;

    public CreditCardPayment(String cardNumber){
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount){
        // Credit card processing logic
        System.out.println("Paid " + amount + " with credit card");
        return true;
    }
}

class PaypalPayment implements PaymentStrategy{
    private String email;

    public PaypalPayment(String email){
        this.email = email;
    }

    @Override
    public boolean pay(double amount){
        // Credit card processing logic
        System.out.println("Paid " + amount + " with Paypal");
        return true;
    }
}

class ShoppingCart{
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(double amount){
        paymentStrategy.pay(amount);
    }
}

public class StrategyMethod {
    public static void main(String args[]){
    ShoppingCart cart = new ShoppingCart();
    cart.setPaymentStrategy(new PaypalPayment("abf@gmail.com"));
    cart.checkout(100.9);

    cart.setPaymentStrategy(new CreditCardPayment("298389238"));
    cart.checkout(1212.78);
    }
}
