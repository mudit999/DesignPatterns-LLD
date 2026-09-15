package designPatterns;

interface Notification{
    void send(String message);
}

class EmailNotification implements Notification{
    @Override
    public void send(String message) {
        // Email sending logic
    }
}

class SMSNotification implements Notification{
    @Override
    public void send(String message) {
        // SMS sending logic
    }
}

class NotificationFactory{
    public static Notification create(String type){
        if("email".equals(type)){
            return new EmailNotification();
        }else if("sms".equals(type)){
            return new SMSNotification();
        }else{
            throw new IllegalArgumentException("Unknown type");
        }
    }
}

public class FactoryMethod {
    public static void main(String[] args) {
        // Usage
        Notification notif = NotificationFactory.create("sms");
        notif.send("sms");
    }
}
