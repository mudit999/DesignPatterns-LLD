package problems.InventoryManagement;

import java.util.*;

public class Warehouse {
//    - id : String
//    - inventory : Map<String, int> // <productId, quantity>
//    - AlertConfig : Map<String, List<AlertConfig>>

//    + Warehouse(id)
//    + addStock(productId, quantity)
//    + removeStock(productId, quantity)
//    + getStock(productId)
//    + checkAvailability(productId, quantity) -> boolean
//    + setLowStockAlert(productId, threshold, Listener)
//    + getAlertsToFire(productId, previousQty, newQty) -> List<AlertListener>
    // Only fire an alert when stock drops from above the threshold to below it. Check if we crossed the boundary.

    String id;
    Map<String, Integer> inventory;
    Map<String, List<AlertConfig>> alertConfig;

    public Warehouse(String id){
        this.id = id;
        this.inventory = new HashMap<>();
        this.alertConfig = new HashMap<>();
    }

    public void addStock(String productId, int quantity){
        synchronized (this){
            if(quantity <= 0){
                // throw error
            }

            Integer currentQty = inventory.get(productId);
            if(currentQty == null){
                currentQty = 0;
            }
            inventory.put(productId, currentQty + quantity);
        }
    }

    public boolean removeStock(String productId, int quantity){
        List<AlertConfig> alertsToFire = null;

        synchronized (this){
            if(quantity <= 0){
                return false;
            }

            Integer currentQty = inventory.get(productId);
            if(currentQty < quantity){
                return false;
            }

            int newQty = currentQty - quantity;
            inventory.put(productId, newQty);
            alertsToFire = getAlertsToFire(productId, currentQty, newQty);
        }

        // now fire the alerts
        if(alertsToFire != null){
            for(AlertConfig alert : alertsToFire){
                alert.getListener().onLowStock(id, productId, inventory.get(productId));
            }
        }
        return true;
    }

    private List<AlertConfig> getAlertsToFire(String productId, int previousQty, int newQty){
        List<AlertConfig> configs = alertConfig.get(productId);

        if(configs == null){
            return null;
        }

        List<AlertConfig> alertsToFire = new ArrayList<>();

        for(AlertConfig config : configs){
            if(previousQty >= config.getThreshold() && newQty < config.getThreshold()){ // filter config
                alertsToFire.add(config);
            }
        }
        if(alertsToFire.isEmpty()){
            return null;
        }

        return alertsToFire;
    }

    public int getStock(String productId){
        synchronized (this){
            Integer qty = inventory.get(productId);
            if(qty == null){
                return 0;
            }
            return qty;
        }
    }

    public boolean checkAvailability(String productId, int quantity){
        synchronized (this){
            if(quantity <= 0){
                return false;
            }

            Integer currentQty = inventory.get(productId);
            if(currentQty == null){
                currentQty = 0;
            }
            return currentQty >= quantity;
        }
    }

    public void setLowStockAlert(String productId, int threshold, AlertListener listener){
        synchronized (this) {
            if (threshold <= 0) {
                // throw error
            }

            if (listener == null) {
                // throw error
            }

            List<AlertConfig> configs = alertConfig.get(productId);

            if(configs == null){
                List<AlertConfig> newList = new ArrayList<>();
                configs = alertConfig.put(productId, newList);
            }

            AlertConfig config = new AlertConfig(threshold, listener);
            configs.add(config);
        }
    }
}
