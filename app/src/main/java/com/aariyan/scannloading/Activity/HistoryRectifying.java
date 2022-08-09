package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.scannloading.Adapter.GreenAdapter;
import com.aariyan.scannloading.Adapter.RedAdapter;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Interface.LinesRectifying;
import com.aariyan.scannloading.Interface.RedListChanger;
import com.aariyan.scannloading.Interface.UpdateLines;
import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;
import com.aariyan.scannloading.utils.LinesHistoryImplemented;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class HistoryRectifying extends AppCompatActivity implements UpdateLines, RedListChanger {

    private RecyclerView redListView, greenListView;
    LinesModel linesModel;
    List<HeadersModel> redList = new ArrayList<>();
    List<HeadersModel> greenList = new ArrayList<>();
    List<HeadersModel> headersList = new ArrayList<>();
    List<LinesModel> linesList = new ArrayList<>();

    DatabaseAdapter adapter = new DatabaseAdapter(this);

    private View bottomSheet;
    private BottomSheetBehavior behavior;
    private Button verifyBtn;
    private EditText pinCodeField;

    private TextView toolbarTitle;
    private static int productId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostory_rectifying);

        if (getIntent().hasExtra("id")) {
            productId = getIntent().getIntExtra("id", 0);
        }

        initUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initUI() {

        verifyBtn = findViewById(R.id.verifyBtn);
        pinCodeField = findViewById(R.id.passwordField);

        redListView = findViewById(R.id.redList);
        redListView.setLayoutManager(new LinearLayoutManager(this));

        greenListView = findViewById(R.id.greenList);
        greenListView.setLayoutManager(new LinearLayoutManager(this));

        bottomSheet = findViewById(R.id.bottomSheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        toolbarTitle = findViewById(R.id.toolbarTitle);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //onBackPressed();
            }
        });

        loadData(0);
    }

    private void loadData(int identifier) {
        headersList.clear();
        redList.clear();
        greenList.clear();
        if (identifier == 0) {
            linesModel = LinesHistoryImplemented.getModel();
            headersList = adapter.getHeadersByLines(linesModel.getOrderId());
        } else {
            headersList = adapter.getHeadersByLines(identifier);
            linesModel = adapter.getLinesById(productId);
        }

        toolbarTitle.setText(String.valueOf(linesModel.getPastelDescription()));

//        for (HeadersModel model : headersList) {
//            if (model.getLoaded() == 0) {
//                redList.add(model);
//            } else {
//                greenList.add(model);
//            }
//        }

        if (linesModel.getLoaded() == 0) {
            redList.addAll(headersList);
        } else {
            greenList.addAll(headersList);
        }

        RedAdapter redAdapter = new RedAdapter(this, redList, this, linesModel.getTotalItem(), linesModel.getComment());
        redListView.setAdapter(redAdapter);
        redAdapter.notifyDataSetChanged();

        GreenAdapter greenAdapter = new GreenAdapter(this, greenList, this, linesModel.getTotalItem(), linesModel.getComment());
        greenListView.setAdapter(greenAdapter);
        greenAdapter.notifyDataSetChanged();
    }
