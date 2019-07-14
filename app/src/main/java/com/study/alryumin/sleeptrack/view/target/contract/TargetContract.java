package com.study.alryumin.sleeptrack.view.target.contract;

import java.sql.Time;
import java.util.Date;

public interface TargetContract {
    interface View {

    }

    interface Presenter {
        Time getStartAt();

        Time getFinishAt();

        Long getSleepTime();

        void setStartAt(int hour, int minute);

        void setFinishAt(int hour, int minute);

        void setSleepTime(int hour, int minute);

        void resetTarget();

        void saveTarget();
    }
}
