package com.study.alryumin.sleeptrack.presenter;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.contract.AuthContract;

import java.util.ArrayList;

public class SignInPresenter implements AuthContract.signInPresenter {

    public ArrayList<String> getErrors(String email, String password, Context context) {
        ArrayList<String> errors = new ArrayList();
        if (email.isEmpty()) {
            errors.add(context.getString(R.string.error_empty_email));
        }

        if (password.isEmpty()) {
            errors.add(context.getString(R.string.error_empty_password));
        }

        return errors;
    }

    public void showErrors(ArrayList<String> errors, Context context){
        Toast toast = Toast.makeText(context,
                android.text.TextUtils.join("\n", errors),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    public void userLogin() {

    }
}
