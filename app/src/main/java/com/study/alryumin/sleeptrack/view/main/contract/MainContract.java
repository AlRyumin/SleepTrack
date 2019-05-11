package com.study.alryumin.sleeptrack.view.main.contract;

import com.study.alryumin.sleeptrack.model.ActivityTrack;

import java.util.List;

public interface MainContract {
    interface View{

    }

    interface Presenter{

    }

    interface ActivityTrackView{}

    interface ActivityTrackPresenter{
        public List<ActivityTrack> getItems();
    }
}
