package com.alia.sisdiary.ui.fragment;


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
import android.widget.Toast;

import com.alia.sisdiary.App;
import com.alia.sisdiary.R;
import com.alia.sisdiary.model.DaoSession;
import com.alia.sisdiary.model.ScheduledSubject;
import com.alia.sisdiary.model.ScheduledSubjectDao;
import com.alia.sisdiary.model.Subject;
import com.alia.sisdiary.model.SubjectDao;
import com.alia.sisdiary.ui.adapter.SubjectAdapter;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;


public class DayListTimetableFragment extends Fragment {
    public static final String ARG_NUM = "ARG_NUM";
    private static final String TAG = "DayListTimetableFragment";

    private int weekdayNumber;

    private FloatingActionButton mFab;
    private RecyclerView mTimetableRecyclerView;
    private EditText mNumberEditText;
    private EditText mNameEditText;

    private SubjectAdapter mAdapter;
    private List<ScheduledSubject> mSubjects;

    private ScheduledSubjectDao mScheduledSubjectDao;
    private Query<ScheduledSubject> mScheduledSubjectQuery;

    private SubjectDao mSubjectDao;


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
        Bundle args = getArguments();
        weekdayNumber = args.getInt(ARG_NUM);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daylist_timetable, container,
                false);
        setupViews(view);
        // get the subject DAO
        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        mSubjectDao = daoSession.getSubjectDao();
        mScheduledSubjectDao = daoSession.getScheduledSubjectDao();

        // query all subjects, sorted 1-n by their lesson number
        mScheduledSubjectQuery = mScheduledSubjectDao.queryBuilder()
                .where(ScheduledSubjectDao.Properties.Weekday.eq(weekdayNumber))
                .orderAsc(ScheduledSubjectDao.Properties.LessonNumber)
                .build();
        updateSubjects();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
        mAdapter = new SubjectAdapter(mSubjects, subjectClickListener);
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

    private void addSubject() {
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
        subject.setName(name);
        mSubjectDao.insert(subject);
        Log.i(TAG, "Subject has id: " + subject.getId());
        ScheduledSubject scheduledSubject = new ScheduledSubject(subject);
        scheduledSubject.setLessonNumber(Integer.parseInt(number));
        Log.i(TAG, "Set this weekday number: " + weekdayNumber);
        scheduledSubject.setWeekday(weekdayNumber);
        mScheduledSubjectDao.insert(scheduledSubject);
        updateSubjects();
    }


    public void updateSubjects() {
        mSubjects = mScheduledSubjectQuery.list();   //To load all entities into memory
        mAdapter.setSubjects(mSubjects);
    }


    SubjectAdapter.SubjectClickListener subjectClickListener = new SubjectAdapter.SubjectClickListener() {
        @Override
        public void onSubjectClick(ScheduledSubject subjectItem) {

            //ScheduledSubject scheduledSubject = mAdapter.getScheduleSubject(position);
            Long scheduledSubjectId = subjectItem.getId();

            mScheduledSubjectDao.deleteByKey(scheduledSubjectId);
            Log.i(TAG, "Deleted subject, ID: " + scheduledSubjectId + " NAME: " + subjectItem.getSubject().getName());
            updateSubjects();
        }
    };



}
