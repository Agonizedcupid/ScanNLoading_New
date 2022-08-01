package com.aariyan.scannloading.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Service.PostLinesService;

public class InternetConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constant.isInternetConnected(context)) {
            if (!PostLinesService.isServiceRunning) {
                ContextCompat.startForegroundService(context, new Intent(context, PostLinesService.class));
            }
        } else {
            Log.d("TEST_INTERNET", "onReceive: No Internet");
        }
    }
}