package com.alia.sisdiary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alyona on 19.01.2017.
 */

public class TimetableFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "ПН", "ВТ", "СР", "ЧТ", "ПТ" };
    private Context context;

    public TimetableFragmentPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }
    //Return the number of views available.
    @Override
    public int getCount(){
        return PAGE_COUNT;
    }
    //Return the Fragment associated with a specified position.
    @Override
    public Fragment getItem(int position) {
        return TimetableFragment.newInstance(position + 1);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
