package com.study.alryumin.sleeptrack.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.utils.TrackActivity;
import com.study.alryumin.sleeptrack.view.activity_track.view.ActivityTrackView;
import com.study.alryumin.sleeptrack.view.authorization.AuthorizationActivity;
import com.study.alryumin.sleeptrack.view.settings.view.SettingsView;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeView;
import com.study.alryumin.sleeptrack.view.target.view.TargetView;
import com.study.alryumin.sleeptrack.worker.ActivityTrackWorker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ACTIVITY_TRACK_FRAGMENT = 1, SLEEP_TIME_FRAGMENT = 2, SETTINGS_FRAGMENT = 3;
    private static final String ACTIVITY_TRACK_WORKER_TAG = "activityTrackWorker";
    private int activeFragment;

    @Override
    protected void onStart() {
        super.onStart();

        try {
            TrackActivity.getInstance().track(getBaseContext());
            startActivityTrackWorker();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        if (savedInstanceState != null) {
            setBundledFields(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContent();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("activeFragment", activeFragment);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setContent(new SettingsView());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_activity) {
            setContent(new ActivityTrackView());
        } if (id == R.id.nav_sleep_time) {
            setContent(new SleepTimeView());
        } else if (id == R.id.nav_target) {
            setContent(new TargetView());
        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), AuthorizationActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setContent() {
        int content = R.id.main_content;

        Fragment fragment = new SleepTimeView();

        if (activeFragment == ACTIVITY_TRACK_FRAGMENT) {
            fragment = new ActivityTrackView();
        } else if (activeFragment == SLEEP_TIME_FRAGMENT){
            fragment = new SleepTimeView();
        } else if (activeFragment == SETTINGS_FRAGMENT){
            fragment = new SettingsView();
        }

        replaceFragment(fragment, content);
    }

    public void setContent(Fragment fragment) {
        int content = R.id.main_content;
        replaceFragment(fragment, content);
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        saveActiveFragment(fragment);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void saveActiveFragment(Fragment fragment) {
        if(fragment instanceof ActivityTrackView){
            activeFragment = ACTIVITY_TRACK_FRAGMENT;
        } else if(fragment instanceof SleepTimeView){
            activeFragment = SLEEP_TIME_FRAGMENT;
        }else if(fragment instanceof SettingsView){
            activeFragment = SETTINGS_FRAGMENT;
        }
    }

    protected void setBundledFields(Bundle savedInstanceState) {
        if (savedInstanceState.getSerializable("activeFragment") != null) {
            activeFragment = (int) savedInstanceState.getSerializable("activeFragment");
        }
    }

    protected void startActivityTrackWorker() throws Exception {
        WorkManager.getInstance().cancelAllWorkByTag(ACTIVITY_TRACK_WORKER_TAG);

        if(null == WorkManager.getInstance().getStatusesByTag(ACTIVITY_TRACK_WORKER_TAG).getValue()) {
            PeriodicWorkRequest activityTrackWorkerRequest =
                    new PeriodicWorkRequest.Builder(ActivityTrackWorker.class, 15, TimeUnit.MINUTES)
                            .addTag(ACTIVITY_TRACK_WORKER_TAG)
                            .build();
            WorkManager.getInstance().enqueue(activityTrackWorkerRequest);
        }
    }

    private void initView(){
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
