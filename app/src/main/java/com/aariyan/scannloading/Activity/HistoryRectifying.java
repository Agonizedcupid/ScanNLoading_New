package com.aariyan.scannloading.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aariyan.scannloading.Adapter.GreenAdapter;
import com.aariyan.scannloading.Adapter.RedAdapter;
import com.aariyan.scannloading.Database.DatabaseAdapter;
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
    List<LinesModel> linesList = new ArrayList<>();

    DatabaseAdapter adapter = new DatabaseAdapter(this);

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
        linesList = adapter.getLinesByName(model.getPastelDescription());
        for (LinesModel model : linesList) {
            if (model.getLoaded() == 0) {
                redList.add(model);
            } else {
                greenList.add(model);
            }
        }

        RedAdapter redAdapter = new RedAdapter(this, redList);
        redListView.setAdapter(redAdapter);
        redAdapter.notifyDataSetChanged();

        GreenAdapter greenAdapter = new GreenAdapter(this, greenList);
        greenListView.setAdapter(greenAdapter);
        greenAdapter.notifyDataSetChanged();
    }
}