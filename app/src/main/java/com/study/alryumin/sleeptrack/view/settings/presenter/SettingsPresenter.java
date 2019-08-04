package com.study.alryumin.sleeptrack.view.settings.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.helper.SettingsHelper;
import com.study.alryumin.sleeptrack.model.AppSettings;
import com.study.alryumin.sleeptrack.repository.room.AppSettingsDao;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.settings.contract.SettingsContract;

public class SettingsPresenter implements SettingsContract.Presenter {
    private static SettingsPresenter instance;
    private AppSettings appSettings;
    private final Long defaultMinSleepTime = 14400l * 1000;

    DatabaseHelper database;
    AppSettingsDao appSettingsDao;

    private SettingsPresenter(){
        database = App.getInstance().getDatabase();
        appSettingsDao = database.getAppSettingsDao();
        appSettings = appSettingsDao.getLast();
    }

    public static SettingsPresenter getInstance(){
        if(null == instance){
            instance = new SettingsPresenter();
        }

        return instance;
    }

    @Override
    public String getMinSleepTime(){
        long milliseconds = getMinSleepTime(true);
        return SettingsHelper.hourFromMilliseconds(milliseconds) + ":" + SettingsHelper.minuteFromMilliseconds(milliseconds);
    }

    @Override
    public Long getMinSleepTime(boolean isMilliseconds){
        if(null == appSettings.getMinSleepTime()){
            return defaultMinSleepTime;
        }

        return appSettings.getMinSleepTime();
    }

    public AppSettings getAppSettings(){
        return appSettings;
    }

    @Override
    public void setMinSleepTime(int hour, int minute) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("settings");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        long minSleepTime = SettingsHelper.timeToMilliseconds(hour, minute);

        appSettings.setMinSleepTime(minSleepTime);

        appSettingsDao.update(appSettings);

        reference.child(user.getUid()).setValue(appSettings);
    }

}
