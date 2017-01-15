package com.alia.sisdiary;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
        ListView listView = getListView();
        try {
            SQLiteOpenHelper sisdiaryDatabaseHelper = new SisdiaryDatabaseHelper(this);
            db = sisdiaryDatabaseHelper.getReadableDatabase();
            cursor = db.query("SUBJECT",
                    new String[]{"_id","NAME"},
                    null, null, null, null, null
            );
            CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listView.setAdapter(cursorAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }

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
}
