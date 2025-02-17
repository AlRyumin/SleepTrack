package com.study.alryumin.sleeptrack.view.signin.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.study.alryumin.sleeptrack.view.authorization.presenter.AuthPresenter;
import com.study.alryumin.sleeptrack.view.main.MainActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.view.signin.contract.SignInContract;

import java.util.ArrayList;

public class SignInPresenter extends AuthPresenter implements SignInContract.Presenter {

    private SignInContract.View view;

    public SignInPresenter(SignInContract.View view){
        this.view = view;
    }

    public ArrayList<String> getErrors(Context context) {
        String email = view.getEmail().getText().toString();
        String password = view.getPassword().getText().toString();

        ArrayList<String> errors = new ArrayList<>();

        if(!isOnline()){
            errors.add(context.getString(R.string.error_offline));
            return errors;
        }

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
        String email = view.getEmail().getText().toString();
        String password = view.getPassword().getText().toString();

        final Context context = view.getSignInContext();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            context.startActivity(new Intent(context, MainActivity.class));
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), R.string.error_sign_in, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
