package com.example.ndnd.android_rk_15dec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_number extends AppCompatActivity {

    private TextView textView_countryCode;
    private TextView textView_jsonResult;
    private EditText editText_phone;
    private MyBroadcastReceiver mMyBroadcastReceiver;

    public static final String APP_PREFERENCES = "mysettings";

    private SharedPreferences mSettings;

    String country;
    JSONObject jObject;
    String jsonResult ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(MyService.ACTION_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);

        textView_countryCode = (TextView) findViewById(R.id.textViewCountry);
        textView_jsonResult = (TextView) findViewById(R.id.textView_response);

        editText_phone = (EditText) findViewById(R.id.editText);


        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        if(mSettings.contains("country")) {
            country = mSettings.getString("country", "");
            textView_countryCode.setText(textView_countryCode.getText().toString().concat(country));
        }

        Button button_phone = (Button) findViewById(R.id.button_check);

        button_phone.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)  {
                Intent intentMyIntentService = new Intent(Activity_number.this, MyService.class);
                intentMyIntentService.putExtra("country", country);
                intentMyIntentService.putExtra("phone", editText_phone.getText().toString());
                startService(intentMyIntentService);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(MyService.RESPONSE_STRING);
            jsonResult = "";
            try {
                jObject = new JSONObject(result);
                if (jObject.has("valid")) {
                    jsonResult += "Valid: " + jObject.getString("valid") + "\n";
                }
                if (jObject.has("international-number")) {
                    jsonResult += "International-number: " + jObject.getString("international-number") + "\n";
                }
                if (jObject.has("international-calling-code")) {
                    jsonResult += "International-calling-code: " + jObject.getString("international-calling-code") + "\n";
                }
                if (jObject.has("type")) {
                    jsonResult += "Type: " + jObject.getString("type") + "\n";
                }
                if (jObject.has("is-mobile")) {
                    jsonResult += "Is-mobile: " + jObject.getString("is-mobile") + "\n";
                }
                if (jObject.has("local-number")) {
                    jsonResult += "Local-number: " + jObject.getString("local-number") + "\n";
                }
                if (jObject.has("country-code")) {
                    jsonResult += "Country-code: " + jObject.getString("country-code") + "\n";
                }

                if (jObject.has("api-error")) {
                    jsonResult += "api-error: " + jObject.getString("api-error") + "\n";
                }
                if (jObject.has("api-error-msg")) {
                    jsonResult += "api-error-msg: " + jObject.getString("api-error-msg") + "\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            textView_jsonResult.setText(jsonResult);

        }
    }
}


