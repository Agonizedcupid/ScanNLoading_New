package com.aariyan.scannloading.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.aariyan.scannloading.Activity.HeaderNLineActivity;
import com.aariyan.scannloading.Constant.Constant;
import com.aariyan.scannloading.Database.DatabaseAdapter;
import com.aariyan.scannloading.Database.SharedPreferences;
import com.aariyan.scannloading.MainActivity;
import com.aariyan.scannloading.Model.PostLinesModel;
import com.aariyan.scannloading.Model.QueueModel;
import com.aariyan.scannloading.Network.APIs;
import com.aariyan.scannloading.Network.ApiClient;
import com.aariyan.scannloading.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;

public class PostLinesService extends Service {
    //Tag for checking the targeted result on LogCat:
    private static final String TAG = "MainService";

    //ExecutorService API Instance variable(This is basically a concurrency member variable):
    private ExecutorService mExecutorService;

    //For checking is the service is running or not:
    public static boolean isServiceRunning;

    private Monitoring monitoring;

    //Channel id will be used for NotificationChannel:
    private String CHANNEL_ID = "com.aariyan.scannloading";

    public PostLinesService() {
        //Making the service running false initially:
        isServiceRunning = false;
    }

    //
//    public MonitoringService (SupervisorController supervisorController, GetRunningForegroundApp getRunningForegroundApp) {
//        this.supervisorController = supervisorController;
//        this.getRunningForegroundApp = getRunningForegroundApp;
//    }
    APIs apIs;

