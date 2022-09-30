package com.aariyan.scannloading.Interface;

public interface QuantityUpdater {
    void onClick(int orderId, int orderDetailsId,int userId,int loaded, String quantity, String date,String type, double price,String itemName);
}
