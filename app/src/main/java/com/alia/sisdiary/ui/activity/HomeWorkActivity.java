package com.alia.sisdiary.ui.activity;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alia.sisdiary.R;

public class HomeWorkActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.alia.sisdiary.ui.activity.NAME";
    public static final String EXTRA_HOMEWORK = "com.alia.sisdiary.ui.activity.HOMEWORK";

    private static final String TAG = "HomeWorkActivity";


    private Toolbar mToolbar;
    private TextView mHomeWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        mToolbar = (Toolbar) findViewById(R.id.hw_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar appBar = getSupportActionBar();
        appBar.setDisplayHomeAsUpEnabled(true);

        mHomeWork = (TextView) findViewById(R.id.home_work);

        String subjectName = (String) getIntent().getExtras().get(EXTRA_NAME);
        String homework = (String) getIntent().getExtras().get(EXTRA_HOMEWORK);
        setTitle(subjectName);
        mHomeWork.setText(homework);
    }

    public void onAddHomeWork(View view) {
        Toast toast = Toast.makeText(this, "Homework added", Toast.LENGTH_SHORT);
        toast.show();
        // int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);
    }

    public void onDeleteHomeWork(View view) {
        Toast toast = Toast.makeText(this, "Homework deleted", Toast.LENGTH_SHORT);
        toast.show();
        // int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);
    }

}
