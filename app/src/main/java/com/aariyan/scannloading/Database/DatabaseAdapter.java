package com.aariyan.scannloading.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.Model.QueueModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {


    DatabaseHelper helper;
    private List<HeadersModel> headerList = new ArrayList<>();
    private List<LinesModel> linesList = new ArrayList<>();
    private List<QueueModel> queueList = new ArrayList<>();

    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    //Insert Header:
    public long insertHeader(String storeName, String route, int deliverySequence, int invoiced, String invoiceNo, String orderNo,
                             String customerPastelCode, int customerId, String MESSAGESINV, String userName,
                             int orderId, String strLoadedBy, int loaded, int blnPicked, int blnPriority,
                             String deladdress, int value, String orderDate, String condition, String strCrateName,
                             String date, int routeName, int orderTypes, int userId) {

        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.StoreName, storeName);
        contentValues.put(DatabaseHelper.Route, route);
        contentValues.put(DatabaseHelper.DeliverySequence, deliverySequence);
        contentValues.put(DatabaseHelper.Invoiced, invoiced);
        contentValues.put(DatabaseHelper.InvoiceNo, invoiceNo);
        contentValues.put(DatabaseHelper.OrderNo, orderNo);
        contentValues.put(DatabaseHelper.CustomerPastelCode, customerPastelCode);
        contentValues.put(DatabaseHelper.CustomerId, customerId);
        contentValues.put(DatabaseHelper.MESSAGESINV, MESSAGESINV);
        contentValues.put(DatabaseHelper.UserName, userName);
        contentValues.put(DatabaseHelper.OrderId, orderId);
        contentValues.put(DatabaseHelper.strLoadedBy, strLoadedBy);
        contentValues.put(DatabaseHelper.Loaded, loaded);
        contentValues.put(DatabaseHelper.blnPicked, blnPicked);
        contentValues.put(DatabaseHelper.blnPriority, blnPriority);
        contentValues.put(DatabaseHelper.deladdress, deladdress);
        contentValues.put(DatabaseHelper.Value, value);
        contentValues.put(DatabaseHelper.OrderDate, orderDate);
        contentValues.put(DatabaseHelper.condition, condition);
        contentValues.put(DatabaseHelper.strCrateName, strCrateName);
        contentValues.put(DatabaseHelper.DATE, date);
        contentValues.put(DatabaseHelper.ROUTE_NAME, routeName);
        contentValues.put(DatabaseHelper.ORDER_TYPES, orderTypes);
        contentValues.put(DatabaseHelper.userId, userId);

        long id = database.insert(DatabaseHelper.HEADERS_TABLE_NAME, null, contentValues);
        return id;
    }

    //Insert Lines:
    public long insertLines(int blnPickeds, int loadeds, String pastelCode, String pastelDescription,
                            int productId, int qty, int qtyOrdered, double price, String comment,
                            String unitSize, String strBulkUnit, int unitWeight, int orderId,
                            int orderDetailId, String barCode, String scannedQty, int isRandom, String pickingTeam,
                            String date, int routeName, int orderTypes, int userId) {

        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.blnPickeds, blnPickeds);
        contentValues.put(DatabaseHelper.Loadeds, loadeds);
        contentValues.put(DatabaseHelper.PastelCode, pastelCode);
        contentValues.put(DatabaseHelper.PastelDescription, pastelDescription);
        contentValues.put(DatabaseHelper.ProductId, productId);
        contentValues.put(DatabaseHelper.Qty, qty);
        contentValues.put(DatabaseHelper.QtyOrdered, qtyOrdered);
        contentValues.put(DatabaseHelper.Price, price);
        contentValues.put(DatabaseHelper.Comment, comment);
        contentValues.put(DatabaseHelper.UnitSize, unitSize);
        contentValues.put(DatabaseHelper.strBulkUnit, strBulkUnit);
        contentValues.put(DatabaseHelper.UnitWeight, unitWeight);
        contentValues.put(DatabaseHelper.OrderIds, orderId);
        contentValues.put(DatabaseHelper.OrderDetailId, orderDetailId);
        contentValues.put(DatabaseHelper.BarCode, barCode);
        contentValues.put(DatabaseHelper.ScannedQty, scannedQty);
        contentValues.put(DatabaseHelper.isRandom, isRandom);
        contentValues.put(DatabaseHelper.PickingTeam, pickingTeam);

        contentValues.put(DatabaseHelper.DATE, date);
        contentValues.put(DatabaseHelper.ROUTE_NAME, routeName);
        contentValues.put(DatabaseHelper.ORDER_TYPES, orderTypes);
        contentValues.put(DatabaseHelper.FLAG, 0);
        contentValues.put(DatabaseHelper.userId, userId);

        long id = database.insert(DatabaseHelper.LINES_TABLE_NAME, null, contentValues);
        return id;
    }

    //Insert Lines:
    public long insertQueue(String type, String instructions) {
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.Type, type);
        contentValues.put(DatabaseHelper.Instructions, instructions);

        long id = database.insert(DatabaseHelper.QUEUE_TABLE_NAME, null, contentValues);
        return id;
    }


    //get HEADER by User Name , date, route Name, order types, user id
    public List<HeadersModel> getHeaderByDateRouteNameOrderTypes(String userName, String date, int routeName, int orderTypes, int userId) {

        headerList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        //select * from tableName where name = ? and customerName = ?:
        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
        String selection = DatabaseHelper.UserName + "=?" +
                " and " + DatabaseHelper.DATE + "=?" +
                " and " + DatabaseHelper.ROUTE_NAME + "=?" +
                " and " + DatabaseHelper.ORDER_TYPES + "=?" +
                " and " + DatabaseHelper.userId + "=?";


        String[] args = {userName, date, "" + routeName, "" + orderTypes, "" + userId};
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.StoreName, DatabaseHelper.Route, DatabaseHelper.DeliverySequence,
                DatabaseHelper.Invoiced, DatabaseHelper.InvoiceNo, DatabaseHelper.OrderNo, DatabaseHelper.CustomerPastelCode,
                DatabaseHelper.CustomerId, DatabaseHelper.MESSAGESINV, DatabaseHelper.UserName, DatabaseHelper.OrderId
                , DatabaseHelper.strLoadedBy, DatabaseHelper.Loaded, DatabaseHelper.blnPicked, DatabaseHelper.blnPriority, DatabaseHelper.deladdress
                , DatabaseHelper.Value, DatabaseHelper.OrderDate, DatabaseHelper.condition, DatabaseHelper.strCrateName, DatabaseHelper.DATE
                , DatabaseHelper.ROUTE_NAME, DatabaseHelper.ORDER_TYPES, DatabaseHelper.userId};

        Cursor cursor = database.query(DatabaseHelper.HEADERS_TABLE_NAME, columns, selection, args, null, null, null);
        while (cursor.moveToNext()) {
            HeadersModel model = new HeadersModel(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getInt(11),
                    cursor.getString(12),
                    cursor.getInt(13),
                    cursor.getInt(14),
                    cursor.getInt(15),
                    cursor.getString(16),
                    cursor.getInt(17),
                    cursor.getString(18),
                    cursor.getString(19),
                    cursor.getString(20),
                    cursor.getString(21),
                    cursor.getInt(22),
                    cursor.getInt(23),
                    cursor.getInt(24)
            );
            headerList.add(model);
        }
        return headerList;

    }


    //get HEADER by User Name , date, route Name, order types, user id
    public List<LinesModel> getLinesByDateRouteNameOrderTypes(int orderId) {

        linesList.clear();
        SQLiteDatabase database = helper.getWritableDatabase();
        //select * from tableName where name = ? and customerName = ?:
        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
        String selection = DatabaseHelper.OrderIds + "=?";


        String[] args = {"" + orderId};
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.blnPickeds, DatabaseHelper.Loadeds, DatabaseHelper.PastelCode,
                DatabaseHelper.PastelDescription, DatabaseHelper.ProductId, DatabaseHelper.Qty, DatabaseHelper.QtyOrdered,
                DatabaseHelper.Price, DatabaseHelper.Comment, DatabaseHelper.UnitSize, DatabaseHelper.strBulkUnit
                , DatabaseHelper.UnitWeight, DatabaseHelper.OrderIds, DatabaseHelper.OrderDetailId, DatabaseHelper.BarCode, DatabaseHelper.ScannedQty
                , DatabaseHelper.isRandom, DatabaseHelper.PickingTeam,DatabaseHelper.FLAG};

        Cursor cursor = database.query(DatabaseHelper.LINES_TABLE_NAME, columns, selection, args, null, null, null);
        while (cursor.moveToNext()) {
            LinesModel model = new LinesModel(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getDouble(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getInt(12),
                    cursor.getInt(13),
                    cursor.getInt(14),
                    cursor.getString(15),
                    cursor.getString(16),
                    cursor.getInt(17),
                    cursor.getString(18),
                    cursor.getInt(19)
            );
            linesList.add(model);
        }
        return linesList;

    }

    //Update Quantity of lines table, as well as changing the flag value using orderId & orderDetailsId:
    public long updateLinesQuantity(int orderId, int orderDetailsId, int userId, int quantity, int flag) {
        SQLiteDatabase database = helper.getWritableDatabase();
        String selection = DatabaseHelper.OrderIds + " LIKE ? AND " + DatabaseHelper.OrderDetailId + " LIKE ? ";
        String[] args = {"" + orderId, "" + orderDetailsId};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.Qty, quantity);
        contentValues.put(DatabaseHelper.FLAG, flag);

        long ids = database.update(DatabaseHelper.LINES_TABLE_NAME, contentValues, selection, args);

        return ids;
    }


