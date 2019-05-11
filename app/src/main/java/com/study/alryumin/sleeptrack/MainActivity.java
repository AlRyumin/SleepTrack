package com.study.alryumin.sleeptrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.study.alryumin.sleeptrack.view.main.view.ActivityTrackFragment;
import com.study.alryumin.sleeptrack.worker.ActivityTrackWorker;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ACTIVITY_TRACK_FRAGMENT = 1;
    private static final String ACTIVITY_TRACK_WORKER_TAG = "activityTrackWorker";
    private int activeFragment;

    @Override
    protected void onStart() {
        super.onStart();

        startActivityTrackWorker();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_activity) {
            // Handle the camera action
        } else if (id == R.id.nav_sign_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), AuthorizationActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setContent() {
        int content = R.id.main_content;

        Fragment fragment = new ActivityTrackFragment();

        switch (activeFragment) {
            case ACTIVITY_TRACK_FRAGMENT:
                fragment = new ActivityTrackFragment();
                break;
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
        if(fragment instanceof ActivityTrackFragment){
            activeFragment = ACTIVITY_TRACK_FRAGMENT;
        }
    }

    protected void setBundledFields(Bundle savedInstanceState) {
        if (savedInstanceState.getSerializable("activeFragment") != null) {
            activeFragment = (int) savedInstanceState.getSerializable("activeFragment");
        }
    }

    protected void startActivityTrackWorker(){
        if(null == WorkManager.getInstance().getStatusesByTag(ACTIVITY_TRACK_WORKER_TAG).getValue()) {
            OneTimeWorkRequest activityTrackWorkerRequest =
                    new OneTimeWorkRequest.Builder(ActivityTrackWorker.class)
                            .addTag(ACTIVITY_TRACK_WORKER_TAG)
                            .build();
            WorkManager.getInstance().enqueue(activityTrackWorkerRequest);
        }
    }
}
