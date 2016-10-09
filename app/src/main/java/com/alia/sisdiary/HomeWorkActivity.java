package com.alia.sisdiary;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HomeWorkActivity extends AppCompatActivity {
public static final String EXTRA_SUBJECTNO = "subjectNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);
        Subject subject = Subject.subjects[subjectNO];

        TextView subjectName = (TextView) findViewById(R.id.subject_name);
        subjectName.setText(subject.getName());
    }
    public void onAddHomeWork(View view){
        EditText homeWorkEdit = (EditText)findViewById(R.id.editHomeWork);
        String  homeWorkText =  homeWorkEdit.getText().toString();
        TextView homeWorkView = (TextView) findViewById(R.id.homeWork);
        homeWorkView.setText(homeWorkText);
    }
}
