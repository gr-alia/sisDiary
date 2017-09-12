package com.alia.sisdiary.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.alia.sisdiary.ui.fragment.DayListTimetableFragment;

public class TimetableFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"1", "2", "3", "4", "5"};


    public TimetableFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    //Return the number of views available.
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //Return the Fragment associated with a specified position.

    @Override
    public Fragment getItem(int position) {
        Log.i("Alyo", "getItem was launched");
        return DayListTimetableFragment.newInstance(position + 1);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
