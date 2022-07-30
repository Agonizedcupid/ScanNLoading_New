package com.aariyan.scannloading.Interface;

public interface SingleClickUpdate {
    void onSingleClick(int orderId, int orderDetailsId,int userId,int loaded, int quantity, String date,String type, double price,String itemName);
}
