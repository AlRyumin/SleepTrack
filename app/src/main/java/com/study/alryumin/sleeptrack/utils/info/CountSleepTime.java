package com.study.alryumin.sleeptrack.utils.info;

import androidx.annotation.Nullable;

import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.model.AppSettings;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.repository.room.ActivityTrackDao;
import com.study.alryumin.sleeptrack.repository.room.AppSettingsDao;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.view.App;

import java.util.Calendar;
import java.util.List;

public class CountSleepTime {
    private static CountSleepTime instance;
    private DatabaseHelper database;
    private ActivityTrackDao activityTrackDao;
    private SleepTimeDao sleepTimeDao;
    private AppSettingsDao appSettingsDao;
    private Long timeDiff;

    private CountSleepTime() {
        database = App.getInstance().getDatabase();
        sleepTimeDao = database.getSleepTimeDao();
        activityTrackDao = database.getActivityTrackDao();
        appSettingsDao = database.getAppSettingsDao();

        AppSettings settings = appSettingsDao.getLast();
        timeDiff = settings.getMinSleepTime();
    }

    public static CountSleepTime getInstance() {
        if (null == instance) {
            instance = new CountSleepTime();
        }

        return instance;
    }

    public void count() {
        SleepTime last = sleepTimeDao.getLast();

        if (null == last) {
            List<ActivityTrack> tracks = activityTrackDao.getAll();
            insertData(tracks);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(last.getFinishAt());
            Long lastTime = calendar.getTimeInMillis();

            List<ActivityTrack> tracks = activityTrackDao.getByDate(lastTime);
            insertData(tracks);
        }
    }

    private void insertData(@Nullable List<ActivityTrack> tracks) {
        if (tracks == null) {
            return;
        }

        for (ActivityTrack track : tracks) {
            int index = tracks.indexOf(track);
            if (index <= 0) {
                continue;
            }

            ActivityTrack previousTrack = tracks.get(index - 1);

            Long trackTime = track.getStartAt().getTime();
            Long trackPrevTime = previousTrack.getFinishAt().getTime();

            if ((trackTime - trackPrevTime) > timeDiff) {
                SleepTime sleepTime = new SleepTime();
                sleepTime.setStartAt(previousTrack.getFinishAt());
                sleepTime.setFinishAt(track.getStartAt());
                sleepTime.setSleepTime(trackTime - trackPrevTime);

                sleepTimeDao.add(sleepTime);
            }
        }

    }

    private void calendarMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void calendarPreMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}
