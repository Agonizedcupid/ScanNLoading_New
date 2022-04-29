package com.aariyan.scannloading.Model;

public class LinesModel {
    private int blnPicked,Loaded;
    private String PastelCode,PastelDescription;
    private int ProductId,Qty,QtyOrdered;
    private double Price;
    private String Comment,UnitSize,strBulkUnit;
    private int UnitWeight,OrderId,OrderDetailId;
    private String BarCode,ScannedQty;
    private int isRandom;
    private String PickingTeam;
    private int flag;

    public LinesModel(){}

    //Flag Constructor:


    public LinesModel(int blnPicked, int loaded, String pastelCode, String pastelDescription, int productId, int qty, int qtyOrdered, double price, String comment, String unitSize, String strBulkUnit, int unitWeight, int orderId, int orderDetailId, String barCode, String scannedQty, int isRandom, String pickingTeam, int flag) {
        this.blnPicked = blnPicked;
        Loaded = loaded;
        PastelCode = pastelCode;
        PastelDescription = pastelDescription;
        ProductId = productId;
        Qty = qty;
        QtyOrdered = qtyOrdered;
        Price = price;
        Comment = comment;
        UnitSize = unitSize;
        this.strBulkUnit = strBulkUnit;
        UnitWeight = unitWeight;
        OrderId = orderId;
        OrderDetailId = orderDetailId;
        BarCode = barCode;
        ScannedQty = scannedQty;
        this.isRandom = isRandom;
        PickingTeam = pickingTeam;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public LinesModel(int blnPicked, int loaded, String pastelCode, String pastelDescription, int productId, int qty, int qtyOrdered, double price, String comment, String unitSize, String strBulkUnit, int unitWeight, int orderId, int orderDetailId, String barCode, String scannedQty, int isRandom, String pickingTeam) {
        this.blnPicked = blnPicked;
        Loaded = loaded;
        PastelCode = pastelCode;
        PastelDescription = pastelDescription;
        ProductId = productId;
        Qty = qty;
        QtyOrdered = qtyOrdered;
        Price = price;
        Comment = comment;
        UnitSize = unitSize;
        this.strBulkUnit = strBulkUnit;
        UnitWeight = unitWeight;
        OrderId = orderId;
        OrderDetailId = orderDetailId;
        BarCode = barCode;
        ScannedQty = scannedQty;
        this.isRandom = isRandom;
        PickingTeam = pickingTeam;
    }


    public int getBlnPicked() {
        return blnPicked;
    }

    public void setBlnPicked(int blnPicked) {
        this.blnPicked = blnPicked;
    }

    public int getLoaded() {
        return Loaded;
    }

    public void setLoaded(int loaded) {
        Loaded = loaded;
    }

    public String getPastelCode() {
        return PastelCode;
    }

    public void setPastelCode(String pastelCode) {
        PastelCode = pastelCode;
    }

    public String getPastelDescription() {
        return PastelDescription;
    }

    public void setPastelDescription(String pastelDescription) {
        PastelDescription = pastelDescription;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getQtyOrdered() {
        return QtyOrdered;
    }

    public void setQtyOrdered(int qtyOrdered) {
        QtyOrdered = qtyOrdered;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getUnitSize() {
        return UnitSize;
    }

    public void setUnitSize(String unitSize) {
        UnitSize = unitSize;
    }

    public String getStrBulkUnit() {
        return strBulkUnit;
    }

    public void setStrBulkUnit(String strBulkUnit) {
        this.strBulkUnit = strBulkUnit;
    }

    public int getUnitWeight() {
        return UnitWeight;
    }

    public void setUnitWeight(int unitWeight) {
        UnitWeight = unitWeight;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getOrderDetailId() {
        return OrderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        OrderDetailId = orderDetailId;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getScannedQty() {
        return ScannedQty;
    }

    public void setScannedQty(String scannedQty) {
        ScannedQty = scannedQty;
    }

    public int getIsRandom() {
        return isRandom;
    }

    public void setIsRandom(int isRandom) {
        this.isRandom = isRandom;
    }

    public String getPickingTeam() {
        return PickingTeam;
    }

    public void setPickingTeam(String pickingTeam) {
        PickingTeam = pickingTeam;
    }
}
