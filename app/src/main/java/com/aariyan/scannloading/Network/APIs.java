package com.aariyan.scannloading.Network;

import com.aariyan.scannloading.Model.QueueModel;

import org.json.JSONArray;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIs {

    @GET("users.php")
    Call<ResponseBody> getUser();

    @GET("OrdersAndOrderLines.php?")
    Call<ResponseBody> getOrderLines(@Query("routename") int routeName, @Query("ordertype") int orderType
            , @Query("deliverydate") String deliveryDate, @Query("userId") int userId);

    @POST("PostQueueStaging.php")
    Call<String> post(@Body List<QueueModel> list);

}
