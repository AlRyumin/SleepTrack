package com.study.alryumin.sleeptrack.view.sleep_time.contract;

import com.study.alryumin.sleeptrack.model.SleepTime;

import java.util.List;

public interface SleepTimeContract {
    interface View{}

    interface Presenter{
        List<SleepTime> getItems();
    }
}
