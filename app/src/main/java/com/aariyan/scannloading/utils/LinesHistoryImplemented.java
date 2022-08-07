package com.aariyan.scannloading.utils;

import com.aariyan.scannloading.Interface.LinesRectifying;
import com.aariyan.scannloading.Model.LinesModel;

public class LinesHistoryImplemented implements LinesRectifying {

    private static LinesModel model;

    public static LinesModel getModel() {
        return model;
    }

    @Override
    public void carry(LinesModel model) {
        LinesHistoryImplemented.model = model;
    }
}
