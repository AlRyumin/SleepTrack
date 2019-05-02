package com.study.alryumin.sleeptrack.worker;

import android.app.KeyguardManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

import com.study.alryumin.sleeptrack.App;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

public class ActivityTrackWorker extends Worker {
    static final String TAG = "ActivityTrackWorker";

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.d(TAG, "ActivityTrackWorker: start");

        try {
            trackActivity();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "ActivityTrackWorker: end");

        return WorkerResult.SUCCESS;
    }

    private synchronized void trackActivity() throws Settings.SettingNotFoundException, InterruptedException {
        while (true) {
            track();
            sleep();
        }
    }

    private void sleep() throws Settings.SettingNotFoundException, InterruptedException {
//        float time = Settings.System.getFloat(getApplicationContext().getContentResolver(), SCREEN_OFF_TIMEOUT);
//        long lTime = (long) time;
        long lTime = 10000;

        Log.d("testTime:", Long.toString(lTime));

        TimeUnit.MILLISECONDS.sleep(lTime);
    }

    private boolean isActive() {
        try {
            KeyguardManager myKM = (KeyguardManager) getApplicationContext().getSystemService(getApplicationContext().KEYGUARD_SERVICE);
            if (myKM.inKeyguardRestrictedInputMode()) {
                return false;
            }

            return true;
        } catch (Exception e) {
            Log.d("TrackException", e.getMessage());
            return false;
        }
    }

    private void track() {
        boolean isActive = isActive();

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
