package com.alia.sisdiary.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.adapter.TimetableFragmentPagerAdapter;

/**
 * Created by Alyona on 06.09.2017.
 */

public class NotesFragment extends Fragment {
    public NotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        getActivity().setTitle(R.string.menu_title_notes);


        return view;
    }
}
