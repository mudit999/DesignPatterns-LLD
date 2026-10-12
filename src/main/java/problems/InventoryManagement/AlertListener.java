package problems.InventoryManagement;

public interface AlertListener {
//    + onLowStock(warehouseId, productId, currentQuantity) -> void
    public void onLowStock(String warehouseId, String productId, int currentQty);
}
