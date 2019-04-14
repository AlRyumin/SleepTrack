package com.study.alryumin.sleeptrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.alryumin.sleeptrack.presenter.SplashPresenter;

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
