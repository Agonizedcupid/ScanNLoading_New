package com.aariyan.scannloading.Interface;

import android.view.View;

import com.aariyan.scannloading.Model.HeadersModel;
import com.aariyan.scannloading.Model.LinesModel;

public interface UpdateLines {

    //void clickForUpdate(LinesModel model, int position, String adapterSelection, View shower);
    void clickForUpdate(HeadersModel model, int position, String adapterSelection);
}
