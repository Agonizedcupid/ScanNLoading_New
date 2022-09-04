package com.aariyan.scannloading;

import static com.aariyan.scannloading.Constant.Constant.DEFAULT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.scannloading.Activity.Home;
import com.aariyan.scannloading.Adapter.UserAdapter;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.SharedPreferences;
import com.aariyan.scannloading.Interface.UserListClick;
import com.aariyan.scannloading.Model.UserModel;
import com.aariyan.scannloading.Network.APIs;
import com.aariyan.scannloading.Network.ApiClient;
import com.aariyan.scannloading.Service.PostLinesService;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UserListClick {

    //Instance variable of warning message:
    private TextView warningMessage;
    //Instance of RecyclerView variable:
    private RecyclerView recyclerView;

    //List for populating user data:
    List<UserModel> list = new ArrayList<>();

    private TextView userMessage;
    private EditText passwordField;
    private Button logInBtn;

    private View bottomSheet, ipBottomSheet;
    BottomSheetBehavior behavior, ipBehavior;

    private FloatingActionButton closeApp;

    private EditText ipField, userId;
    private Button saveBtn, exitBtn;

    private CoordinatorLayout snackBarLayout;

    private RequestQueue requestQueue;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);
        snackBarLayout = findViewById(R.id.coordinatorLayout);
        progressBar = findViewById(R.id.progressbar);
        getSavedData();

        //Instantiating the UI element:
        initUI();


        //Loading data from API:
        //loadData();
    }

    private void getSavedData() {
        android.content.SharedPreferences sharedPreferences = getSharedPreferences(Constant.IP_MODE_KEY, Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(Constant.IP_URL, DEFAULT);
        if (result.equals(DEFAULT)) {
            progressBar.setVisibility(View.GONE);
            //Toast.makeText(MainActivity.this, "IP is not set.", Toast.LENGTH_SHORT).show();
            Snackbar.make(snackBarLayout, "IP is not set!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Configure", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showAlertDialog();
                        }
                    }).show();
        } else {
            loadData();
        }
    }

    private void initUI() {
        //instantiating the warning text View:
        warningMessage = findViewById(R.id.warningMessage);
        //Instantiating the recyclerView:
        recyclerView = findViewById(R.id.userRecyclerView);
        // setting the layout as view purpose
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        userMessage = findViewById(R.id.userMessage);
        passwordField = findViewById(R.id.passwordField);
        logInBtn = findViewById(R.id.logInBtn);

        bottomSheet = findViewById(R.id.bottomSheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        closeApp = findViewById(R.id.closeTheApp);

        ipField = findViewById(R.id.ipField);
        userId = findViewById(R.id.userId);
        saveBtn = findViewById(R.id.saveBtn);
        exitBtn = findViewById(R.id.exitBtn);

        ipBottomSheet = findViewById(R.id.bottomSheetForIp);
        ipBehavior = BottomSheetBehavior.from(ipBottomSheet);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ipField.getText().toString().endsWith("/")) {
                    ipBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    SharedPreferences sharedPreferences = new SharedPreferences(MainActivity.this);
                    sharedPreferences.saveURL(Constant.IP_MODE_KEY, ipField.getText().toString());

                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();


                    loadData();
                } else {
                    Toast.makeText(MainActivity.this, "Ip should end with a / (Forward slash)", Toast.LENGTH_SHORT).show();
                }

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ipBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_close_dialog, null);
        TextView setUps = view.findViewById(R.id.setUps);
        TextView yes = view.findViewById(R.id.yes);
        TextView no = view.findViewById(R.id.no);

        setUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //saving the value on shared preference:
                ipBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                SharedPreferences sharedPreferences = new SharedPreferences(MainActivity.this);
                String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL);

                ipField.setText(appendedUrl, TextView.BufferType.EDITABLE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = new SharedPreferences(MainActivity.this);

        String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL) + "users.php";

        JsonArrayRequest array = new JsonArrayRequest(appendedUrl,
                this::parseJson,
                e -> {
                    warningMessage.setVisibility(View.VISIBLE);
                    warningMessage.setText("Error: " + e.getMessage());
                    progressBar.setVisibility(View.GONE);
                });

        requestQueue.add(array);
    }

    @Override
    protected void onResume() {
        if (Constant.isInternetConnected(this)) {
            if (!PostLinesService.isServiceRunning) {
                ContextCompat.startForegroundService(this, new Intent(this, PostLinesService.class));
            }
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        super.onResume();
    }

    private void parseJson(JSONArray array) {

        try {

            //Taking the root data as JSON Array:
            //JSONArray array = new JSONArray(response.body().string());
            //checking to know, if the data is not available:
            //Clearing the list if any data already in:
            list.clear();
            if (array.length() > 0) {
                //Making the warning text Disable:
                warningMessage.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                //Traversing through all the array element:
                for (int i = 0; i < array.length(); i++) {
                    JSONObject single = array.getJSONObject(i);
                    //taking particular element:
                    String UserName = single.getString("UserName");
                    int TabletUser = single.getInt("TabletUser");
                    int UserID = single.getInt("UserID");
                    int PinCode = single.getInt("PinCode");
                    String strQRCode = single.getString("strQRCode");
                    int GroupId = single.getInt("GroupId");

                    UserModel model = new UserModel(UserName, TabletUser, UserID, PinCode, strQRCode, GroupId);
                    list.add(model);
                }

                UserAdapter adapter = new UserAdapter(MainActivity.this, list, MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            } else {
                //If any error happen make it visible and show a warning message:
                warningMessage.setVisibility(View.VISIBLE);
                warningMessage.setText("No data found!");
                progressBar.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            //If any error happen make it visible and show a warning message:
            warningMessage.setVisibility(View.VISIBLE);
            warningMessage.setText("Error: " + e.getMessage());
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void clickOnUser(String name, int pinCode, int userId) {
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        userMessage.setText(String.format("Enter pin for %s", name));

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pin = Integer.parseInt(passwordField.getText().toString());
                if (pin == pinCode) {
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("name", name);
                    intent.putExtra("id", userId);
                    Constant.PIN_CODE = pinCode;
                    Constant.userId = userId;
                    startActivity(intent);
                } else {
                    passwordField.setError("Wrong Credential");
                }
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


    }
}