package com.alia.sisdiary.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alia.sisdiary.R;
import com.alia.sisdiary.ui.fragment.AddSubjectDialog;
import com.alia.sisdiary.ui.fragment.DayListTimetableFragment;
import com.alia.sisdiary.ui.fragment.NotesFragment;
import com.alia.sisdiary.ui.fragment.TimetableFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String EXTRA_USERNAME = "com.alia.sisdiary.ui.activity.USERNAME";
    public static final String EXTRA_PHOTOURL = "com.alia.sisdiary.ui.activity.PHOTOURL";

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private TextView mUserName;
    private CircleImageView mUserPhoto;

    private FragmentManager fragmentManager;
    private Fragment fragment = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(R.string.menu_title_timetable);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);
        mUserPhoto = (CircleImageView) headerView.findViewById(R.id.user_photo);


        //String username = (String) getIntent().getExtras().get("userName");
        //Uri photouri = (Uri) getIntent().getExtras().get("userPhoto");
        //Log.i(TAG, "alyo " + username + " " + String.valueOf(photouri));

        SharedPreferences spUserData = getSharedPreferences(
                getString(R.string.pref_user_data), MODE_PRIVATE);
        String username = spUserData.getString(getString(R.string.saved_user_name), null);
        String photoUrl = spUserData.getString(getString(R.string.saved_user_photourl), null);
        //  Log.i(TAG, username);
        mUserName.setText(username);

        Picasso.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.ic_menu_timetable)
                .into(mUserPhoto);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_timetable) {
                    fragment = new TimetableFragment();
                } else if (id == R.id.nav_notes) {
                    fragment = new NotesFragment();
                } else if (id == R.id.nav_msgs) {

                } else if (id == R.id.nav_photos) {

                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.commit();

                if (mDrawer != null) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new TimetableFragment();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_exit:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
