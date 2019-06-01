package com.study.alryumin.sleeptrack.view.main.presenter;

import android.util.Log;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.main.contract.ActivityTrackContract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ActivityTrackPresenter implements ActivityTrackContract.Presenter {

    private String TAG = "ActivityTrackPresenter";

    @Override
    public List<ActivityTrack> getItems() {
        DatabaseHelper database = App.getInstance().getDatabase();
        ActivityTrackDao activityTrackDao = database.getActivityTrackDao();

//        Long now = System.currentTimeMillis();
//        Long yesterday = System.currentTimeMillis();
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();

        return getItems(date);

//        cal.add(Calendar.DATE, -1);
//        String sYesterday = dateFormat.format(cal.getTime()).toString();
//
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date dYesterday = null;
//        try {
//            dYesterday = (Date)formatter.parse(sYesterday);
//            yesterday = dYesterday.getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        if(dYesterday != null){
//            return activityTrackDao.getByDate(yesterday, now);
//        } else {
//            return activityTrackDao.getAll();
//        }

    }

    @Override
    public List<ActivityTrack> getItems(Calendar date) {
        DatabaseHelper database = App.getInstance().getDatabase();
        ActivityTrackDao activityTrackDao = database.getActivityTrackDao();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String sDate = dateFormat.format(date.getTime()).toString();

        Long showFrom = System.currentTimeMillis();
        Long showTo = System.currentTimeMillis();

        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        showFrom = date.getTimeInMillis();

        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 59);
        date.set(Calendar.MILLISECOND, 999);
        showTo = date.getTimeInMillis();


        Log.d(TAG + " From", showFrom.toString());
        Log.d(TAG + " To", showTo.toString());

        return activityTrackDao.getByDate(showFrom, showTo);

    }
}