//    //Getting all the user
//    public List<UserListModel> getUserData() {
//
//        list.clear();
//        SQLiteDatabase database = helper.getWritableDatabase();
//        String[] columns = {DatabaseHelper.UID, DatabaseHelper.uID, DatabaseHelper.strPinCode, DatabaseHelper.strName, DatabaseHelper.intCompanyID};
//        Cursor cursor = database.query(DatabaseHelper.USER_TABLE_NAME, columns, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//
//            UserListModel model = new UserListModel(
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3),
//                    cursor.getString(4)
//            );
//            list.add(model);
//        }
//        return list;
//
//    }
//
//    //Insert customer data
//    public long insertCustomerData(String strCustName, String strCustDesc, String Uid) {
//
//        SQLiteDatabase database = helper.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.strCustName, strCustName);
//        contentValues.put(DatabaseHelper.strCustDesc, strCustDesc);
//        contentValues.put(DatabaseHelper.Uid, Uid);
//
//        long id = database.insert(DatabaseHelper.CUSTOMER_TABLE_NAME, null, contentValues);
//        return id;
//    }
//
//    //Getting all the Customer
//    public List<CustomerModel> getAlLCustomer() {
//
//        customerList.clear();
//        SQLiteDatabase database = helper.getWritableDatabase();
//        String[] columns = {DatabaseHelper.UID, DatabaseHelper.strCustName, DatabaseHelper.strCustDesc, DatabaseHelper.Uid};
//        Cursor cursor = database.query(DatabaseHelper.CUSTOMER_TABLE_NAME, columns, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//
//            CustomerModel model = new CustomerModel(
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3)
//            );
//            customerList.add(model);
//        }
//        return customerList;
//
//    }
//
//    //getTiming by User Name and Customer Name
//    public List<TimingModel> getTiming(String userName, String customerName) {
//
//        timingList.clear();
//        SQLiteDatabase database = helper.getWritableDatabase();
//        //select * from tableName where name = ? and customerName = ?:
//        // String selection = DatabaseHelper.USER_NAME+" where ? AND "+DatabaseHelper.CUSTOMER_NAME+" LIKE ?";
//        String selection = DatabaseHelper.USER_NAME + "=?" + " and " + DatabaseHelper.CUSTOMER_NAME + "=?";
//
//        Log.d("NAME_TEST", userName + " -> " + customerName);
//
//        String[] args = {userName, customerName};
//        String[] columns = {DatabaseHelper.UID, DatabaseHelper.USER_NAME, DatabaseHelper.CUSTOMER_NAME, DatabaseHelper.START_DATE,
//                DatabaseHelper.BILLABLE_TIME, DatabaseHelper.STATUS, DatabaseHelper.TOTAL_TIME, DatabaseHelper.WORK_TYPE,
//                DatabaseHelper.COMPLETED, DatabaseHelper.DESCRIPTION};
//
//        Cursor cursor = database.query(DatabaseHelper.TIMING_TABLE_NAME, columns, selection, args, null, null, null);
//        while (cursor.moveToNext()) {
//            TimingModel model = new TimingModel(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3),
//                    cursor.getString(4),
//                    cursor.getString(5),
//                    cursor.getString(6),
//                    cursor.getString(7),
//                    cursor.getString(8),
//                    cursor.getString(9)
//            );
//            timingList.add(model);
//        }
//        return timingList;
//
//    }
//
//
//    //Delete timing by User Name , Customer Name, ID
//    public long deleteTiming(String userName, String customerName, int id) {
//        SQLiteDatabase database = helper.getWritableDatabase();
//        String selection = DatabaseHelper.USER_NAME + " LIKE ? AND " + DatabaseHelper.CUSTOMER_NAME + " LIKE ? AND " + DatabaseHelper.UID + " LIKE ?";
//
//        String[] args = {userName, customerName, "" + id};
//        long ids = database.delete(DatabaseHelper.TIMING_TABLE_NAME, selection, args);
//
//        return ids;
//    }
//
//    //Delete timing by id
//    public long deleteJobs(int id) {
//        SQLiteDatabase database = helper.getWritableDatabase();
//        //select * from table_name where id = id
//        String selection = DatabaseHelper.UID + " LIKE ?";
//
//        String[] args = {"" + id};
//        long ids = database.delete(DatabaseHelper.TIMING_TABLE_NAME, selection, args);
//
//        return ids;
//    }

    class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        private static final String DATABASE_NAME = "scan_N_loading.db";
        private static final int VERSION_NUMBER = 4;

        //Header Table:
        private static final String HEADERS_TABLE_NAME = "headers";
        private static final String UID = "_id";
        private static final String StoreName = "StoreName";
        private static final String Route = "Route";
        private static final String DeliverySequence = "DeliverySequence";
        private static final String Invoiced = "Invoiced";
        private static final String InvoiceNo = "InvoiceNo";
        private static final String OrderNo = "OrderNo";
        private static final String CustomerPastelCode = "CustomerPastelCode";
        private static final String CustomerId = "CustomerId";
        private static final String MESSAGESINV = "MESSAGESINV";
        private static final String UserName = "UserName";
        private static final String OrderId = "OrderId";
        private static final String strLoadedBy = "strLoadedBy";
        private static final String Loaded = "Loaded";
        private static final String blnPicked = "blnPicked";
        private static final String blnPriority = "blnPriority";
        private static final String deladdress = "deladdress";
        private static final String Value = "Value";
        private static final String OrderDate = "OrderDate";
        private static final String condition = "condition";
        private static final String strCrateName = "strCrateName";
        private static final String DATE = "date";
        private static final String ROUTE_NAME = "routeName";
        private static final String ORDER_TYPES = "orderTypes";
        private static final String userId = "userId";
        //Creating the table:
        private static final String CREATE_HEADERS_TABLE = "CREATE TABLE " + HEADERS_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StoreName + " VARCHAR(255),"
                + Route + " VARCHAR(255),"
                + DeliverySequence + " INTEGER,"
                + Invoiced + " INTEGER,"
                + InvoiceNo + " VARCHAR(255),"
                + OrderNo + " VARCHAR(255),"
                + CustomerPastelCode + " VARCHAR(255),"
                + CustomerId + " INTEGER,"
                + MESSAGESINV + " VARCHAR(255),"
                + UserName + " VARCHAR(255),"
                + OrderId + " INTEGER,"
                + strLoadedBy + " VARCHAR(255),"
                + Loaded + " INTEGER,"
                + blnPicked + " INTEGER,"
                + blnPriority + " INTEGER,"
                + deladdress + " VARCHAR(255),"
                + Value + " INTEGER,"
                + OrderDate + " VARCHAR(255),"
                + condition + " VARCHAR(255),"
                + DATE + " VARCHAR(255),"
                + ROUTE_NAME + " INTEGER,"
                + ORDER_TYPES + " INTEGER,"
                + userId + " INTEGER,"
                + strCrateName + " VARCHAR(255));";
        private static final String DROP_HEADERS_TABLE = "DROP TABLE IF EXISTS " + HEADERS_TABLE_NAME;

        //Lines table:
        private static final String LINES_TABLE_NAME = "lines";
        private static final String blnPickeds = "blnPicked";
        private static final String Loadeds = "Loaded";
        private static final String PastelCode = "PastelCode";
        private static final String PastelDescription = "PastelDescription";
        private static final String ProductId = "ProductId";
        private static final String Qty = "Qty";
        private static final String QtyOrdered = "QtyOrdered";
        private static final String Price = "Price";
        private static final String Comment = "Comment";
        private static final String UnitSize = "UnitSize";
        private static final String strBulkUnit = "strBulkUnit";
        private static final String UnitWeight = "UnitWeight";
        private static final String OrderIds = "OrderId";
        private static final String OrderDetailId = "OrderDetailId";
        private static final String BarCode = "BarCode";
        private static final String ScannedQty = "ScannedQty";
        private static final String isRandom = "isRandom";
        private static final String PickingTeam = "PickingTeam";
        private static final String FLAG = "FLAG";
        //Creating the table:
        private static final String CREATE_LINES_TABLE = "CREATE TABLE " + LINES_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + blnPickeds + " INTEGER,"
                + Loadeds + " INTEGER,"
                + PastelCode + " VARCHAR(255),"
                + PastelDescription + " VARCHAR(255),"
                + ProductId + " INTEGER,"
                + Qty + " INTEGER,"
                + QtyOrdered + " INTEGER,"
                + Price + " REAL,"
                + Comment + " VARCHAR(255),"
                + UnitSize + " VARCHAR(255),"
                + strBulkUnit + " VARCHAR(255),"
                + UnitWeight + " INTEGER,"
                + OrderIds + " INTEGER,"
                + OrderDetailId + " INTEGER,"
                + BarCode + " VARCHAR(255),"
                + ScannedQty + " VARCHAR(255),"
                + isRandom + " INTEGER,"
                + DATE + " VARCHAR(255),"
                + ROUTE_NAME + " INTEGER,"
                + ORDER_TYPES + " INTEGER,"
                + userId + " INTEGER,"
                + FLAG + " INTEGER,"
                + PickingTeam + " VARCHAR(255));";
        private static final String DROP_LINES_TABLE = "DROP TABLE IF EXISTS " + LINES_TABLE_NAME;

        //Queue table:
        private static final String QUEUE_TABLE_NAME = "Queue";
        private static final String Type = "type";
        private static final String Instructions = "instructions";

        //Creating the table:
        private static final String CREATE_QUEUE_TABLE = "CREATE TABLE " + QUEUE_TABLE_NAME
                + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Type + " VARCHAR(255),"
                + Instructions + " VARCHAR(255));";
        private static final String DROP_QUEUE_TABLE = "DROP TABLE IF EXISTS " + QUEUE_TABLE_NAME;


        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, VERSION_NUMBER);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create table:
            try {
                db.execSQL(CREATE_HEADERS_TABLE);
                db.execSQL(CREATE_LINES_TABLE);
                db.execSQL(CREATE_QUEUE_TABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_HEADERS_TABLE);
                db.execSQL(DROP_LINES_TABLE);
                db.execSQL(DROP_QUEUE_TABLE);
                onCreate(db);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
