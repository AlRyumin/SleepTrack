package com.study.alryumin.sleeptrack.view.sleep_time.presenter;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.sleep_time.contract.EditSleepTimeContract;

import java.util.Calendar;
import java.util.Date;

public class EditSleepTimePresenter implements EditSleepTimeContract.Presenter {
    private static EditSleepTimePresenter instance;
    private SleepTime sleepTime;

    private SleepTime sleepTimeMemento;
    private SleepTimeDao sleepTimeDao;
    private DatabaseHelper database;

    private EditSleepTimePresenter(){
        database = App.getInstance().getDatabase();
        sleepTimeDao = database.getSleepTimeDao();
        sleepTimeMemento = new SleepTime();
    }

    public static EditSleepTimePresenter getInstance(){
        if(null == instance){
            instance = new EditSleepTimePresenter();
        }

        return instance;
    }

    public SleepTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTimeId){
        sleepTime = sleepTimeDao.getById(sleepTimeId);
        sleepTime.copy(sleepTimeMemento);
    }

    public void setStartAt(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sleepTime.getStartAt());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        sleepTime.setStartAt(new Date(calendar.getTimeInMillis()));
        checkDate();
        countSleepTime();
    }

    public void setFinishAt(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sleepTime.getFinishAt());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        sleepTime.setFinishAt(new Date(calendar.getTimeInMillis()));
        checkDate();
        countSleepTime();
    }

    public void setSleepTime(int hour, int minute){
        long time = DateFormatHelper.timeToMilliseconds(hour, minute);

        sleepTime.setSleepTime(time);
        sleepTime.setFinishAt(new Date(sleepTime.getStartAt().getTime() + time));
    }

    public void resetSleepTime(){
        sleepTimeMemento.copy(sleepTime);
    }

    public void saveSleepTime(){
        sleepTimeDao.update(sleepTime);
    }

    public void countSleepTime(){
        Long time = sleepTime.getFinishAt().getTime() - sleepTime.getStartAt().getTime();

//        if(time < 0l){
//            Long dayNight = 24l * 60 * 60 * 1000;
//            time = dayNight - (time * -1l);
//        }

        sleepTime.setSleepTime(time);
    }

    private void checkDate(){
        if(sleepTime.getStartAt().compareTo(sleepTime.getFinishAt()) == 1){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sleepTime.getFinishAt());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            sleepTime.setFinishAt(new Date(calendar.getTimeInMillis()));
        }
    }

}
