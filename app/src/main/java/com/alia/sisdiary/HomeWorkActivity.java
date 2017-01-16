package com.alia.sisdiary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeWorkActivity extends AppCompatActivity {
    public static final String EXTRA_SUBJECTNO = "subjectNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);

        //Create Cursor
        try {
            SQLiteOpenHelper sisdiaryDatabaseHelper = new SisdiaryDatabaseHelper(this);
            SQLiteDatabase db = sisdiaryDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("SUBJECT",
                    new String[]{"NAME", "HOMEWORK"},
                    "_id = ?",
                    new String[]{Integer.toString(subjectNO)},
                    null, null, null);
            //move to row
            if (cursor.moveToFirst()) {
                //get values of row from Cursor
                String subjectNameText = cursor.getString(0);  // here 0 is "NAME" from String[] columns
                String homeworkText = cursor.getString(1);

                //fill in subject name
                TextView subjectName = (TextView) findViewById(R.id.subject_name);
                subjectName.setText(subjectNameText);
                //fill in subject homework
                TextView homework = (TextView) findViewById(R.id.homeWork);
                homework.setText(homeworkText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public void onAddHomeWork(View view) {
        EditText homeWorkEdit = (EditText) findViewById(R.id.editHomeWork);
        int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);
        new UpdateHomeWorkTask(homeWorkEdit.getText().toString()).execute(subjectNO);
    }

    public void onDeleteHomeWork(View view) {
        int subjectNO = (Integer) getIntent().getExtras().get(EXTRA_SUBJECTNO);
        new UpdateHomeWorkTask(" ").execute(subjectNO);
    }

    private class UpdateHomeWorkTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues homeworkValues;
        String homeworkText;
        String value;
        public UpdateHomeWorkTask(String value){
            this.value = value;
        }
        protected void onPreExecute(){
            homeworkValues = new ContentValues();
            homeworkValues.put("HOMEWORK", value);
        }
        protected Boolean doInBackground(Integer... subjects) {
            int subjectNO = subjects[0];
            SQLiteOpenHelper sisdiaryDatabaseHelper = new SisdiaryDatabaseHelper(HomeWorkActivity.this);
            try {
                //clear current homework in DB here
                SQLiteDatabase db = sisdiaryDatabaseHelper.getWritableDatabase();
                db.update("SUBJECT",
                        homeworkValues,
                        "_id=?",
                        new String[]{Integer.toString(subjectNO)});
                //read data from DB here
                Cursor cursor = db.query("SUBJECT",
                        new String[]{"HOMEWORK"},
                        "_id = ?",
                        new String[]{Integer.toString(subjectNO)},
                        null, null, null);
                //move to row
                if (cursor.moveToFirst()) {
                    //get values of row from Cursor
                    homeworkText = cursor.getString(0);

                }
                cursor.close();
                db.close();
                return true;
            } catch (SQLiteException e) {
               return false;
            }
        }
        protected void onPostExecute(Boolean success){
            if (!success) {
                Toast toast = Toast.makeText(HomeWorkActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                //fill in subject homework
                TextView homework = (TextView) findViewById(R.id.homeWork);
                homework.setText(homeworkText);
            }
        }

    }
}
