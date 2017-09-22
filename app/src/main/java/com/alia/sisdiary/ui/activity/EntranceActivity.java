package com.alia.sisdiary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.alia.sisdiary.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EntranceActivity extends AppCompatActivity {
    private static final String TAG = "EntranceActivity";
    private static final int REQUEST_LOGIN = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
    }

    public void onGo(View view) {
        Log.i(TAG, "Launch click GO");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
