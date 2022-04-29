package com.aariyan.scannloading.Model;

public class RouteModel {
    private int Routeid;
    private String Route;
    private int NotInUse;
    private int InActive;
    private int NewRec;
    private int LocationId;
    private String Rmessage;
    private String MinOrderLevel;
    private int DoNotInvoice;

    public RouteModel(){}

    public RouteModel(int routeid, String route, int notInUse, int inActive, int newRec, int locationId, String rmessage, String minOrderLevel, int doNotInvoice) {
        Routeid = routeid;
        Route = route;
        NotInUse = notInUse;
        InActive = inActive;
        NewRec = newRec;
        LocationId = locationId;
        Rmessage = rmessage;
        MinOrderLevel = minOrderLevel;
        DoNotInvoice = doNotInvoice;
    }

    public int getRouteid() {
        return Routeid;
    }

    public void setRouteid(int routeid) {
        Routeid = routeid;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public int getNotInUse() {
        return NotInUse;
    }

    public void setNotInUse(int notInUse) {
        NotInUse = notInUse;
    }

    public int getInActive() {
        return InActive;
    }

    public void setInActive(int inActive) {
        InActive = inActive;
    }

    public int getNewRec() {
        return NewRec;
    }

    public void setNewRec(int newRec) {
        NewRec = newRec;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getRmessage() {
        return Rmessage;
    }

    public void setRmessage(String rmessage) {
        Rmessage = rmessage;
    }

    public String getMinOrderLevel() {
        return MinOrderLevel;
    }

    public void setMinOrderLevel(String minOrderLevel) {
        MinOrderLevel = minOrderLevel;
    }

    public int getDoNotInvoice() {
        return DoNotInvoice;
    }

    public void setDoNotInvoice(int doNotInvoice) {
        DoNotInvoice = doNotInvoice;
    }

    @Override
    public String toString() {
        return ""+getRoute();
    }
}
