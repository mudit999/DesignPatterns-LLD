package problems.InventoryManagement;

public class AlertConfig {
//    - threshold : int // threshold represents the minimum stock level you want to maintain for a product.
//    - listener : AlertListener

//    + AlertConfig(threshold, listener)
//    + getThreshold()
//    + getListener()

    int threshold;
    AlertListener listener;

    public AlertConfig(int threshold, AlertListener listener){
        this.threshold = threshold;
        this.listener = listener;
    }

    public int getThreshold() {
        return threshold;
    }

    public AlertListener getListener() {
        return listener;
    }
}
