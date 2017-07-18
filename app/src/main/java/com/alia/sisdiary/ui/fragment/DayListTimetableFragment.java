package com.alia.sisdiary.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alia.sisdiary.App;
import com.alia.sisdiary.R;
import com.alia.sisdiary.model.DaoSession;
import com.alia.sisdiary.model.Subject;
import com.alia.sisdiary.model.SubjectDao;
import com.alia.sisdiary.ui.adapter.SubjectAdapter;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;


public class DayListTimetableFragment extends Fragment {
    public static final String ARG_NUM = "ARG_NUM";
    private static final String TAG = "DayListTimetableFragment";

    private FloatingActionButton mFab;
    private RecyclerView mTimetableRecyclerView;
    private EditText mNumberEditText;
    private EditText mNameEditText;

    private SubjectAdapter mAdapter;
    private List<Subject> mSubjects;

    private SubjectDao mSubjectDao;
    private Query<Subject> mSubjectQuery;


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
        setupViews(view);
        // get the subject DAO
        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
        mSubjectDao = daoSession.getSubjectDao();

        // query all cats, sorted a-z by their name
        mSubjectQuery = mSubjectDao.queryBuilder().orderAsc(SubjectDao.Properties.Number).build();
        updateSubjects();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String tabTitles[] = new String[]{"ПН", "ВТ", "СР", "ЧТ", "ПТ"};

        Bundle args = getArguments();
        int dayOfWeek = args.getInt(ARG_NUM);
    }

    private void setupViews(View view) {
        mTimetableRecyclerView = (RecyclerView) view
                .findViewById(R.id.timetable_recycler_view);
        mFab = (FloatingActionButton) view
                .findViewById(R.id.fab);
        mNumberEditText = (EditText) view
                .findViewById(R.id.subject_number_et);
        mNameEditText = (EditText) view
                .findViewById(R.id.subject_name_et);

        mSubjects = new ArrayList<>();
        mAdapter = new SubjectAdapter(mSubjects,subjectClickListener);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        mTimetableRecyclerView.setAdapter(mAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubject();
            }
        });
    }

    private void addSubject(){
        String number = mNumberEditText.getText().toString();
        String name = mNameEditText.getText().toString();
        mNumberEditText.setText("");
        mNameEditText.setText("");
        mNumberEditText.clearFocus();
        mNameEditText.clearFocus();

        if (name.trim().equals("") || number.trim().equals("")) {
            Toast.makeText(getActivity(), "Номер або назва не заповнені", Toast.LENGTH_SHORT).show();
            return;
        }
        Subject subject = new Subject();
        subject.setNumber(Integer.parseInt(number));
        subject.setName(name);
        mSubjectDao.insert(subject);
        updateSubjects();
    }
    public void updateSubjects() {
        mSubjects = mSubjectQuery.list();   //To load all entities into memory
        mAdapter.setSubjects(mSubjects);
    }
    SubjectAdapter.SubjectClickListener subjectClickListener = new SubjectAdapter.SubjectClickListener() {
        @Override
        public void onSubjectClick(int position) {
            Subject subject = mAdapter.getSubject(position);
            Long subjectId = subject.getId();

            mSubjectDao.deleteByKey(subjectId);
            Log.i("SubjectDao", "Deleted subject, ID: " + subjectId + " NAME: " + subject.getName());
            updateSubjects();
        }
    };

/*
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


               // CursorAdapter cursorAdapter = new SimpleCursorAdapter(
               //         getActivity(),
               //         android.R.layout.simple_list_item_1,
               //         cursor,
               //         new String[]{"NAME"},
               //        new int[]{android.R.id.text1},
               //         0);
               //setListAdapter(cursorAdapter);

                Log.i(TAG, "Fill arrayList from cursor "+ mArrayList);
                mAdapter = new SubjectAdapter(mArrayList);
                mTimetableRecyclerView.setAdapter(mAdapter);
            }
            Log.i(TAG, "onPostExecute finished");
        }
    }
    */
}
