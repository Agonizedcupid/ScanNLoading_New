package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aariyan.scannloading.Interface.LinesRectifying;
import com.aariyan.scannloading.Model.LinesModel;
import com.aariyan.scannloading.R;
import com.aariyan.scannloading.utils.LinesHistoryImplemented;

import java.util.ArrayList;
import java.util.List;

public class HistoryRectifying extends AppCompatActivity{

    private RecyclerView redListView, greenListView;
    LinesModel model = LinesHistoryImplemented.getModel();
    List<LinesModel> redList = new ArrayList<>();
    List<LinesModel> greenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostory_rectifying);

        initUI();
    }

    private void initUI() {
        redListView = findViewById(R.id.redList);
        redListView.setLayoutManager(new LinearLayoutManager(this));

        greenListView = findViewById(R.id.greenList);
        greenListView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {

    }
}