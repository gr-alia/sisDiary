package com.alia.sisdiary.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.alia.sisdiary.model.ScheduledSubject;

/**
 * Created by Alyona on 09.09.2017.
 */

public class AddHomeWorkDialog extends DialogFragment {
    private static final String ARG_HOMEWORK = "homework";
    public static final String EXTRA_HOMEWORK =
            "com.alia.sisdiary.ui.fragment.HOMEWORK";

    private EditText mHomeWork;

    public AddHomeWorkDialog() {

    }

    public static AddHomeWorkDialog newInstance(String homework) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_HOMEWORK, homework);
        AddHomeWorkDialog dialog = new AddHomeWorkDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String homework = (String) getArguments().getSerializable(ARG_HOMEWORK);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_homework, null);
        mHomeWork = (EditText) v.findViewById(R.id.edit_homework);
        mHomeWork.setText(homework);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_hw_tittle)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String homework = mHomeWork.getText().toString();

                        if (homework.trim().equals("")) {
                            Toast.makeText(getActivity(), "Поле не заповнено", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (getTargetFragment() == null) {
                            return;
                        }
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_HOMEWORK, homework);

                        getTargetFragment()
                                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }
}
