package com.alia.sisdiary.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alia.sisdiary.R;
import com.alia.sisdiary.model.ScheduledSubject;
import com.alia.sisdiary.model.Subject;

import java.util.List;

/**
 * Created by Alyona on 17.07.2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectHolder> {
    private SubjectClickListener mClickListener;
    private List<ScheduledSubject> mSubjects;

    public interface SubjectClickListener {
        void onSubjectClick(int position);
    }

    class SubjectHolder extends RecyclerView.ViewHolder {

        private TextView mNumberTextView;
        private TextView mNameTextView;
        private ScheduledSubject mSubject;

        public SubjectHolder(View itemView, final SubjectClickListener clickListener) {
            super(itemView);
            mNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_subject_name);
            mNumberTextView = (TextView)
                    itemView.findViewById(R.id.list_item_subject_number);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onSubjectClick(getAdapterPosition());
                    }
                }
            });
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
        public void bindSubject(ScheduledSubject subject) {
            mSubject = subject;
            mNameTextView.setText(mSubject.getSubject().getName());
            mNumberTextView.setText(String.valueOf(mSubject.getLessonNumber()));
        }
    }

    public SubjectAdapter(List<ScheduledSubject> subjects, SubjectClickListener clickListener) {
        this.mClickListener = clickListener;
        this.mSubjects = subjects;
    }

    public ScheduledSubject getScheduleSubject(int position) {
        return mSubjects.get(position);
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

    public void setSubjects(List<ScheduledSubject> subjects) {
        mSubjects = subjects;
        notifyDataSetChanged();
    }


}
