package com.study.alryumin.sleeptrack.view.sleep_time.view.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeChartView;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeListView;

    public class SleepTimePagerAdapter extends FragmentPagerAdapter {
    private int NUM_ITEMS = 2;
    private String[] titles;
    String TAG = "SleepTimePagerAdapter";

    public SleepTimePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        titles= new String[]{
                context.getString(R.string.sleep_time_tab_1),
                context.getString(R.string.sleep_time_tab_2)
        };
    }

    @Override
    public int getCount() { return  NUM_ITEMS ; }

    @Override
    public Fragment getItem(int position) {
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
