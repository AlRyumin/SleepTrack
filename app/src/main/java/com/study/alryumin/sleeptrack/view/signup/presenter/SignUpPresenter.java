package com.study.alryumin.sleeptrack.view.signup.presenter;

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
import com.study.alryumin.sleeptrack.view.signup.contract.SignUpContract;

import java.util.ArrayList;

public class SignUpPresenter extends AuthPresenter implements SignUpContract.Presenter {

    private SignUpContract.View view;

    public SignUpPresenter(SignUpContract.View view){
        this.view = view;
    }

    @Override
    public ArrayList<String> getErrors(Context context) {
        ArrayList<String> errors = new ArrayList<>();

        String email = view.getEmail().getText().toString();
        String password = view.getPassword().getText().toString();
        String passwordRepeat = view.getPasswordRepeat().getText().toString();

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

        if (passwordRepeat.isEmpty()) {
            errors.add(context.getString(R.string.error_empty_password_repeat));
        }

        if (!passwordRepeat.equals(password)) {
            errors.add(context.getString(R.string.error_password_not_match));
        }

        return errors;
    }

    @Override
    public void showErrors(ArrayList<String> errors, Context context){
        Toast toast = Toast.makeText(context,
                android.text.TextUtils.join("\n", errors),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    @Override
    public void userRegister(){
        String email = view.getEmail().getText().toString();
        String password = view.getPassword().getText().toString();
        final Context context = view.getSignUpContext();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            context.startActivity(new Intent(context, MainActivity.class));
//                            finish();
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(),R.string.error_sign_up, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
