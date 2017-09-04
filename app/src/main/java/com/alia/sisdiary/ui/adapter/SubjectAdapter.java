package com.alia.sisdiary.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.alia.sisdiary.model.ScheduledSubject;
import com.alia.sisdiary.model.Subject;
import com.alia.sisdiary.ui.activity.HomeWorkActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alyona on 17.07.2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {
    private Context mContext;
    private SubjectClickListener mClickListener;
    private List<ScheduledSubject> mSubjects;

    private boolean multiSelect = false;
    private ArrayList<ScheduledSubject> selectedItems = new ArrayList<>();
     private ActionMode mActionMode;

    public interface SubjectClickListener {
        void onSubjectClick(ScheduledSubject subjectItem);
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_action_bar_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete_subject:
                    for (ScheduledSubject scheduledSubjectItem : selectedItems) {
                        mClickListener.onSubjectClick(scheduledSubjectItem);
                    }
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
            mActionMode = null;
        }
    };


    public SubjectAdapter(Context context, List<ScheduledSubject> subjects, SubjectClickListener clickListener) {
        this.mContext = context;
        this.mClickListener = clickListener;
        this.mSubjects = subjects;
    }

    @Override
    public SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater
                .inflate(R.layout.list_item_subject, parent, false);
        return new SubjectHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(SubjectHolder holder, int position) {
        ScheduledSubject subject = mSubjects.get(position);
        holder.bindSubject(subject);
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    class SubjectHolder extends RecyclerView.ViewHolder {

        private LinearLayout mSubjectItem;
        private TextView mNumberTextView;
        private TextView mTimeTextView;
        private TextView mNameTextView;
        private ScheduledSubject mSubject;

        public SubjectHolder(View itemView, final SubjectClickListener clickListener) {
            super(itemView);
            mSubjectItem = (LinearLayout)
                    itemView.findViewById(R.id.subject_item);
            mNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_subject_name);
            mNumberTextView = (TextView)
                    itemView.findViewById(R.id.list_item_subject_number);
            mTimeTextView = (TextView)
                    itemView.findViewById(R.id.list_item_time);
        }




        public void selectItem(ScheduledSubject item) {
            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    mSubjectItem.setBackgroundColor(Color.WHITE);
                } else {
                    selectedItems.add(item);
                    mSubjectItem.setBackgroundColor(Color.LTGRAY);
                }
            }
        }

        public void bindSubject(final ScheduledSubject subject) {
            mSubject = subject;
            mNameTextView.setText(mSubject.getSubject().getName());
            mNumberTextView.setText(String.valueOf(mSubject.getLessonNumber()));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            mTimeTextView.setText(simpleDateFormat.format(subject.getLessonTime()));

            if (selectedItems.contains(subject)) {
                mSubjectItem.setBackgroundColor(Color.LTGRAY);
            } else {
                mSubjectItem.setBackgroundColor(Color.WHITE);
            }
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mActionMode != null) {
                        return false;
                    }
                    mActionMode = ((AppCompatActivity) mContext).startSupportActionMode(actionModeCallbacks);
                    selectItem(subject);
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mActionMode != null) {
                        selectItem(subject);
                        if (selectedItems.isEmpty())
                            mActionMode.finish();
                    }
                    else {
                        Toast toast = Toast.makeText(itemView.getContext(), "Item clicked: ", Toast.LENGTH_LONG);
                        toast.show();
                            Intent intent = new Intent(mContext, HomeWorkActivity.class);
                           // intent.putExtra(HomeWorkActivity.EXTRA_SUBJECTNO, (int) mSubject.getId());
                            mContext.startActivity(intent);


                    }
                }
            });
        }

    }

    public ScheduledSubject getScheduleSubject(int position) {
        return mSubjects.get(position);
    }

    public void setSubjects(List<ScheduledSubject> subjects) {
        mSubjects = subjects;
        notifyDataSetChanged();
    }


}