//    private void loadData(String identifier) {
//        linesList.clear();
//        redList.clear();
//        greenList.clear();
//        if (identifier.equals("pastel")) {
//            model = LinesHistoryImplemented.getModel();
//            linesList = adapter.getLinesByName(model.getPastelDescription());
//        } else {
//            linesList = adapter.getLinesByName(identifier);
//        }
//
//        for (LinesModel model : linesList) {
//            if (model.getLoaded() == 0) {
//                redList.add(model);
//            } else {
//                greenList.add(model);
//            }
//        }
//
//        RedAdapter redAdapter = new RedAdapter(this, redList, this);
//        redListView.setAdapter(redAdapter);
//        redAdapter.notifyDataSetChanged();
//
//        GreenAdapter greenAdapter = new GreenAdapter(this, greenList, this);
//        greenListView.setAdapter(greenAdapter);
//        greenAdapter.notifyDataSetChanged();
//    }

    @Override
    public void clickForUpdate(HeadersModel model, int position, String adapterSelection) {
        int loaded;
//        if (adapterSelection.equals("red")) {
//            loaded = 1;
//            //Toast.makeText(this, "Called", Toast.LENGTH_SHORT).show();
//            //long id = adapter.updateHeadersLoaded(model.getOrderId(), model.getStoreName(), loaded);
//            long id = adapter.updateLinesLoaded(model.getOrderId(), linesModel.getOrderDetailId(), loaded, loaded);
//            if (id > 0) {
//                loadData(model.getOrderId());
//                //onBackPressed();
//            } else {
//                Toast.makeText(HistoryRectifying.this, "Unable to update!", Toast.LENGTH_SHORT).show();
//            }
//        } else {
        loaded = 0;
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pinCodeField.getText().toString())) {
                    pinCodeField.setError("Please Enter Pin");
                    pinCodeField.requestFocus();
                    return;
                }
                if (Constant.PIN_CODE == Integer.parseInt(pinCodeField.getText().toString().trim())) {
                    //long id = adapter.updateLinesLoaded(model.getOrderId(), model.getOrderDetailId(), loaded, loaded);
                    long id = adapter.updateLinesLoaded(model.getOrderId(), linesModel.getOrderDetailId(), loaded, loaded);
                    //long id = adapter.updateHeadersLoaded(model.getOrderId(), model.getStoreName(), loaded);
                    if (id > 0) {
                        loadData(model.getOrderId());
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        //onBackPressed();
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Toast.makeText(HistoryRectifying.this, "Unable to update!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Toast.makeText(HistoryRectifying.this, "Invalid password", Toast.LENGTH_SHORT).show();

                }
            }
        });
        // }

//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        verifyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isEmpty(pinCodeField.getText().toString())) {
//                    pinCodeField.setError("Please Enter Pin");
//                    pinCodeField.requestFocus();
//                    return;
//                }
//                if (Constant.PIN_CODE == Integer.parseInt(pinCodeField.getText().toString().trim())) {
//                    //long id = adapter.updateLinesLoaded(model.getOrderId(), model.getOrderDetailId(), loaded, loaded);
//                    long id = adapter.updateHeadersLoaded(model.getOrderId(), model.getStoreName(), loaded);
//                    if (id > 0) {
//                        loadData(model.getOrderId());
//                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        shower.setVisibility(View.VISIBLE);
//                    } else {
//                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        Toast.makeText(HistoryRectifying.this, "Unable to update!", Toast.LENGTH_SHORT).show();
//                        shower.setVisibility(View.GONE);
//                    }
//                } else {
//                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                    Toast.makeText(HistoryRectifying.this, "Invalid password", Toast.LENGTH_SHORT).show();
//                    shower.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    @Override
    public void clickForRedUpdate(HeadersModel model, int position, String adapterSelection) {
        int loaded = 1;
        //Toast.makeText(this, "Called", Toast.LENGTH_SHORT).show();
        //long id = adapter.updateHeadersLoaded(model.getOrderId(), model.getStoreName(), loaded);
        long id = adapter.updateLinesLoaded(model.getOrderId(), linesModel.getOrderDetailId(), loaded, loaded);
        if (id > 0) {
            redList.clear();
            greenList.clear();
            linesModel = adapter.getLinesById(productId);
            if (linesModel.getLoaded() == 0) {
                redList.addAll(headersList);
            } else {
                greenList.addAll(headersList);
            }

            GreenAdapter greenAdapter = new GreenAdapter(this, greenList, this, linesModel.getTotalItem(), linesModel.getComment());
            greenListView.setAdapter(greenAdapter);
            greenAdapter.notifyDataSetChanged();
            //loadData(model.getOrderId());
            //onBackPressed();
        } else {
            Toast.makeText(HistoryRectifying.this, "Unable to update!", Toast.LENGTH_SHORT).show();
        }
    }
}