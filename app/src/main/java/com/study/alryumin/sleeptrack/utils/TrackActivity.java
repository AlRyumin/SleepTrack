package com.study.alryumin.sleeptrack.utils;

import android.content.Context;
import android.content.Intent;

public class TrackActivity {
    private static TrackActivity instance;
    private static final String HANDLER_THREAD = "trackHandlerThread";

    private TrackActivity(){}

    public static TrackActivity getInstance(){
        if(instance == null){
            instance = new TrackActivity();
        }
        return instance;
    }

    public void track(Context context){
        context.startService(new Intent(context, TrackService.class));
    }
}
