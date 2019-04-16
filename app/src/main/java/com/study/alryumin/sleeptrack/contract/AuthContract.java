package com.study.alryumin.sleeptrack.contract;

import android.content.Context;

import java.util.ArrayList;

public interface AuthContract {
    public interface signInPresenter {
        public void userLogin();
        public ArrayList<String> getErrors(String email, String password, Context context);
        public void showErrors(ArrayList<String> errors, Context context);
    }
    public interface signInView {

    }
    public interface signUpPresenter {

    }
}
