package problems.InventoryManagement;

public class EmailListener implements AlertListener{
    String emailAddress;

    public EmailListener(String emailAddress){
        this.emailAddress = emailAddress;
    }
    @Override
    public void onLowStock(String warehouseId, String productId, int currentQty) {
        String subject = "Low stock alert: " + warehouseId;
        String body = productId + " is low (" + currentQty + " units remaining)";
        sendEmail(emailAddress, subject, body);
    }

    private void sendEmail(String emailAddress, String subject, String body){
        // send email
    }
}
