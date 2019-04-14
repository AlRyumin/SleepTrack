package com.study.alryumin.sleeptrack.contract;

public interface SplashContract {
    interface View{
        public boolean startActivity();
    }

    interface Presenter{
        public Class<?> getActivityClass();
    }
}
