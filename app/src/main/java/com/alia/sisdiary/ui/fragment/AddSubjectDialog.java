package com.alia.sisdiary.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.alia.sisdiary.model.ScheduledSubject;
import com.alia.sisdiary.model.Subject;

import java.util.Date;


/**
 * Created by Alyona on 31.08.2017.
 */

public class AddSubjectDialog extends DialogFragment {
    public static final String EXTRA_NAME =
            "com.alia.sisdiary.date.ui.fragment.NAME";
    public static final String EXTRA_NUMBER =
            "com.alia.sisdiary.date.ui.fragment.NUMBER";
    public static final String EXTRA_TIME =
            "com.alia.sisdiary.date.ui.fragment.TIME";


    private EditText mNumberEditText;
    private EditText mNameEditText;
    private TimePicker mTimePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_add_subject, null);
        mNameEditText = (EditText) v.findViewById(R.id.edit_name);
        mNumberEditText = (EditText) v.findViewById(R.id.edit_number);
        mTimePicker = (TimePicker) v.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_tittle)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = mNameEditText.getText().toString();
                        String number = mNumberEditText.getText().toString();
                        int[] time = new int[2];
                        time[0] = mTimePicker.getCurrentHour();
                        time[1] = mTimePicker.getCurrentMinute();

                        if (name.trim().equals("") || number.trim().equals("")) {
                            Toast.makeText(getActivity(), "Номер або назва не заповнені", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sendResult(Activity.RESULT_OK, name, number, time);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultCode, String name, String number, int[] time) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_NUMBER, number);
        intent.putExtra(EXTRA_TIME, time);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
