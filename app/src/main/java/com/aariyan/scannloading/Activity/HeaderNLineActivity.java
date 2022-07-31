package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.scannloading.Adapter.HeaderNLineAdapter;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Database.SharedPreferences;
import com.aariyan.scannloading.Interface.QuantityUpdater;
import com.aariyan.scannloading.Interface.SingleClickUpdate;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.Model.PostLinesModel;
import com.aariyan.scannloading.Network.Filtering;
import com.aariyan.scannloading.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeaderNLineActivity extends AppCompatActivity implements QuantityUpdater, SingleClickUpdate {

    private int orderId = 0;
    private String orderNumber, createdBy, orderDate, invoiceNo, address, sName;
    private int orderValue = 0;
    //UI
    private MaterialButton addProductBtn, closeBtn;
    private TextView storeName, delaAddress, orderNo, oId, value, cBy, oDate, iNo;
    private EditText barcodeEditText;
    private MaterialButton submitBtn;

    private int userId = 1;

    private RecyclerView greenRecycler, redRecyclerView, blackRecyclerView;

    private List<LinesModel> linesList = new ArrayList<>();
    private List<LinesModel> barcodeLineList = new ArrayList<>();
    private DatabaseAdapter databaseAdapter;

    private View bottomSheet;
    private BottomSheetBehavior behavior;
    private ImageView closeBottomSheet;
    private EditText superVisorCode, quantityUpdate, comment;
    private TextView itemNames, itemPrice, titleOne;
    private Button updateQuantityBtn, updateQuantityBtnByBarcode;

    int position = 0;

    //

    //Snackbar:
    private CoordinatorLayout snackBarLayout;

    private static final String TAG = "HeaderNLineActivity";

    //Instantiating:
    private Filtering filtering;
    HeaderNLineAdapter adapter;

    private List<PostLinesModel> postLinesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_nline);

        databaseAdapter = new DatabaseAdapter(this);
        filtering = new Filtering(HeaderNLineActivity.this);

        if (getIntent() != null) {
            orderId = getIntent().getIntExtra("orderId", 0);
            orderNumber = getIntent().getStringExtra("orderNumber");
            createdBy = getIntent().getStringExtra("createdBy");
            orderDate = getIntent().getStringExtra("orderDate");
            invoiceNo = getIntent().getStringExtra("invoiceNo");
            address = getIntent().getStringExtra("address");
            orderValue = getIntent().getIntExtra("value", 0);
            sName = getIntent().getStringExtra("storeName");
            position = getIntent().getIntExtra("position", position);
        }

        initUI();
    }

    private void initUI() {
        blackRecyclerView = findViewById(R.id.linesRecyclerView);
        blackRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        redRecyclerView = findViewById(R.id.redList);
        redRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        greenRecycler = findViewById(R.id.greenList);
        greenRecycler.setLayoutManager(new LinearLayoutManager(this));

        addProductBtn = findViewById(R.id.addProductBtn);
        closeBtn = findViewById(R.id.closeBtn);
        storeName = findViewById(R.id.storeName);
        delaAddress = findViewById(R.id.address);
        orderNo = findViewById(R.id.orderNumber);
        oId = findViewById(R.id.orderId);
        value = findViewById(R.id.orderValue);
        cBy = findViewById(R.id.createdBy);
        oDate = findViewById(R.id.orderDate);
        iNo = findViewById(R.id.invoiceNo);
        barcodeEditText = findViewById(R.id.barcodeEdtText);
        submitBtn = findViewById(R.id.barcodeSubmitBtn);
        bottomSheet = findViewById(R.id.bottomSheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        closeBottomSheet = findViewById(R.id.closeBottomSheet);
        closeBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        titleOne = findViewById(R.id.titleOne);
        superVisorCode = findViewById(R.id.supervisorCode);
        quantityUpdate = findViewById(R.id.quantityUpdate);
        comment = findViewById(R.id.comment);
        itemNames = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        updateQuantityBtn = findViewById(R.id.saveQuantity);
        updateQuantityBtnByBarcode = findViewById(R.id.saveQuantityByBarcode);
        snackBarLayout = findViewById(R.id.coordinatorLayout);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataByBarCode();
            }
        });

        //set the intent value:
        oId.setText(String.format("Order Id:      %s", orderId));
        value.setText(String.format("Order Value:      %s", orderValue));
        cBy.setText(String.format("Created By:      %s", createdBy));
        oDate.setText(String.format("Order Date:      %s", orderDate));
        iNo.setText(String.format("Invoice No:      %s", invoiceNo));
        delaAddress.setText(address);
        orderNo.setText(orderNumber);
        storeName.setText(sName);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(HeaderNLineActivity.this, Home.class).putExtra("position", position));
                finish();
            }
        });
        loadLinesFromSQLite();
    }

    private void getDataByBarCode() {
        updateQuantityBtnByBarcode.setVisibility(View.VISIBLE);
        updateQuantityBtn.setVisibility(View.GONE);
        titleOne.setVisibility(View.GONE);
        superVisorCode.setVisibility(View.GONE);
        barcodeLineList.clear();
        barcodeLineList = databaseAdapter.getLinesByDateRouteNameOrderTypes(orderId);
        LinesModel model = filtering.getLineByBarcode(barcodeLineList, barcodeEditText.getText().toString(), 0);
        if (model != null) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            itemNames.setText(model.getPastelDescription());
            quantityUpdate.setText("" + model.getQty(), TextView.BufferType.EDITABLE);
            itemPrice.setText(String.format("%s", model.getPrice()));
        } else {
            Snackbar.make(snackBarLayout, "No item found!", Snackbar.LENGTH_SHORT).show();
        }

        updateQuantityBtnByBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First update the line table
                int flag = 0;
                if (Integer.parseInt(quantityUpdate.getText().toString()) == model.getQty()) {
                    flag = 1;
                } else if (Integer.parseInt(quantityUpdate.getText().toString()) < model.getQty()) {
                    flag = 2;
                }
                long ids = databaseAdapter.updateLinesQuantity(orderId, model.getOrderDetailId(),
                        userId, Integer.parseInt(quantityUpdate.getText().toString()), flag);
                if (ids < 0) {
                    Toast.makeText(HeaderNLineActivity.this, "Unable to update Lines Table!", Toast.LENGTH_SHORT).show();
                }

                //then insert data into Queue table to sync on server:
                StringBuilder builder = new StringBuilder();
                builder.append(orderId).append("|").append(model.getOrderDetailId()).append("|")
                        .append(userId).append("|").append(model.getLoaded()).append("|").append(quantityUpdate.getText().toString())
                        .append("|").append(Constant.getDate()).append("|").append("0.0");

                if (!Constant.isInternetConnected(HeaderNLineActivity.this)) {
                    //As there has no connection, we are saving data locally
                    Snackbar.make(snackBarLayout, "No Stable Internet Connection!", Snackbar.LENGTH_SHORT).show();
                    long checkInsert = databaseAdapter.insertQueue(Constant.types[0], builder.toString());
                    Log.d(TAG, "" + builder);
                    if (checkInsert > 0) {
                        Snackbar.make(snackBarLayout, "Data stored locally!", Snackbar.LENGTH_SHORT).show();
                        loadLinesFromSQLite();
                    } else {
                        Snackbar.make(snackBarLayout, "Unable to store data!", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    //If there has a connection then upload directly to the server:
                    Snackbar.make(snackBarLayout, "You have stable internet connection!", Snackbar.LENGTH_SHORT).show();
                    postLinesList.add(new PostLinesModel(new Random().nextInt(), "UPDATE", builder.toString()));
                    postLinesData(postLinesList);
                    //So upload to the server directly
                    loadLinesFromSQLite();
                    Snackbar.make(snackBarLayout, "Posted to the server directly!", Snackbar.LENGTH_SHORT).show();
                }
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    private void loadLinesFromSQLite() {
        linesList.clear();

        //Retrieving data from SQLite database
        linesList = databaseAdapter.getLinesByDateRouteNameOrderTypes(orderId);
        //filtering = new Filtering(HeaderNLineActivity.this);
        //linesList.clear();

        handleData(0);
        handleData(1);
        handleData(2);

    }

    private void handleData(int flag) {
        //for Green List
        //linesList = filtering.getFlagData(flag);
        //Retrieving data from SQLite database
        // linesList.clear();
        linesList = databaseAdapter.getLinesByDateRouteNameOrderTypes(orderId);
        linesList = filtering.getFlagData(linesList, flag);
        adapter = new HeaderNLineAdapter(this, linesList, this, this);
        //HeaderNLineAdapter adapter = new HeaderNLineAdapter(this, linesList, this);
        if (flag == 0) {
            blackRecyclerView.setAdapter(adapter);
        }
        if (flag == 1) {
            greenRecycler.setAdapter(adapter);
        }
        if (flag == 2) {
            redRecyclerView.setAdapter(adapter);
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(int orderId, int orderDetailsId, int userId, int loaded, int quantity, String date, String type, double price, String itemName) {
        itemNames.setText(itemName);
        quantityUpdate.setText("" + quantity, TextView.BufferType.EDITABLE);
        itemPrice.setText(String.format("%s", price));
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        quantityUpdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    int enteredQuantity = Integer.parseInt(charSequence.toString());
                    if (enteredQuantity > quantity) {
                        quantityUpdate.setError("Maximum quantity is " + quantity);
                        quantityUpdate.requestFocus();
                        return;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        updateQuantityBtn.setVisibility(View.VISIBLE);
        updateQuantityBtnByBarcode.setVisibility(View.GONE);
        //Click on save Btn:
        updateQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // First update the line table
                int flag = 0;
                if (Integer.parseInt(quantityUpdate.getText().toString()) == quantity) {
                    flag = 1;
                } else if (Integer.parseInt(quantityUpdate.getText().toString()) < quantity) {
                    flag = 2;
                }
                long ids = databaseAdapter.updateLinesQuantity(orderId, orderDetailsId,
                        userId, Integer.parseInt(quantityUpdate.getText().toString()), flag);
                if (ids < 0) {
                    Toast.makeText(HeaderNLineActivity.this, "Unable to update Lines Table!", Toast.LENGTH_SHORT).show();
                }

                //then insert data into Queue table to sync on server:
                StringBuilder builder = new StringBuilder();
                builder.append(orderId).append("|").append(orderDetailsId).append("|")
                        .append(userId).append("|").append(loaded).append("|").append(quantityUpdate.getText().toString())
                        .append("|").append(Constant.getDate()).append("|").append("0.0");

                if (!Constant.isInternetConnected(HeaderNLineActivity.this)) {
                    //As there has no connection, we are saving data locally
                    Snackbar.make(snackBarLayout, "No Stable Internet Connection!", Snackbar.LENGTH_SHORT).show();
                    long checkInsert = databaseAdapter.insertQueue(Constant.types[0], builder.toString());
                    Log.d(TAG, "" + builder);
                    if (checkInsert > 0) {
                        Snackbar.make(snackBarLayout, "Data stored locally!", Snackbar.LENGTH_SHORT).show();
                        loadLinesFromSQLite();
                    } else {
                        Snackbar.make(snackBarLayout, "Unable to store data!", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    //If there has a connection then upload directly to the server:
                    Snackbar.make(snackBarLayout, "You have stable internet connection!", Snackbar.LENGTH_SHORT).show();
                    postLinesList.add(new PostLinesModel(new Random().nextInt(), "UPDATE", builder.toString()));
                    postLinesData(postLinesList);
                    //So upload to the server directly
                    loadLinesFromSQLite();
                    Snackbar.make(snackBarLayout, "Posted to the server directly!", Snackbar.LENGTH_SHORT).show();
                }
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });


    }

    private void postLinesData(List<PostLinesModel> getStock) {
        SharedPreferences sharedPreferences = new SharedPreferences(HeaderNLineActivity.this);

        String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL);
        if (getStock.size() > 0) {
            StringRequest mStringRequest = new StringRequest(
                    Request.Method.POST,
                    appendedUrl + "PostQueueStaging.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("FEEDBACK", response);
                            Toast.makeText(HeaderNLineActivity.this, response.toString() + " Posted successfully!", Toast.LENGTH_SHORT).show();
                            //Now Removing the data from SQLite:
                            //deleteUploadedJobs();
                            postLinesList.clear();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(HeaderNLineActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("FEEDBACK", ""+error.getMessage());
                }
            }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    String jsonString = new Gson().toJson(getStock).toString();
                    return jsonString.getBytes();
                }
            };
            Volley.newRequestQueue(HeaderNLineActivity.this).add(mStringRequest);
        } else {
            Toast.makeText(HeaderNLineActivity.this, "Not enough data!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onSingleClick(int orderId, int orderDetailsId, int userId, int loaded, int quantity, String date, String type, double price, String itemName) {
        int flag = 1;
        long ids = databaseAdapter.updateLinesQuantity(orderId, orderDetailsId,
                userId, quantity, flag);
        if (ids < 0) {
            Toast.makeText(HeaderNLineActivity.this, "Unable to update Lines Table!", Toast.LENGTH_SHORT).show();
        }

        //then insert data into Queue table to sync on server:
        StringBuilder builder = new StringBuilder();
        builder.append(orderId).append("|").append(orderDetailsId).append("|")
                .append(userId).append("|").append(loaded).append("|").append(quantityUpdate.getText().toString())
                .append("|").append(Constant.getDate()).append("|").append("0.0");

        if (!Constant.isInternetConnected(HeaderNLineActivity.this)) {
            //As there has no connection, we are saving data locally
            Snackbar.make(snackBarLayout, "No Stable Internet Connection!", Snackbar.LENGTH_SHORT).show();
            long checkInsert = databaseAdapter.insertQueue(Constant.types[0], builder.toString());
            Log.d(TAG, "" + builder);
            if (checkInsert > 0) {
                Snackbar.make(snackBarLayout, "Data stored locally!", Snackbar.LENGTH_SHORT).show();
                loadLinesFromSQLite();
            } else {
                Snackbar.make(snackBarLayout, "Unable to store data!", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            //If there has a connection then upload directly to the server:
            Snackbar.make(snackBarLayout, "You have stable internet connection!", Snackbar.LENGTH_SHORT).show();
            //So upload to the server directly
            postLinesList.add(new PostLinesModel(new Random().nextInt(),"UPDATE", builder.toString()));
            postLinesData(postLinesList);
            loadLinesFromSQLite();
            Snackbar.make(snackBarLayout, "Posted to the server directly!", Snackbar.LENGTH_SHORT).show();
        }
    }
}