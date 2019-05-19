package com.study.alryumin.sleeptrack.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;

public class TrackService extends Service {
    private final String TAG = "TrackService";
    private static final String HANDLER_THREAD = "trackHeandlerThread";

    public TrackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent,
                              final int flags,
                              final int startId) {

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);

//        context.registerReceiver(new ScreenTrackReceiver(), mIntentFilter);

        HandlerThread handlerThread = new HandlerThread(HANDLER_THREAD);
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        registerReceiver(new ScreenTrackReceiver(), mIntentFilter, null, handler);

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        selfRestart();

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        selfRestart();

        super.onDestroy();
    }

    private void selfRestart(){
        Intent restartServiceTask = new Intent(getApplicationContext(), this.getClass());
        restartServiceTask.setPackage(getPackageName());
        PendingIntent restartPendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceTask, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager myAlarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        myAlarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartPendingIntent);
    }
}
