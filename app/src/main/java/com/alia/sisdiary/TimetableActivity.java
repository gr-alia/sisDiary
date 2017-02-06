package com.alia.sisdiary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TimetableFragmentPagerAdapter(getSupportFragmentManager(),TimetableActivity.this));


        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        //connect TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager);

    }

}
