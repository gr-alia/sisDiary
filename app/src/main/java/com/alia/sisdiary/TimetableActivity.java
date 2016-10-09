package com.alia.sisdiary;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    public void onListItemClick(
            ListView listView,
            View itemView,
            int position,
            long id
    ){
        Intent intent = new Intent(this, HomeWorkActivity.class);
        intent.putExtra(HomeWorkActivity.EXTRA_SUBJECTNO, (int) id);
        startActivity(intent);
    }
}
