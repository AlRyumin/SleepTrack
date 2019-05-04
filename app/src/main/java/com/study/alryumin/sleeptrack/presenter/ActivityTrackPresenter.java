package com.study.alryumin.sleeptrack.presenter;

import com.study.alryumin.sleeptrack.App;
import com.study.alryumin.sleeptrack.contract.MainContract;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;

import java.util.List;

public class ActivityTrackPresenter implements MainContract.ActivityTrackPresenter {

    @Override
    public List<ActivityTrack> getItems() {
        DatabaseHelper database = App.getInstance().getDatabase();
        ActivityTrackDao activityTrackDao = database.getActivityTrackDao();
        return activityTrackDao.getAll();
    }
}
