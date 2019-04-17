package com.study.alryumin.sleeptrack.fragment.auth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.study.alryumin.sleeptrack.AuthorizationActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.contract.AuthContract;
import com.study.alryumin.sleeptrack.presenter.SignInPresenter;

import java.util.ArrayList;

public class SignInFragment extends Fragment implements AuthContract.signInView, View.OnClickListener {
    private Button signInButton, signUpButton;
    private EditText email, password;

    private String emailText;
    private String passwordText;

    private AuthContract.signInPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

    protected void initView(View view) {
        signInButton = view.findViewById(R.id.signInButton);
        signUpButton = view.findViewById(R.id.signUpButton);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
    }

    protected void initListener() {
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        AuthorizationActivity activity = null;
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

    public void authUser() {
        Context context = this.getContext();

        ArrayList<String> errors = presenter.getErrors(context);

        if(errors.isEmpty()){
            presenter.userLogin();
        } else {
            presenter.showErrors(errors, context);
        }
    }

    protected void setBundledFields(Bundle savedInstanceState) {
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
}
