package com.study.alryumin.sleeptrack.view.authorization.contract;

import android.content.Context;
import android.widget.EditText;

import java.util.ArrayList;

public interface SignUpContract {

    interface Presenter {
        void userRegister();
        ArrayList<String> getErrors(Context context);
        void showErrors(ArrayList<String> errors, Context context);
    }

    interface View {
        EditText getEmail();
        EditText getPassword();
        EditText getPasswordRepeat();
        Context getSignUpContext();
    }
}
