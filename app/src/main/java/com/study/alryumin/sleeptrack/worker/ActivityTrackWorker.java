package com.study.alryumin.sleeptrack.worker;

import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.utils.TrackActivity;
import com.study.alryumin.sleeptrack.view.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityTrackWorker extends Worker {
    private static final String TAG = "ActivityTrackWorker";

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.d(TAG, "ActivityTrackWorker: start");

        try {
//            trackActivity();
//            runComamnd();
            trackService();
        }catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "ActivityTrackWorker: end");

        return WorkerResult.SUCCESS;
    }

    public void trackService(){
        TrackActivity.getInstance().track(getApplicationContext());
    }

    private void runComamnd(){
        try {
            Log.d(TAG, "StartLogCat");
            // Executes the command.
            Process process = Runtime.getRuntime().exec("logcat -b events -d");

            // Reads stdout.
            // NOTE: You can write to stdin of the command using
            //       process.getOutputStream().
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            // Waits for the command to finish.
            process.waitFor();

            Log.d("ShowLog", output.toString());
            Log.d(TAG, "EndLogCat");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void trackActivity() throws Settings.SettingNotFoundException, InterruptedException {
        while (true) {
            track();
            sleep();
        }
    }

    private void sleep() throws InterruptedException {
//        float time = Settings.System.getFloat(getApplicationContext().getContentResolver(), SCREEN_OFF_TIMEOUT);
//        long lTime = (long) time;
        long lTime = 10000;

        Log.d("testTime:", Long.toString(lTime));

        TimeUnit.MILLISECONDS.sleep(lTime);
    }

    private boolean isActive() {
        try {
            PowerManager pm = (PowerManager)getApplicationContext().getSystemService(getApplicationContext().POWER_SERVICE);
            return pm.isScreenOn();

//            KeyguardManager myKM = (KeyguardManager) getApplicationContext().getSystemService(getApplicationContext().KEYGUARD_SERVICE);
//            if (myKM.inKeyguardRestrictedInputMode()) {
//                return false;
//            }
//            return true;
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

        Log.d("isActive: ", Boolean.toString(isActive));

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
