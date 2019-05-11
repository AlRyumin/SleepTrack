package com.study.alryumin.sleeptrack.view.splash.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.alryumin.sleeptrack.AuthorizationActivity;
import com.study.alryumin.sleeptrack.MainActivity;
import com.study.alryumin.sleeptrack.view.splash.contract.SplashContract;

public class SplashPresenter implements SplashContract.Presenter {

    @Override
    public Class<?> getActivityClass() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Class<?> cls = null;

        if(user != null){
            cls = MainActivity.class;
        } else {
            cls = AuthorizationActivity.class;
        }

        return cls;
    }
}
