package com.aariyan.scannloading.Network;

import android.content.Context;

import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;
//    static Gson gson = new GsonBuilder()
//            .setLenient()
//            .create();
    private static String BASE_URL = "http://102.37.0.48/Loadscan/";
    //private static String BASE_URL = Constant.DEFAULT_URL;
//    static SharedPreferences sharedPreferences = new SharedPreferences(context.getApplicationContext());
//    private static String BASE_URL = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL);

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
