package com.study.alryumin.sleeptrack.view.main.contract;

import java.text.ParseException;
import java.util.List;

public interface ActivityTrackContract {
    interface View{}

    interface Presenter{
        List<com.study.alryumin.sleeptrack.model.ActivityTrack> getItems();
    }
}
