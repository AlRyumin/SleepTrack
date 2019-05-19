package com.study.alryumin.sleeptrack.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.view.App;

import java.util.Date;

public class ScreenTrackReceiver extends BroadcastReceiver {
    private String TAG = "ScreenReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            track(false);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            track(true);
        }
    }

    private void track(boolean screen_on){
        boolean isActive = screen_on;

        DatabaseHelper database = App.getInstance().getDatabase();
        ActivityTrackDao activityTrackDao = database.getActivityTrackDao();
        ActivityTrack activityTrack = activityTrackDao.getLast();

        if(!isActive && activityTrack == null){
            return;
        } else if(!isActive && activityTrack.getFinishAt() != null){
            return;
        } else if(!isActive && activityTrack.getFinishAt() == null){
            activityTrackUpdateFinishAt(database, activityTrackDao, activityTrack);
        } else if(isActive && activityTrack == null){
            createNewActivity(database, activityTrackDao);
        } else if(isActive && activityTrack.getFinishAt() == null){
            return;
        } else {
            createNewActivity(database, activityTrackDao);
        }

    }

    private void createNewActivity(DatabaseHelper database, ActivityTrackDao activityTrackDao) {
        ActivityTrack activityTrack = new ActivityTrack("activityTrack test", new Date());
        activityTrackDao.add(activityTrack);
    }

    private void activityTrackUpdateFinishAt(DatabaseHelper database, ActivityTrackDao activityTrackDao, ActivityTrack activityTrack) {
        activityTrack.setFinishAt(new Date());
        activityTrackDao.update(activityTrack);
    }
}
