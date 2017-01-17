package com.alia.sisdiary;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TimetableActivity extends ListActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CreateTimetableTask().execute();
    }
    @Override
    public  void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
    public void onListItemClick(
            ListView listView,
            View itemView,
            int position,
            long id
    ) {
        Intent intent = new Intent(this, HomeWorkActivity.class);
        intent.putExtra(HomeWorkActivity.EXTRA_SUBJECTNO, (int) id);
        startActivity(intent);
    }
    private class CreateTimetableTask extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                SQLiteOpenHelper sisdiaryDatabaseHelper = new SisdiaryDatabaseHelper(TimetableActivity.this);
                db = sisdiaryDatabaseHelper.getReadableDatabase();
                cursor = db.query("SUBJECT",
                        new String[]{"_id","NAME"},
                        null, null, null, null, null
                );
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }
        protected void onPostExecute(Boolean success){
            if (!success) {
                Toast toast = Toast.makeText(TimetableActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                ListView listView = getListView();
                CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                        TimetableActivity.this,
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0);
                listView.setAdapter(cursorAdapter);
            }
        }
    }
}
