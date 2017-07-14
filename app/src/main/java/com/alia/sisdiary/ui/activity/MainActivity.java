package com.alia.sisdiary.ui.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.fragment.TimetableFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";



    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, " Launched MainActivity OnCreate");
        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       // fragment = TimetableFragment.newInstance(1);
        fragment = new TimetableFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();

    }
}
