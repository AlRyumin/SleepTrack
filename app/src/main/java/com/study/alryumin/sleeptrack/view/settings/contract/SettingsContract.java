package com.study.alryumin.sleeptrack.view.settings.contract;

import com.study.alryumin.sleeptrack.model.AppSettings;

public interface SettingsContract {
    interface View{

    }

    interface Presenter{
        void setMinSleepTime(int hour, int minute);
        String getMinSleepTime();
        AppSettings getAppSettings();
        Long getMinSleepTime(boolean isMilliseconds);
    }
}
