package com.alia.sisdiary.ui.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.fragment.AddSubjectDialog;
import com.alia.sisdiary.ui.fragment.DayListTimetableFragment;
import com.alia.sisdiary.ui.fragment.TimetableFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";




    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, " Launched MainActivity OnCreate");
        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new TimetableFragment();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();

    }



}
