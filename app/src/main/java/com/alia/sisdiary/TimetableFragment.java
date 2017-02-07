package com.alia.sisdiary;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


public class TimetableFragment extends ListFragment {
    public static final String ARG_NUM = "ARG_NUM";
    private static final String TAG = "TimetableFragment";

    private SQLiteDatabase db;
    private Cursor cursor;

    public static TimetableFragment newInstance(int num) {

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ARG_NUM, num);
        TimetableFragment fragment = new TimetableFragment();
        fragment.setArguments(args);
        Log.d(TAG, "Launch newInstance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String tabTitles[] = new String[]{"ПН", "ВТ", "СР", "ЧТ", "ПТ"};

        Bundle args = getArguments();
        int dayOfWeek = args.getInt(ARG_NUM);

        new CreateTimetableTask().execute(tabTitles[dayOfWeek - 1]);

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Log.i(TAG, "Item clicked: " + id);

        Intent intent = new Intent(getActivity(), HomeWorkActivity.class);
        intent.putExtra(HomeWorkActivity.EXTRA_SUBJECTNO, (int) id);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();

    }

    private class CreateTimetableTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String dayOfWeek = strings[0];
            try {
                SQLiteOpenHelper sisdiaryDatabaseHelper = new SisdiaryDatabaseHelper(getContext());
                db = sisdiaryDatabaseHelper.getReadableDatabase();
                cursor = db.query("SUBJECT",
                        new String[]{"_id", "NAME"},
                        "DAY=?",
                        new String[]{dayOfWeek},
                        null, null, null
                );

                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(getActivity(),
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0);
                setListAdapter(cursorAdapter);

            }
        }
    }
}
