package com.alia.sisdiary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class TimetableFragment extends ListFragment {
    public static final String ARG_NUM = "ARG_NUM";

    public static TimetableFragment newInstance(int num) {
        TimetableFragment fragment = new TimetableFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ARG_NUM, num);
        fragment.setArguments(args);

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
        String[] array = {"Математика", "Фіз-ра", "IU","PO"};
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, array));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast toast = Toast.makeText(getActivity(),
                "Item clicked: " + id, Toast.LENGTH_SHORT);
        toast.show();
    }

}
