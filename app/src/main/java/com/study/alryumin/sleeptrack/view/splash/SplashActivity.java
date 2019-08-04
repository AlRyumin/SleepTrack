package com.study.alryumin.sleeptrack.view.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.AppSettings;
import com.study.alryumin.sleeptrack.repository.room.AppSettingsDao;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.main.MainActivity;
import com.study.alryumin.sleeptrack.view.splash.presenter.SplashPresenter;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    SplashPresenter presenter;
    private DatabaseHelper database;
    private AppSettingsDao appSettingsDao;
    private AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        Class<?> cls = presenter.getActivityClass();

        if(cls.getSimpleName().equals(MainActivity.class.getSimpleName())){
            initSettings(cls);
        } else {
            startNewActivity(cls);
        }


    }

    protected void initView(){
        presenter = new SplashPresenter();
        database = App.getInstance().getDatabase();
        appSettingsDao = database.getAppSettingsDao();
        AppSettings appSettings = appSettingsDao.getLast();
    }

    private void initSettings(Class<?> cls){
        if(isOnline()){
            setFirebaseSettings(cls);
        } else {
            checkSettings();
            startNewActivity(cls);
        }

    }

    private void checkSettings(){
        if(null == appSettings){
            Long minSleepTime = 14400l * 1000;//4 hours in milliseconds;
            appSettings = new AppSettings();
            appSettings.setMinSleepTime(minSleepTime);
            appSettingsDao.add(appSettings);
        }
    }

    private void setFirebaseSettings(Class<?> cls){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("settings");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppSettings settings = dataSnapshot.getValue(AppSettings.class);
                saveSettings(settings);
                startNewActivity(cls);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startNewActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    private void saveSettings(AppSettings settings){
        if(null == appSettings){
            appSettingsDao.add(settings);
        } else {
            appSettings.setMinSleepTime(settings.getMinSleepTime());
            appSettingsDao.update(appSettings);
        }
    }

    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}
