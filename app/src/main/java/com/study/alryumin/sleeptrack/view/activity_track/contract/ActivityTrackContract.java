package com.study.alryumin.sleeptrack.view.activity_track.contract;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public interface ActivityTrackContract {
    interface View{}

    interface Presenter{
        List<com.study.alryumin.sleeptrack.model.ActivityTrack> getItems();
        List<com.study.alryumin.sleeptrack.model.ActivityTrack> getItems(Calendar date);
    }
}
