package com.alia.sisdiary;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TimetableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<Subject> listAdapter = new ArrayAdapter<Subject>(
                this,
                android.R.layout.simple_list_item_1,
                Subject.subjects
        );

        ListView listView = getListView();
        listView.setAdapter(listAdapter);

    }
}
