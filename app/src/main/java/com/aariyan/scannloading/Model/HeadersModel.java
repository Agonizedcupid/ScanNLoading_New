package com.aariyan.scannloading.Model;

public class HeadersModel {

    private String StoreName;
    private String Route;
    private int DeliverySequence, Invoiced;
    private String InvoiceNo, OrderNo, CustomerPastelCode;
    private int CustomerId;
    private String MESSAGESINV, UserName;
    private int orderId;
    private String strLoadedBy;
    private int Loaded, blnPicked, blnPriority;
    private String deladdress;
    private int Value;
    private String OrderDate, condition, strCrateName;

    String date;
    int routeName, orderTypes, userId;

    public HeadersModel() {
    }

    public HeadersModel(String storeName, String route, int deliverySequence, int invoiced, String invoiceNo, String orderNo, String customerPastelCode, int customerId, String MESSAGESINV, String userName, int orderId, String strLoadedBy, int loaded, int blnPicked, int blnPriority, String deladdress, int value, String orderDate, String condition, String strCrateName, String date, int routeName, int orderTypes, int userId) {
        StoreName = storeName;
        Route = route;
        DeliverySequence = deliverySequence;
        Invoiced = invoiced;
        InvoiceNo = invoiceNo;
        OrderNo = orderNo;
        CustomerPastelCode = customerPastelCode;
        CustomerId = customerId;
        this.MESSAGESINV = MESSAGESINV;
        UserName = userName;
        this.orderId = orderId;
        this.strLoadedBy = strLoadedBy;
        Loaded = loaded;
        this.blnPicked = blnPicked;
        this.blnPriority = blnPriority;
        this.deladdress = deladdress;
        Value = value;
        OrderDate = orderDate;
        this.condition = condition;
        this.strCrateName = strCrateName;
        this.date = date;
        this.routeName = routeName;
        this.orderTypes = orderTypes;
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRouteName() {
        return routeName;
    }

    public void setRouteName(int routeName) {
        this.routeName = routeName;
    }

    public int getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(int orderTypes) {
        this.orderTypes = orderTypes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HeadersModel(String storeName, String route, int deliverySequence, int invoiced, String invoiceNo, String orderNo, String customerPastelCode, int customerId, String MESSAGESINV, String userName, int orderId, String strLoadedBy, int loaded, int blnPicked, int blnPriority, String deladdress, int value, String orderDate, String condition, String strCrateName) {
        StoreName = storeName;
        Route = route;
        DeliverySequence = deliverySequence;
        Invoiced = invoiced;
        InvoiceNo = invoiceNo;
        OrderNo = orderNo;
        CustomerPastelCode = customerPastelCode;
        CustomerId = customerId;
        this.MESSAGESINV = MESSAGESINV;
        UserName = userName;
        this.orderId = orderId;
        this.strLoadedBy = strLoadedBy;
        Loaded = loaded;
        this.blnPicked = blnPicked;
        this.blnPriority = blnPriority;
        this.deladdress = deladdress;
        Value = value;
        OrderDate = orderDate;
        this.condition = condition;
        this.strCrateName = strCrateName;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public int getDeliverySequence() {
        return DeliverySequence;
    }

    public void setDeliverySequence(int deliverySequence) {
        DeliverySequence = deliverySequence;
    }

    public int getInvoiced() {
        return Invoiced;
    }

    public void setInvoiced(int invoiced) {
        Invoiced = invoiced;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getCustomerPastelCode() {
        return CustomerPastelCode;
    }

    public void setCustomerPastelCode(String customerPastelCode) {
        CustomerPastelCode = customerPastelCode;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getMESSAGESINV() {
        return MESSAGESINV;
    }

    public void setMESSAGESINV(String MESSAGESINV) {
        this.MESSAGESINV = MESSAGESINV;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStrLoadedBy() {
        return strLoadedBy;
    }

    public void setStrLoadedBy(String strLoadedBy) {
        this.strLoadedBy = strLoadedBy;
    }

    public int getLoaded() {
        return Loaded;
    }

    public void setLoaded(int loaded) {
        Loaded = loaded;
    }

    public int getBlnPicked() {
        return blnPicked;
    }

    public void setBlnPicked(int blnPicked) {
        this.blnPicked = blnPicked;
    }

    public int getBlnPriority() {
        return blnPriority;
    }

    public void setBlnPriority(int blnPriority) {
        this.blnPriority = blnPriority;
    }

    public String getDeladdress() {
        return deladdress;
    }

    public void setDeladdress(String deladdress) {
        this.deladdress = deladdress;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStrCrateName() {
        return strCrateName;
    }

    public void setStrCrateName(String strCrateName) {
        this.strCrateName = strCrateName;
    }
}
