package com.study.alryumin.sleeptrack.view.sleep_time.view.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.study.alryumin.sleeptrack.view.activity_track.view.ActivityTrackView;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeChartView;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeListView;

    public class SleepTimePagerAdapter extends FragmentPagerAdapter {
    private int NUM_ITEMS = 2;
    private String[] titles= new String[]{"Tab 1", "Tab 2"};
    String TAG = "SleepTimePagerAdapter";

    public SleepTimePagerAdapter(FragmentManager fm) {
        super(fm);
        Log.d(TAG, "Constructor");
    }

    @Override
    public int getCount() { return  NUM_ITEMS ; }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem");
        if(position == 1){
            return new SleepTimeChartView();
        }

        return new SleepTimeListView();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  titles[position];
    }

}
