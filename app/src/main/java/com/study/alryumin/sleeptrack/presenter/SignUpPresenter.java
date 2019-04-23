package com.study.alryumin.sleeptrack.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.study.alryumin.sleeptrack.MainActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.contract.AuthContract;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

public class SignUpPresenter extends AuthPresenter implements AuthContract.signUpPresenter{

    AuthContract.signUpView view;

    public SignUpPresenter(AuthContract.signUpView view){
        this.view = view;
    }

    @Override
    public ArrayList<String> getErrors(Context context) {
        ArrayList<String> errors = new ArrayList();

        String email = view.getEmail().getText().toString();
        String password = view.getPassword().getText().toString();
        String passwordRepeat = view.getPasswordRepeat().getText().toString();

        if(isOnline() == false){
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
                            Toast.makeText(context.getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
