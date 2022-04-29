package com.aariyan.scannloading.Model;

public class UserModel {
    private String UserName;
    private int TabletUser,UserID,PinCode;
    private String strQRCode;
    private int GroupId;

    public UserModel() {}

    public UserModel(String userName, int tabletUser, int userID, int pinCode, String strQRCode, int groupId) {
        UserName = userName;
        TabletUser = tabletUser;
        UserID = userID;
        PinCode = pinCode;
        this.strQRCode = strQRCode;
        GroupId = groupId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getTabletUser() {
        return TabletUser;
    }

    public void setTabletUser(int tabletUser) {
        TabletUser = tabletUser;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getPinCode() {
        return PinCode;
    }

    public void setPinCode(int pinCode) {
        PinCode = pinCode;
    }

    public String getStrQRCode() {
        return strQRCode;
    }

    public void setStrQRCode(String strQRCode) {
        this.strQRCode = strQRCode;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

}
