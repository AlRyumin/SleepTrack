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
import com.study.alryumin.sleeptrack.view.authorization.presenter.SignInPresenter;

import java.util.ArrayList;

import butterknife.BindView;

public class SignInFragment extends Fragment implements AuthContract.signInView, View.OnClickListener {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.signInButton) Button signInButton;
    @BindView(R.id.signUpButton) Button signUpButton;

    private String emailText;
    private String passwordText;

    private AuthContract.signInPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        initView(rootView);
        initListener();

        if (savedInstanceState != null) {
            setBundledFields(savedInstanceState);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("emailText", email.getText().toString());
        outState.putSerializable("passwordText", password.getText().toString());
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
            case R.id.signInButton:
                authUser();
                break;
            case R.id.signUpButton:
                activity = (AuthorizationActivity) getActivity();
                activity.setContent(new SignUpFragment());
                break;
        }
    }

    private void authUser() {
        Context context = this.getContext();

        ArrayList<String> errors = presenter.getErrors(context);

        if(errors.isEmpty()){
            presenter.userLogin();
        } else {
            presenter.showErrors(errors, context);
        }
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
    public Context getSignInContext() {
        return this.getContext();
    }
}
