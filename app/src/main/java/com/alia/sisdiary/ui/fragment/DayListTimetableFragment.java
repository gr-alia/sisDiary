package com.alia.sisdiary.ui.fragment;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.ImageButton;
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
import java.util.Date;
import java.util.List;


public class DayListTimetableFragment extends Fragment {
    private static final String ARG_NUM = "ARG_NUM";
    private static final String TAG = "DayListTimetableFragment";
    private static final String DIALOG_ADD_SUBJECT = "DialogAddSubject";
    private static final int REQUEST_NEW_SUBJECT = 0;

    private int weekdayNumber;

    private ImageButton mAddButton;
    private RecyclerView mTimetableRecyclerView;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_NEW_SUBJECT) {
            String name = (String) data.getSerializableExtra(AddSubjectDialog.EXTRA_NAME);
            String number = (String) data.getSerializableExtra(AddSubjectDialog.EXTRA_NUMBER);
            int[] time = (int[]) data.getSerializableExtra(AddSubjectDialog.EXTRA_TIME);
            Subject subject = new Subject();
            subject.setName(name);
            mSubjectDao.insert(subject);
            Log.i(TAG, "Subject has id: " + subject.getId());
            ScheduledSubject scheduledSubject = new ScheduledSubject(subject);
            scheduledSubject.setLessonNumber(Integer.parseInt(number));
            scheduledSubject.setLessonIntTime(time[0], time[1]);
            Log.i(TAG, "Set this weekday number: " + weekdayNumber);
            scheduledSubject.setWeekday(weekdayNumber);
            mScheduledSubjectDao.insert(scheduledSubject);
            updateSubjects();
        }

    }

    private void setupViews(View view) {
        mTimetableRecyclerView = (RecyclerView) view
                .findViewById(R.id.timetable_recycler_view);
        mAddButton = (ImageButton) view.findViewById(R.id.add_button);

        mSubjects = new ArrayList<>();
        mAdapter = new SubjectAdapter(getContext(), mSubjects, subjectClickListener);
        mTimetableRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        mTimetableRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubject();
            }
        });
    }


    private void addSubject() {
        AddSubjectDialog addSubjectDialog = new AddSubjectDialog();
        addSubjectDialog.setTargetFragment(DayListTimetableFragment.this, REQUEST_NEW_SUBJECT);
        addSubjectDialog.show(getFragmentManager(), DIALOG_ADD_SUBJECT);


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
