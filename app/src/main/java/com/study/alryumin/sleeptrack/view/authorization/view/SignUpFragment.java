package com.study.alryumin.sleeptrack.view.authorization.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.study.alryumin.sleeptrack.AuthorizationActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.view.authorization.contract.AuthContract;
import com.study.alryumin.sleeptrack.view.authorization.presenter.SignUpPresenter;

import java.util.ArrayList;

import butterknife.BindView;

public class SignUpFragment extends Fragment implements AuthContract.signUpView, View.OnClickListener{
    private AuthContract.signUpPresenter presenter;

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordRepeat) EditText passwordRepeat;
    @BindView(R.id.signUpButton) Button signUpButton;
    @BindView(R.id.signInButton) Button signInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initView(rootView);
        initListener();

        return rootView;
    }

    private void initView(View view) {

    }

    private void initListener() {
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AuthorizationActivity activity;
        switch (view.getId()) {
            case R.id.signUpButton:
                registerUser();
                break;
            case R.id.signInButton:
                activity = (AuthorizationActivity) getActivity();
                activity.setContent(new SignInFragment());
                break;
        }
    }

    private void registerUser() {
        Context context = this.getContext();

        ArrayList<String> errors = presenter.getErrors(context);

        if(errors.isEmpty()){
            presenter.userRegister();
        } else {
            presenter.showErrors(errors, context);
        }
    }

    @Override
    public EditText getEmail() {
        return this.email;
    }

    @Override
    public EditText getPassword() {
        return this.password;
    }

    @Override
    public EditText getPasswordRepeat() {
        return this.passwordRepeat;
    }

    @Override
    public Context getSignUpContext() {
        return this.getContext();
    }
}
