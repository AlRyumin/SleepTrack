package com.study.alryumin.sleeptrack.view.settings.contract;

import com.study.alryumin.sleeptrack.model.AppSettings;

public interface SettingsContract {
    interface View{

    }

    interface Presenter{
        public void setMinSleepTime(int hour, int minute);
        public String getMinSleepTime();
        public AppSettings getAppSettings();
        public void setAppSettings(AppSettings settings);
        public Long getMinSleepTime(boolean isMilliseconds);
    }
}
