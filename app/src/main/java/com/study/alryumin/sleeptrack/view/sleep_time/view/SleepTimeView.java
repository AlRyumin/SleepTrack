package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.utils.LockableViewPager;
import com.study.alryumin.sleeptrack.view.sleep_time.view.adapter.SleepTimePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SleepTimeView extends Fragment {
    private LockableViewPager viewPager;

    @BindView(R.id.pagerHeader)
    PagerTabStrip pagerHeader;

    SleepTimePagerAdapter pagerAdapter;
    final String TAG = "SleepTimeView";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.sleep_pager, container, false);

        ButterKnife.bind(this, rootView);

        initView(rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(View rootView) {
        viewPager = (LockableViewPager) rootView.findViewById(R.id.pager);
        viewPager.setSwipeable(false);

        pagerAdapter = new SleepTimePagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(pagerAdapter);
    }
}
