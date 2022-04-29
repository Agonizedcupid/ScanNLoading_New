package com.aariyan.scannloading.Model;

public class OrderModel {
    private int OrderTypeId;
    private String OrderType;

    public OrderModel(){}

    public OrderModel(int orderTypeId, String orderType) {
        OrderTypeId = orderTypeId;
        OrderType = orderType;
    }

    public int getOrderTypeId() {
        return OrderTypeId;
    }

    public void setOrderTypeId(int orderTypeId) {
        OrderTypeId = orderTypeId;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    @Override
    public String toString() {
        return ""+getOrderType();
    }
}
