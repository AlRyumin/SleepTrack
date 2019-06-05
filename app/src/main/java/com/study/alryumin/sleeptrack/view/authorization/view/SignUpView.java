package com.study.alryumin.sleeptrack.view.authorization.view;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.study.alryumin.sleeptrack.AuthorizationActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.view.authorization.contract.SignUpContract;
import com.study.alryumin.sleeptrack.view.authorization.presenter.SignUpPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpView extends Fragment implements SignUpContract.View, View.OnClickListener{
    private SignUpContract.Presenter presenter;

    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.passwordRepeat) EditText passwordRepeat;
    @BindView(R.id.signUpButton) Button signUpButton;
    @BindView(R.id.signInButton) Button signInButton;

    private String emailText;
    private String passwordText;
    private String passwordRepeatText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        if (savedInstanceState != null) {
            setBundledFields(savedInstanceState);
        }

        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    public void initView(){
        signInButton.setPaintFlags(signInButton.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }

    @OnClick({R.id.signInButton, R.id.signUpButton})
    public void onClick(View view) {
        AuthorizationActivity activity;
        switch (view.getId()) {
            case R.id.signUpButton:
                registerUser();
                break;
            case R.id.signInButton:
                activity = (AuthorizationActivity) getActivity();
                activity.setContent(new SignInView());
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

    private void setBundledFields(Bundle savedInstanceState) {
        if (savedInstanceState.getSerializable("emailText") != null) {
            emailText = (String) savedInstanceState.getSerializable("emailText");
            email.setText(emailText);
        }
        if (savedInstanceState.getSerializable("passwordText") != null) {
            passwordText = (String) savedInstanceState.getSerializable("passwordText");
            password.setText(passwordText);
        }
        if (savedInstanceState.getSerializable("passwordRepeatText") != null) {
            passwordRepeatText = (String) savedInstanceState.getSerializable("passwordRepeatText");
            passwordRepeat.setText(passwordRepeatText);
        }
    }
}
