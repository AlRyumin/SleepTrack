package com.study.alryumin.sleeptrack;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.study.alryumin.sleeptrack.view.splash.presenter.SplashPresenter;

public class SplashActivity extends AppCompatActivity {

    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        Class<?> cls = presenter.getActivityClass();

        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    protected void initView(){
        presenter = new SplashPresenter();
    }
}
