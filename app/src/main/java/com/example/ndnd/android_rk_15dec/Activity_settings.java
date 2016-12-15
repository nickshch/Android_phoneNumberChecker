package com.example.ndnd.android_rk_15dec;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Activity_settings extends AppCompatActivity {

    public static final String APP_PREFERENCES = "mysettings";

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroupLang);

        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        if(mSettings.contains("country")) {
            String country = mSettings.getString("country", "");
            switch (country) {
                case "RU":
                    radio.check(R.id.rb_RU);
                    break;
                case "GB":
                    radio.check(R.id.rb_GB);
                    break;
                case "DE":
                    radio.check(R.id.rb_DE);
                    break;
                case "FR":
                    radio.check(R.id.rb_FR);
                    break;
            }
        } else {
            radio.check(R.id.rb_RU);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rb_RU:
                saveText("RU");
                break;
            case R.id.rb_DE:
                saveText("DE");
                break;
            case R.id.rb_FR:
                saveText("FR");
                break;
            case R.id.rb_GB:
                saveText("GB");
                break;

            default:
                break;
        }
    }

    void saveText(String text) {
        mSettings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("country", text);
        editor.apply();
    }


}
