package com.aariyan.scannloading.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {

    @GET("users.php")
    Call<ResponseBody> getUser();

    @GET("OrdersAndOrderLines.php?")
    Call<ResponseBody> getOrderLines(@Query("routename") int routeName, @Query("ordertype") int orderType
            , @Query("deliverydate") String deliveryDate, @Query("userId") int userId);
}
