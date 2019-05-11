package com.study.alryumin.sleeptrack.view.splash.contract;

public interface SplashContract {
    interface View{
        public boolean startActivity();
    }

    interface Presenter{
        public Class<?> getActivityClass();
    }
}
