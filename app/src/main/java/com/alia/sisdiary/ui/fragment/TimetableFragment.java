package com.alia.sisdiary.ui.fragment;

import android.os.Bundle;;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.adapter.TimetableFragmentPagerAdapter;


public class TimetableFragment extends Fragment {

    private static final String TAG = "TimetableFragment";

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public TimetableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        getActivity().setTitle(R.string.menu_title_timetable);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TimetableFragmentPagerAdapter(getChildFragmentManager()));

        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        //connect TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
