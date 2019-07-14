package com.study.alryumin.sleeptrack.view.sleep_time.contract;

import com.study.alryumin.sleeptrack.model.SleepTime;

import java.util.Date;

public interface EditSleepTimeContract {
    interface View {

    }

    interface Presenter {
        void setStartAt(int hour, int minute);

        void setFinishAt(int hour, int minute);

        void setSleepTime(long sleepTimeId);

        void setSleepTime(int hour, int minute);

        void resetSleepTime();

        void saveSleepTime();

        SleepTime getSleepTime();
    }
}
