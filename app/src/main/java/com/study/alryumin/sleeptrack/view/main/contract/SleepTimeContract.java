package com.study.alryumin.sleeptrack.view.main.contract;

import com.study.alryumin.sleeptrack.model.SleepTime;

import java.util.List;

public interface SleepTimeContract {
    interface View{}

    interface Presenter{
        List<SleepTime> getItems();
    }
}
