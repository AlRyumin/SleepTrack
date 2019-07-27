package com.study.alryumin.sleeptrack.view.sleep_time.contract;

import com.study.alryumin.sleeptrack.model.SleepTime;

public class AddSleepTimeContract {
    interface View {

    }

    public interface Presenter {
        void setStartAt(int hour, int minute);

        void setFinishAt(int hour, int minute);

        void setSleepTime(SleepTime sleepTime);

        void setSleepTime(int hour, int minute);

        void saveSleepTime();
    }
}
