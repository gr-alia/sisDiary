package com.alia.sisdiary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.alia.sisdiary.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EntranceActivity extends AppCompatActivity {
    private static final String TAG = "EntranceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    public void onComeIn(View view) {
        Log.i(TAG, "Launch click ComeIN");
        EditText nameView = (EditText) findViewById(R.id.name);
        String childName = nameView.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, childName);
        startActivity(intent);

    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
