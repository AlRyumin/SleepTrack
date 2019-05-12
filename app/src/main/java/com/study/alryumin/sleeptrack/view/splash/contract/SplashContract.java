package com.study.alryumin.sleeptrack.view.splash.contract;

public interface SplashContract {
    interface View{
        boolean startActivity();
    }

    interface Presenter{
        Class<?> getActivityClass();
    }
}
