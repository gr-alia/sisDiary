package com.alia.sisdiary;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeWorkActivity extends AppCompatActivity {
public static final String EXTRA_SUBJECTNO = "subjectNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);
        Subject subject = Subject.subjects[subjectNO];

        TextView homework_view = (TextView) findViewById(R.id.homework_view);
        homework_view.setText(subject.getName());
    }
}