    @Override
    public void onCreate() {
        super.onCreate();
        //Creating a notification channel for latest android version
        createNotificationChannel();
        //notifying the service is running:
        isServiceRunning = true;

        apIs = ApiClient.getClient().create(APIs.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Showing the notification as Foreground service:
        showNotification();
        //Running some background thread for Asynchronous task:
        triggerTheMonitor();
        //Toast.makeText(getApplicationContext(), "Service Started!!", Toast.LENGTH_SHORT).show();
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    //It will show the notification to aware the user that, Supervisor is running in the Foreground, Chill!
    private void showNotification() {
        //This intent will be used as pending intent; means when user will click on notification tab it will open this activity:
        Intent notificationIntent = new Intent(this, MainActivity.class);
        //Attaching the pending intent:
        //PendingIntent.FLAG_IMMUTABLE is used for >= android 11:
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                //this is the notification title:
                .setContentTitle("Service is Running")
                //Notification sub-title:
                .setContentText("Activity Tracking!")
                //notification icon:
                .setSmallIcon(R.mipmap.ic_launcher)
                //setting the pending intent on the notification:
                .setContentIntent(pendingIntent)
                //set the background color of intent
                .setColor(getResources().getColor(R.color.teal_700))
                //Finally build the notification to show:
                .build();
        /**
         * A started service can use the startForeground API to put the service in a foreground state,
         * where the system considers it to be something the user is actively aware of and thus not
         * a candidate for killing when low on memory.
         */
        // it will starting show the ForeGround notification:
        startForeground(new Random().nextInt(), notification);
    }

    //Notification channel is only needed for above Oreo:
    private void createNotificationChannel() {
        //Checking the device OS version:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //App name
            String appName = getString(R.string.app_name);
            //creating the notification channel here and adding all the information:
            NotificationChannel serviceChannel = new NotificationChannel(
                    //Channel id. that could be anything but same package name is recommended:
                    CHANNEL_ID,
                    //Putting the app name to show
                    appName,
                    //This is the importance on notification showing:
                    //For now we are setting as Default:
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            //Instantiating the Notification Manager:
            NotificationManager manager = getSystemService(NotificationManager.class);
            //Finally creating the notification channel and passing as parameter of manager:
            manager.createNotificationChannel(serviceChannel);
        }
    }

    //Running background thread for Asynchronously working:
    public void triggerTheMonitor() {
        //Instantiating the concurrency service:
        mExecutorService = Executors.newSingleThreadExecutor();
        //Creating an object of StartMonitoring class for submitting on the background continuous running:
        //monitoring = new StartMonitoring();
        monitoring = new Monitoring();
        //monitoring = new Monitoring(this);
        // Finally, submit the monitoring object to the background concurrency:
        mExecutorService.submit(monitoring);
        Log.e(TAG, "onCreateService: Started, service is running");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DummyBinderForTestingService();
    }

    @Override
    public void onDestroy() {
        isServiceRunning = false;
        stopForeground(true);

        if (mExecutorService != null) {
            mExecutorService.shutdown();
        }
        super.onDestroy();
    }

    public class DummyBinderForTestingService extends Binder {
        PostLinesService getService() {
            return PostLinesService.this;
        }
    }

    private class Monitoring implements Runnable {
        List<QueueModel> toBePostedData = new ArrayList<>();
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(PostLinesService.this);

        @Override
        public void run() {
            while (true) {
                toBePostedData = new DatabaseAdapter(PostLinesService.this).getQueue();
                if (toBePostedData.size() > 0) {
                    postLinesData(toBePostedData);
                    Log.d("FEEDBACK", "" + toBePostedData.size());
                    //postLinesByRetrofit(toBePostedData);
                } else {
                    Log.d(TAG, "run: No data");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        private void postLinesByRetrofit(List<QueueModel> getStock) {
//            apIs.post(getStock).enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                    Log.d("FEEDBACK", response.body().toString());
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    Log.d("FEEDBACK", t.getMessage());
//                }
//            });
//
////            SharedPreferences sharedPreferences = new SharedPreferences(PostLinesService.this);
////            String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL);
////            String URL = appendedUrl + "PostQueueStaging.php";
////            Log.d(TAG, "postLinesData: " + URL);
////            Log.d(TAG, "Size: " + getStock);
////            for (QueueModel model : getStock) {
////                Log.d(TAG, "print: " + model.getIds() + "\n" + model.getTypes() + "\n" + model.getInstrunctions());
////            }
//        }

        private void postLinesData(List<QueueModel> getStock) {
            SharedPreferences sharedPreferences = new SharedPreferences(PostLinesService.this);
            String appendedUrl = sharedPreferences.getURL(Constant.IP_MODE_KEY, Constant.IP_URL);
            String URL = appendedUrl + "PostQueueStaging.php";
            Log.d(TAG, "postLinesData: " + URL);
            Log.d(TAG, "Size: " + getStock);
            for (QueueModel model : getStock) {
                Log.d(TAG, "print: " + model.getIds() + "\n" + model.getTypes() + "\n" + model.getInstrunctions());
            }

            if (getStock.size() > 0) {
                StringRequest mStringRequest = new StringRequest(
                        Request.Method.POST,
                        appendedUrl + "PostQueueStaging.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                for (int i = 0; i < toBePostedData.size(); i++) {
                                    QueueModel model = toBePostedData.get(i);
                                    databaseAdapter.deleteQueue(model.getIds());
                                }

                                toBePostedData.clear();
                                Log.d("FEEDBACK", response);


                                Toast.makeText(PostLinesService.this, response.toString() + " Posted successfully!", Toast.LENGTH_SHORT).show();
                                //Now Removing the data from SQLite:
                                //deleteUploadedJobs();
                                //new DatabaseAdapter(PostLinesService.this).dropQueueTable();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostLinesService.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("FEEDBACK", "" + error.getMessage());
                    }
                }
                ) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        String jsonString = new Gson().toJson(getStock).toString();
                        Log.d("FEEDBACK", jsonString);
                        return jsonString.getBytes();
                    }
                };
                Volley.newRequestQueue(PostLinesService.this).add(mStringRequest);
            } else {
                Toast.makeText(PostLinesService.this, "Not enough data!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}