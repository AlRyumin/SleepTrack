package com.study.alryumin.sleeptrack.utils.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class LineChartMarkerView extends MarkerView {

    private TextView fallAsleep, wakeUp, sleepTimeView;
    private List<SleepTime> items;

    public LineChartMarkerView(Context context, int layoutResource, @Nullable List<SleepTime> items) {
        super(context, layoutResource);
        this.items = items;

        initView();
    }

    public LineChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

       initView();
    }

    private void initView(){
        fallAsleep = findViewById(R.id.fallAsleep);
        wakeUp = findViewById(R.id.wakeUp);
        sleepTimeView = findViewById(R.id.sleepTime);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float value;

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;
            value = ce.getX();
        } else {
            value = e.getX();
        }

        if(null != items) {
            SleepTime sleepTime = items.get((int) value);

            fallAsleep.setText(DateFormatHelper.getTimeDateFormat(sleepTime.getStartAt()));
            wakeUp.setText(DateFormatHelper.getTimeDateFormat(sleepTime.getFinishAt()));
            sleepTimeView.setText(DateFormatHelper.getTimeFormat(sleepTime.getSleepTime()));

            super.refreshContent(e, highlight);
        }

    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}