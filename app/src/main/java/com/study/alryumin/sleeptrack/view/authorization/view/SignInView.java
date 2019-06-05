package com.study.alryumin.sleeptrack.view.authorization.view;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.study.alryumin.sleeptrack.AuthorizationActivity;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.view.authorization.contract.SignInContract;
import com.study.alryumin.sleeptrack.view.authorization.presenter.SignInPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInView extends Fragment implements SignInContract.View, View.OnClickListener {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.signInButton) Button signInButton;
    @BindView(R.id.signUpButton) Button signUpButton;

    private String emailText;
    private String passwordText;

    private SignInContract.Presenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SignInPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        if (savedInstanceState != null) {
            setBundledFields(savedInstanceState);
        }

        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    public void initView(){
        signUpButton.setPaintFlags(signUpButton.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("emailText", email.getText().toString());
        outState.putSerializable("passwordText", password.getText().toString());
    }

    @OnClick({R.id.signInButton, R.id.signUpButton})
    public void onClick(View view) {
        AuthorizationActivity activity;
        switch (view.getId()) {
            case R.id.signInButton:
                authUser();
                break;
            case R.id.signUpButton:
                activity = (AuthorizationActivity) getActivity();
                activity.setContent(new SignUpView());
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
        if (savedInstanceState.getSerializable("emailText") != null && email != null) {
            emailText = (String) savedInstanceState.getSerializable("emailText");
            email.setText(emailText);
        }
        if (savedInstanceState.getSerializable("passwordText") != null && password != null) {
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
