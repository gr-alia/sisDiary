package com.alia.sisdiary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.alia.sisdiary.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EntranceActivity extends AppCompatActivity {
    private static final String TAG = "EntranceActivity";
    private static final int REQUEST_LOGIN = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);

        SharedPreferences spUserData = getSharedPreferences(
                getString(R.string.pref_user_data), MODE_PRIVATE);
        boolean isContainsSP = spUserData.contains(getString(R.string.saved_user_id));
        if (!isContainsSP) {
            Intent intentLogin = new Intent(this, LoginActivity.class);
            startActivityForResult(intentLogin, REQUEST_LOGIN);
        } else {
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }
        finish();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
