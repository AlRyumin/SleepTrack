package com.study.alryumin.sleeptrack.view.sleep_time.presenter;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.utils.info.CountSleepTime;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.sleep_time.contract.SleepTimeContract;

import java.util.List;

public class SleepTimePresenter implements SleepTimeContract.Presenter {
    @Override
    public List<SleepTime> getItems() {
        DatabaseHelper database = App.getInstance().getDatabase();
        SleepTimeDao sleepTimeDao = database.getSleepTimeDao();

        CountSleepTime.getInstance().count();

//        Long now = System.currentTimeMillis();
//        Long yesterday = System.currentTimeMillis();
//
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
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


        return sleepTimeDao.getAll();
    }
}
