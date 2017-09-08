package com.alia.sisdiary.ui.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alia.sisdiary.R;

public class HomeWorkActivity extends AppCompatActivity {
    public static final String EXTRA_SUBJECTNO = "subjectNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

      //  int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);

    }

    public void onAddHomeWork(View view) {
        EditText homeWorkEdit = (EditText) findViewById(R.id.editHomeWork);
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
