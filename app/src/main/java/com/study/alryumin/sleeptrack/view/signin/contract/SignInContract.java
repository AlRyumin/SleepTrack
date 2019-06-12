package com.study.alryumin.sleeptrack.view.signin.contract;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;

public interface SignInContract {
    interface Presenter {
        void userLogin();
        ArrayList<String> getErrors(Context context);
        void showErrors(ArrayList<String> errors, Context context);
    }

    interface View {
        EditText getEmail();
        EditText getPassword();
        Context getSignInContext();
    }
}
