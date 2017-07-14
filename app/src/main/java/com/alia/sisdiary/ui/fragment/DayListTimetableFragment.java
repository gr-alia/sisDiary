package com.alia.sisdiary.ui.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.alia.sisdiary.database.SisdiaryDatabaseHelper;
import com.alia.sisdiary.model.Subject;

import java.util.ArrayList;
import java.util.List;


public class DayListTimetableFragment extends Fragment {
    public static final String ARG_NUM = "ARG_NUM";
    private static final String TAG = "DayListTimetableFragment";

    private RecyclerView mTimetableRecyclerView;
    private SubjectAdapter mAdapter;
    private SQLiteDatabase db;
    private Cursor cursor;
    private List<Subject> mArrayList = new ArrayList<>();

    public DayListTimetableFragment() {
    }

    public static DayListTimetableFragment newInstance(int num) {

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ARG_NUM, num);
        DayListTimetableFragment fragment = new DayListTimetableFragment();
        fragment.setArguments(args);
        Log.i(TAG, "Launch newInstance");
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daylist_timetable, container,
                false);
        mTimetableRecyclerView = (RecyclerView) view
                .findViewById(R.id.timetable_recycler_view);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));


        return view;
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
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();

    }

    private class SubjectHolder extends RecyclerView.ViewHolder {

        private TextView mTitleTextView;
        private TextView mIdTextView;

        private Subject mSubject;

        public SubjectHolder(View itemView) {
            super(itemView);
            // itemView.setOnClickListener(this);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_subject_title_text_view);
           // mIdTextView = (TextView)
             //       itemView.findViewById(R.id.list_item_subject_id_text_view);
        }

        public void bindSubject(Subject subject, int position) {
            mSubject = subject;
            Log.i(TAG, "bindSubject set this TittleText " + mSubject.getName());
            mTitleTextView.setText(mSubject.getName());
            Log.i(TAG, "Adapter bing subject and ViewHolder in position "+ position);
            // mIdTextView.setText(position);
        }
        /*
        @Override
        public void onClick(View v) {
            Log.i(TAG, "Item clicked: " + mSubject.getId());

            Intent intent = new Intent(getActivity(), HomeWorkActivity.class);
            intent.putExtra(HomeWorkActivity.EXTRA_SUBJECTNO, (int) mSubject.getId());
            startActivity(intent);

        }
        */

    }

    private class SubjectAdapter extends RecyclerView.Adapter<SubjectHolder> {
        private List<Subject> mSubjects;

        public SubjectAdapter(List<Subject> subjects) {
            this.mSubjects = subjects;
            Log.i(TAG, "Create new SubjectAdapter, field mSubjects= " + mSubjects);
        }

        @Override
        public SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_subject, parent, false);
            return new SubjectHolder(view);
        }

        @Override
        public void onBindViewHolder(SubjectHolder holder, int position) {
            Subject subject = mSubjects.get(position);
            holder.bindSubject(subject, position);
        }

        @Override
        public int getItemCount() {
            return mSubjects.size();
        }
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
            Log.i(TAG, "doInBackground finished");
            if (!success) {
                Toast toast = Toast.makeText(getActivity(),
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (cursor.moveToFirst()) {
                    do {
                        Subject subject = new Subject();
                        subject.setName(cursor.getString(1));
                        mArrayList.add(subject);
                    } while (cursor.moveToNext());
                }

                /*
                CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"NAME"},
                        new int[]{android.R.id.text1},
                        0);
                setListAdapter(cursorAdapter);
                */
                Log.i(TAG, "Fill arrayList from cursor "+ mArrayList);
                mAdapter = new SubjectAdapter(mArrayList);
                mTimetableRecyclerView.setAdapter(mAdapter);
            }
            Log.i(TAG, "onPostExecute finished");
        }
    }

}
