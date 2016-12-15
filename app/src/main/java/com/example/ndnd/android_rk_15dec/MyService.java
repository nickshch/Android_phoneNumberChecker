package com.example.ndnd.android_rk_15dec;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyService extends IntentService {

    final String LOG_TAG = "myLogs";
    public final static String ACTION_RESPONSE = "ru.mail.park.WEATHER_LOAD_ACTION";
    public final static String URL = "https://neutrinoapi-phone-validate.p.mashape.com/phone-validate";
    public final static String X_MASHAPE = "X-Mashape-Key";
    public final static String X_MASHAPE_KEY = "xbsaIWfudGmshHaydN3vIHCVyn43p1cLjwPjsnwO6oeBQiBplA";
    public static final String RESPONSE_STRING = "myResponse";

    public MyService() {
        super("myname");
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String responseBody = "";
        String country = intent.getStringExtra("country");
        String phone = intent.getStringExtra("phone");
        Log.d(LOG_TAG, "onHandleIntent start " + country + phone);
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("country-code", country)
                    .add("number", phone)
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
                    .post(body)
                    .addHeader("Accept", "application/json")
                    .addHeader(X_MASHAPE, X_MASHAPE_KEY)
                    .build();

            Log.d(LOG_TAG, "Executing request");

            Response response = client.newCall(request).execute();
            responseBody = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_STRING, responseBody);
        sendBroadcast(broadcastIntent);
        Log.d(LOG_TAG, "onHandleIntent end " + responseBody);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
