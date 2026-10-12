package problems.InventoryManagement;

import java.util.*;

public class InventoryManager {
//    - warehouses : Map<String, Warehouse>

//    + InventoryManager
//    + addStock(warehouseId, productId, quantity) -> void
//    + removeStock(warehouseId, productId, quantity) -> boolean
//    + transfer(productId, fromWarehouseId, toWarehouseId, quantity) -> boolean
//    + getWarehousesWithAvailability(productId, quantity) -> List<warehouseId>
//    + setLowStockAlert(warehouseId, productId, threshold, listener) -> void

    Map<String, Warehouse> warehouses;

    public InventoryManager(List<String> warehouseIdList){
        warehouses = new HashMap<>();
        for(String id: warehouseIdList){
            warehouses.put(id, new Warehouse(id));
        }
    }

    public void addStock(String warehouseId, String productId, int quantity){
        Warehouse warehouse = warehouses.get(warehouseId);
        if(warehouse == null){
            // throw error
        }
        warehouse.addStock(productId, quantity);
    }

    public boolean removeStock(String warehouseId, String productId, int quantity){
        Warehouse warehouse = warehouses.get(warehouseId);
        if(warehouse == null){
            return false;
        }
        return warehouse.removeStock(productId, quantity);
    }

    public List<String> getWarehousesWithAvailability(String productId, int quantity){
        List<String> available = new ArrayList<>();
        Set<String> warehouseIdSet = warehouses.keySet();
        for(String warehouseId : warehouseIdSet){
            Warehouse warehouse = warehouses.get(warehouseId);
            if(warehouse.checkAvailability(productId, quantity)){
                available.add(warehouseId);
            }
        }

        return available;
    }

    public void setLowStockAlert(String warehouseId, String productId, int threshold, AlertListener listener){
       Warehouse warehouse = warehouses.get(warehouseId);
       if(warehouse == null){
//           throw error
       }
       warehouse.setLowStockAlert(productId, threshold, listener);
    }

    public boolean transfer(String productId, String fromWarehouseId, String toWarehouseId, int quantity){
        if(quantity <= 0){
            return false;
        }

        if(!warehouses.containsKey(fromWarehouseId) || !warehouses.containsKey(toWarehouseId)){
            return false;
        }

        Warehouse fromWarehouse = warehouses.get(fromWarehouseId);
        Warehouse toWarehouse = warehouses.get(toWarehouseId);

        // Lock in consistent order to prevent deadlock
        String firstLock = fromWarehouseId.compareTo(toWarehouseId) <= 0 ? fromWarehouseId : toWarehouseId; // finding who comes first
        String secondLock = fromWarehouseId.compareTo(toWarehouseId) >= 0 ? fromWarehouseId : toWarehouseId; // finding who comes second

        synchronized (warehouses.get(firstLock)){
            synchronized ((warehouses.get(secondLock))){
                if(!fromWarehouse.removeStock(productId, quantity)){
                    return false;
                }
                toWarehouse.addStock(productId, quantity);
                return true;
            }
        }
    }


}
