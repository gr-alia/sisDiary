package com.alia.sisdiary.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.fragment.DayListTimetableFragment;

public class TimetableFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabSubTitles[] = new String[]{"Пн", "В", "С", "Ч", "П"};
    private String tabTitles[] = new String[]{"1", "2", "3", "4", "5"};
    private Context mContext;


    public TimetableFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    //Return the number of views available.
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //Return the Fragment associated with a specified position.

    @Override
    public Fragment getItem(int position) {
        return DayListTimetableFragment.newInstance(position + 1);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView wd = (TextView) v.findViewById(R.id.tab_weekday);
        wd.setText(tabSubTitles[position]);
        TextView num = (TextView) v.findViewById(R.id.tab_number);
        num.setText(tabTitles[position]);
        return v;
    }
}
