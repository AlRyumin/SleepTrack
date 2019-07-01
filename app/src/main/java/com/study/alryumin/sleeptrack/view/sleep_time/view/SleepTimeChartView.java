package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.utils.chart.LineChartMarkerView;
import com.study.alryumin.sleeptrack.view.sleep_time.presenter.SleepTimePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SleepTimeChartView extends Fragment {
    private SleepTimePresenter sleepTimePresenter;
    private final RectF mOnValueSelectedRectF = new RectF();

    @BindView(R.id.chart_content)
    LineChart chart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sleepTimePresenter = new SleepTimePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sleep_time_chart_view, container, false);

        ButterKnife.bind(this, rootView);

        chartData(getContext());

        return rootView;
    }

    private void chartData(Context context) {
        List<SleepTime> items = sleepTimePresenter.getItems();
        Collections.reverse(items);
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (SleepTime sleepTime : items) {
            String date = DateFormatHelper.getDateDayMonthFormat(sleepTime.getFinishAt());
            String time = DateFormatHelper.getTimeDoubleFormat(sleepTime.getSleepTime());

            labels.add(date);
            entries.add(new Entry(items.indexOf(sleepTime), Float.parseFloat(time)));
        }

        LineDataSet dataSet = new LineDataSet(entries, ""); // add entries to dataset

        dataSet.setDrawIcons(false);

        // draw dashed line
//        dataSet.enableDashedLine(10f, 5f, 0f);

        dataSet.setColor(R.color.colorPrimaryDark);
        dataSet.setCircleColor(R.color.colorPrimaryDark);
        dataSet.setValueTextSize(14f);

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);

        chart.getDescription().setEnabled(false);

        chart.setTouchEnabled(true);

        chart.getLegend().setEnabled(false);

        LineChartMarkerView mv = new LineChartMarkerView(getContext(), R.layout.line_chart_marker_view, items);

        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new LabelValueFormatter(labels));

        YAxis leftAxis = chart.getAxisLeft();

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.invalidate();
    }
}

class LabelValueFormatter extends ValueFormatter {
    private List<String> labels;

    public LabelValueFormatter(List<String> labels){
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value) {
        return labels.get((int) value);
    }
}

