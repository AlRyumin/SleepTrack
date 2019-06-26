package com.study.alryumin.sleeptrack.view.settings.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.study.alryumin.sleeptrack.helper.SettingsHelper;
import com.study.alryumin.sleeptrack.model.AppSettings;
import com.study.alryumin.sleeptrack.view.settings.contract.SettingsContract;

public class SettingsPresenter implements SettingsContract.Presenter {
    private static SettingsPresenter instance;
    private static AppSettings appSettings;
    private static final Long defaultMinSleepTime = 14400l * 1000;

    private SettingsPresenter(){
        appSettings = new AppSettings();
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

    public void setAppSettings(AppSettings appSettings){
        if(null == appSettings || null == appSettings.getMinSleepTime()){
            this.appSettings.setMinSleepTime(defaultMinSleepTime);
        } else {
            this.appSettings.setMinSleepTime(appSettings.getMinSleepTime());
        }
    }



    @Override
    public void setMinSleepTime(int hour, int minute) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("settings");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        long minSleepTime = SettingsHelper.timeToMilliseconds(hour, minute);

        appSettings = new AppSettings(minSleepTime);

        reference.child(user.getUid()).setValue(appSettings);
    }

}
