package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.scannloading.Adapter.HeaderLinesAdapter;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Database.SharedPreferences;
import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.Model.OrderModel;
import com.aariyan.scannloading.Model.RouteModel;
import com.aariyan.scannloading.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Home extends AppCompatActivity {

    public static String userName = "";
    private int userId = 1;

    private RecyclerView recyclerView;

    private RadioButton loadingBtn, queueBtn;

    private CardView datePicker;
    private TextView dateText;
    private Spinner orderSpinner, routeSpinner;
    private Button getLoadingBtn;

    private RequestQueue requestQueue;

    private ProgressBar topProgressbar;
    private TextView warningMessage;
    private List<OrderModel> orderList = new ArrayList<>();
    private List<RouteModel> routeList = new ArrayList<>();

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    int day, month, year;
    String date = "";

    private static int selectedRoute, selectedOrder;

    private List<HeadersModel> headerLinesList = new ArrayList<>();
    private List<LinesModel> linesList = new ArrayList<>();

    private ProgressBar progressBar;

    private ConstraintLayout loadingLayout;

    DatabaseAdapter databaseAdapter;

    HeaderLinesAdapter adapter;

    private ConstraintLayout snackBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Instantiating the database:
        databaseAdapter = new DatabaseAdapter(Home.this);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        requestQueue = Volley.newRequestQueue(this);

        if (getIntent() != null) {
            userName = getIntent().getStringExtra("name");
            userId = getIntent().getIntExtra("id", userId);
        }

        setTitle(userName);

        initUI();

        populateSpinner();
    }

    private void populateSpinner() {
        topProgressbar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = new SharedPreferences(Home.this);

        String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL) + "RouteAndOrdertypes.php";

        JsonObjectRequest array = new JsonObjectRequest(Request.Method.GET, appendedUrl, null,
                this::parseJson,
                e -> {
                    warningMessage.setVisibility(View.VISIBLE);
                    warningMessage.setText("Error: " + e.getMessage());
                    topProgressbar.setVisibility(View.GONE);
                });

        requestQueue.add(array);

    }

    private void parseJson(JSONObject object) {
        try {
            //Taking the root data as JSON Array:
            //JSONArray array = new JSONArray(response.body().string());
            //checking to know, if the data is not available:
            //Clearing the list if any data already in:
            orderList.clear();
            routeList.clear();
            //Making the warning text Disable:
            warningMessage.setVisibility(View.GONE);

            JSONArray routes = object.getJSONArray("routes");
            if (routes.length() > 0) {
                for (int i = 0; i < routes.length(); i++) {
                    JSONObject single = routes.getJSONObject(i);
                    int Routeid = single.getInt("Routeid");
                    String Route = single.getString("Route");
                    int NotInUse = single.getInt("NotInUse");
                    int InActive = single.getInt("InActive");
                    int NewRec = single.getInt("NewRec");
                    int LocationId = single.getInt("LocationId");
                    String Rmessage = single.getString("Rmessage");
                    String MinOrderLevel = single.getString("MinOrderLevel");
                    int DoNotInvoice = single.getInt("DoNotInvoice");

                    RouteModel model = new RouteModel(
                            Routeid, Route, NotInUse, InActive, NewRec, LocationId, Rmessage, MinOrderLevel, DoNotInvoice
                    );
                    routeList.add(model);
                }

                routeSpinnerFunc(routeList);
            }

            JSONArray order = object.getJSONArray("ordertypes");
            if (order.length() > 0) {
                for (int i = 0; i < order.length(); i++) {
                    JSONObject single = order.getJSONObject(i);
                    int OrderTypeId = single.getInt("OrderTypeId");
                    String OrderType = single.getString("OrderType");

                    OrderModel model = new OrderModel(OrderTypeId, OrderType);
                    orderList.add(model);
                }

                orderSpinnerFunc(orderList);
            }

            topProgressbar.setVisibility(View.GONE);
            //If any error happen make it visible and show a warning message:
            warningMessage.setVisibility(View.GONE);
            //warningMessage.setText("No data found!");

        } catch (Exception e) {
            //If any error happen make it visible and show a warning message:
            warningMessage.setVisibility(View.VISIBLE);
            warningMessage.setText("Error: " + e.getMessage());
            topProgressbar.setVisibility(View.GONE);
        }

    }

    private void routeSpinnerFunc(List<RouteModel> routeList) {

        //Spinner items
        ArrayAdapter<RouteModel> dataAdapter = new ArrayAdapter<RouteModel>(Home.this,
                android.R.layout.simple_spinner_item, routeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        routeSpinner.setAdapter(dataAdapter);
        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //selectedRoute = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
                selectedRoute = routeList.get(position).getRouteid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void orderSpinnerFunc(List<OrderModel> orderList) {
        //Spinner items
        ArrayAdapter<OrderModel> dataAdapter = new ArrayAdapter<OrderModel>(Home.this,
                android.R.layout.simple_spinner_item, orderList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(dataAdapter);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //selectedOrder = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
                selectedOrder = orderList.get(position).getOrderTypeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initUI() {

        snackBarLayout = findViewById(R.id.homeConstraintLayout);

        topProgressbar = findViewById(R.id.topProgressbar);
        progressBar = findViewById(R.id.pBar);
        warningMessage = findViewById(R.id.warningMessage);

        loadingLayout = findViewById(R.id.loadingLayout);

        recyclerView = findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        datePicker = findViewById(R.id.dateCardView);
        dateText = findViewById(R.id.dateTextView);
        routeSpinner = findViewById(R.id.routeSpinner);
        orderSpinner = findViewById(R.id.orderIdSpinner);
        getLoadingBtn = findViewById(R.id.getLoadingBtn);

        getLoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerLinesList.clear();
                headerLinesList = databaseAdapter.getHeaderByDateRouteNameOrderTypes(userName, date, selectedRoute, selectedOrder, userId);

                if (date.equals("")) {
                    Toast.makeText(Home.this, "Please select date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (headerLinesList.size() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    warningMessage.setVisibility(View.GONE);
                    adapter = new HeaderLinesAdapter(Home.this, headerLinesList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Snackbar.make(snackBarLayout, "Data is showing from local storage.", Snackbar.LENGTH_LONG).show();
                } else {
                    callAPIs();
                    Snackbar.make(snackBarLayout, "Data is showing from API.", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        loadingBtn = findViewById(R.id.loadingRadioBtn);
        queueBtn = findViewById(R.id.queueRadioBtn);

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });

        loadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.setVisibility(View.VISIBLE);
            }
        });

        queueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingLayout.setVisibility(View.GONE);
            }
        });

    }

    private void callAPIs() {

        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = new SharedPreferences(Home.this);

        String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL) +
                "OrdersAndOrderLines.php?routename=" + selectedRoute + "&ordertype=" + selectedOrder + "&deliverydate=" + date + "&userId=" + userId;

        JsonObjectRequest array = new JsonObjectRequest(Request.Method.GET, appendedUrl, null,
                this::parseOrdernOrderJson,
                e -> {
                    recyclerView.setVisibility(View.GONE);
                    warningMessage.setVisibility(View.VISIBLE);
                    warningMessage.setText("Error: " + e.getMessage());
                    progressBar.setVisibility(View.GONE);
                });

        requestQueue.add(array);
    }

    private void parseOrdernOrderJson(JSONObject finalResponse) {

        try {

            JSONArray Headers = finalResponse.getJSONArray("Headers");
            JSONArray Lines = finalResponse.getJSONArray("Lines");

            headerLinesList.clear();
            if (Headers.length() > 0) {
                warningMessage.setVisibility(View.GONE);
                for (int i = 0; i < Headers.length(); i++) {
                    JSONObject single = Headers.getJSONObject(i);

                    String StoreName = single.getString("StoreName");
                    String Route = single.getString("Route");
                    int DeliverySequence = single.getInt("DeliverySequence");
                    int Invoiced = single.getInt("Invoiced");
                    String InvoiceNo = single.getString("InvoiceNo");
                    String OrderNo = single.getString("OrderNo");
                    String CustomerPastelCode = single.getString("CustomerPastelCode");
                    int CustomerId = single.getInt("CustomerId");
                    String MESSAGESINV = single.getString("MESSAGESINV");
                    String UserName = single.getString("UserName");
                    int OrderId = single.getInt("OrderId");
                    String strLoadedBy = single.getString("strLoadedBy");
                    int Loaded = single.getInt("Loaded");
                    int blnPicked = single.getInt("blnPicked");
                    int blnPriority = single.getInt("blnPriority");
                    String deladdress = single.getString("deladdress");
                    int Value = single.getInt("Value");
                    String OrderDate = single.getString("OrderDate");
                    String condition = single.getString("condition");
                    String strCrateName = single.getString("strCrateName");


                    HeadersModel model = new HeadersModel(StoreName,
                            Route,
                            DeliverySequence,
                            Invoiced,
                            InvoiceNo,
                            OrderNo,
                            CustomerPastelCode,
                            CustomerId,
                            MESSAGESINV,
                            UserName,
                            OrderId,
                            strLoadedBy,
                            Loaded,
                            blnPicked,
                            blnPriority,
                            deladdress,
                            Value,
                            OrderDate,
                            condition,
                            strCrateName);
                    headerLinesList.add(model);

                    //if data is not empty
                    if (Headers.length() > 0 && Lines.length() > 0) {
                        // Insert into SQLite:
                        long id = databaseAdapter.insertHeader(StoreName, Route, DeliverySequence, Invoiced, InvoiceNo, OrderNo, CustomerPastelCode, CustomerId,
                                MESSAGESINV, UserName, OrderId, strLoadedBy, Loaded, blnPicked, blnPriority, deladdress, Value, OrderDate, condition, strCrateName,
                                date, selectedRoute, selectedOrder, userId);

                        if (id > 0) {
                            Snackbar.make(snackBarLayout, "Data also saved on local database", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }

                recyclerView.setVisibility(View.VISIBLE);

                adapter = new HeaderLinesAdapter(Home.this, headerLinesList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);

            } else {
                progressBar.setVisibility(View.GONE);
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("No data found!");
                recyclerView.setVisibility(View.GONE);

            }


            //JSONArray Lines = finalResponse.getJSONArray("Lines");
            linesList.clear();
            if (Lines.length() > 0) {

                for (int i = 0; i < Lines.length(); i++) {
                    JSONObject single = Lines.getJSONObject(i);
                    int blnPicked = single.getInt("blnPicked");
                    int Loaded = single.getInt("Loaded");
                    String PastelCode = single.getString("PastelCode");
                    String PastelDescription = single.getString("PastelDescription");
                    int ProductId = single.getInt("ProductId");
                    int Qty = single.getInt("Qty");
                    int QtyOrdered = single.getInt("QtyOrdered");
                    double Price = single.getDouble("Price");
                    String Comment = single.getString("Comment");
                    String UnitSize = single.getString("UnitSize");
                    String strBulkUnit = single.getString("strBulkUnit");
                    int UnitWeight = single.getInt("UnitWeight");
                    int OrderId = single.getInt("OrderId");
                    int OrderDetailId = single.getInt("OrderDetailId");
                    String BarCode = single.getString("BarCode");
                    String ScannedQty = single.getString("ScannedQty");
                    int isRandom = single.getInt("isRandom");
                    String PickingTeam = single.getString("PickingTeam");

                    LinesModel linesModel = new LinesModel(
                            blnPicked, Loaded, PastelCode, PastelDescription, ProductId, Qty, QtyOrdered,
                            Price, Comment, UnitSize, strBulkUnit, UnitWeight, OrderId, OrderDetailId, BarCode,
                            ScannedQty, isRandom, PickingTeam
                    );

                    linesList.add(linesModel);

                    //if data is not empty
                    if (Headers.length() > 0 && Lines.length() > 0) {
                        // Insert into SQLite:
                        long id = databaseAdapter.insertLines(blnPicked, Loaded, PastelCode, PastelDescription, ProductId, Qty, QtyOrdered, Price, Comment,
                                UnitSize, strBulkUnit, UnitWeight, OrderId, OrderDetailId, BarCode, ScannedQty, isRandom, PickingTeam,
                                date, selectedRoute, selectedOrder, userId);

                        if (id > 0) {
                            Snackbar.make(snackBarLayout, "Data also saved on local database", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }

            } else {
                progressBar.setVisibility(View.GONE);
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("No data found!");
                recyclerView.setVisibility(View.GONE);

            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Home.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            warningMessage.setVisibility(View.VISIBLE);
            warningMessage.setText("" + e.getMessage());
            recyclerView.setVisibility(View.GONE);

        }
    }

    private void showDate() {

        datePickerDialog = new DatePickerDialog(Home.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                //Month
                int j = i1 + 1;

                //date = i + "-" + j + "-" + i2;
                //date = i2 + "-" + j + "-" + i;
                date = i + "-" + j + "-" + i2;
                //2022-1-15
                dateText.setText(date);

            }
            //}, day, month, year);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        new DatePickerDialog(AddTimeActivity.this, null, calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

        datePickerDialog.show();
    }
}