package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.view.sleep_time.view.adapter.SleepTimePagerAdapter;


public class SleepTimeView extends Fragment {
    private ViewPager viewPager;
    SleepTimePagerAdapter pagerAdapter;
    final String TAG = "SleepTimeView";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.sleep_pager, container, false);

        initView(rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    private void initView(View rootView) {
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        pagerAdapter = new SleepTimePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
}
