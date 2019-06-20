package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.helper.ChartHelper;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.view.sleep_time.presenter.SleepTimePresenter;

import java.util.List;

public class SleepTimeChartView extends Fragment {
    private SleepTimePresenter sleepTimePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sleepTimePresenter = new SleepTimePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sleep_time_chart_view, container, false);

        WebView chart = rootView.findViewById(R.id.chart_content);

        chartData(chart, getContext());

        return rootView;
    }

    private void chartData(WebView webView, Context context) {
        List<SleepTime> items = sleepTimePresenter.getItems();
//        Collections.reverse(items);

        String elements = "";

//        for(SleepTime sleepTime : items){
//            String date = DateFormatHelper.getDateDayMonthFormat(sleepTime.getFinishAt());
//            String time = DateFormatHelper.getTimeDoubleFormat(sleepTime.getSleepTime());
//
//            elements += "['" + date + "', " + time + "],";
//        }

        String chart = ChartHelper.getSleepTimeCoreChart(items);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(chart, "text/html", "base64");
//        webView.loadUrl("file:///android_asset/chart.html");

    }
}
