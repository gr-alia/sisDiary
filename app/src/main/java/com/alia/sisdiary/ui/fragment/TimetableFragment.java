package com.alia.sisdiary.ui.fragment;

import android.os.Bundle;;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.adapter.TimetableFragmentPagerAdapter;


public class TimetableFragment extends Fragment {
private static final String KEY_WEEKDAY = "weekdayNumber";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_timetable, container, false);
        getActivity().setTitle(R.string.menu_title_timetable);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
TimetableFragmentPagerAdapter pagerAdapter = new TimetableFragmentPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        //connect TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        return view;
    }


}
