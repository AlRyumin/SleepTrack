package com.study.alryumin.sleeptrack.view.main.presenter;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.main.contract.ActivityTrackContract;

import java.util.List;

public class ActivityTrackPresenter implements ActivityTrackContract.Presenter {

    @Override
    public List<ActivityTrack> getItems() {
        DatabaseHelper database = App.getInstance().getDatabase();
        ActivityTrackDao activityTrackDao = database.getActivityTrackDao();
        return activityTrackDao.getAll();
    }
}
