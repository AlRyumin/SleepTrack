package com.study.alryumin.sleeptrack.fragment.auth;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.study.alryumin.sleeptrack.AuthorizationActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.contract.AuthContract;
import com.study.alryumin.sleeptrack.presenter.SignUpPresenter;

import java.util.ArrayList;

public class SignUpFragment extends Fragment implements AuthContract.signUpView, View.OnClickListener{

    private EditText email, password, passwordRepeat;
    private Button signUpButton, signInButton;
    private AuthContract.signUpPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignUpPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initView(rootView);
        initListener();

        return rootView;
    }

    protected void initView(View view) {
        signInButton = view.findViewById(R.id.signInButton);
        signUpButton = view.findViewById(R.id.signUpButton);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        passwordRepeat = view.findViewById(R.id.passwordRepeat);
    }

    protected void initListener() {
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AuthorizationActivity activity = null;
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

    public void registerUser() {
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
