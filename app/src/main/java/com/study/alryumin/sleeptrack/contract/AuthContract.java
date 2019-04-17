package com.study.alryumin.sleeptrack.contract;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;

public interface AuthContract {

    public interface signInPresenter {
        public void userLogin();
        public ArrayList<String> getErrors(Context context);
        public void showErrors(ArrayList<String> errors, Context context);
    }

    public interface signInView {
        public EditText getEmail();
        public EditText getPassword();
    }

    public interface signUpPresenter {
        public void userRegister();
        public ArrayList<String> getErrors(Context context);
        public void showErrors(ArrayList<String> errors, Context context);
    }

    public interface signUpView {
        public EditText getEmail();
        public EditText getPassword();
        public EditText getPasswordRepeat();
        public Context getSignUpContext();
    }
}
