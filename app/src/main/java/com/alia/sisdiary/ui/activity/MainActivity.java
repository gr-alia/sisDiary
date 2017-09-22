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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private TextView mUserName;
    private CircleImageView mUserPhoto;

    private FragmentManager mFragmentManager;
    private Fragment mFragment = null;
    private SharedPreferences mSpUserData;
    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences.OnSharedPreferenceChangeListener mOnSpChangedListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupViews();
        mSpUserData = getSharedPreferences(
                getString(R.string.pref_user_data), MODE_PRIVATE);
        boolean isContainsSP = mSpUserData.contains(getString(R.string.saved_user_id));
        if (!isContainsSP) {
            login();
        }
        else {
            showUserData();
        }
        mOnSpChangedListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                showUserData();
            }
        };
        mSpUserData.registerOnSharedPreferenceChangeListener(mOnSpChangedListener);
        setSupportActionBar(mToolbar);
        setTitle(R.string.menu_title_timetable);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_timetable) {
                    mFragment = new TimetableFragment();
                } else if (id == R.id.nav_notes) {
                    mFragment = new NotesFragment();
                } else if (id == R.id.nav_msgs) {

                } else if (id == R.id.nav_photos) {

                }
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.content_frame, mFragment);
                transaction.commit();

                if (mDrawer != null) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        mFragment = new TimetableFragment();
        fragmentTransaction.replace(R.id.content_frame, mFragment);
        fragmentTransaction.commit();

        googlePrepare();
    }

    private void login() {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }

    private void showUserData() {
        String username = mSpUserData.getString(getString(R.string.saved_user_name), null);
        String photoUrl = mSpUserData.getString(getString(R.string.saved_user_photourl), null);
        mUserName.setText(username);
        Picasso.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.ic_menu_timetable)
                .into(mUserPhoto);
    }

    private void setupViews() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);
        mUserPhoto = (CircleImageView) headerView.findViewById(R.id.user_photo);
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
                signOut();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void googlePrepare() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getBaseContext(), "Some error happens", Toast.LENGTH_SHORT);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(getBaseContext(), "This is Log Out, Baby!", Toast.LENGTH_LONG).show();
                        mSpUserData.edit().clear().commit();
                        login();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
