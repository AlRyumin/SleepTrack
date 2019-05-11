package com.study.alryumin.sleeptrack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.alryumin.sleeptrack.view.authorization.view.SignInFragment;
import com.study.alryumin.sleeptrack.view.authorization.view.SignUpFragment;

public class AuthorizationActivity extends AppCompatActivity {

    private static final int LOGIN_FRAGMENT = 1, REGISTER_FRAGMENT = 2;

    private int activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        if (savedInstanceState != null) {
            setBundledFields(savedInstanceState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContent();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putSerializable("activeFragment", activeFragment);
    }

    protected void setBundledFields(Bundle savedInstanceState) {
        if (savedInstanceState.getSerializable("activeFragment") != null) {
            activeFragment = (int) savedInstanceState.getSerializable("activeFragment");
        }
    }

    public void setContent() {
        int content = R.id.content;

        Fragment fragment = new SignInFragment();

        switch (activeFragment) {
            case REGISTER_FRAGMENT:
                fragment = new SignUpFragment();
                break;
        }

        replaceFragment(fragment, content);
    }

    public void setContent(Fragment fragment) {
        int content = R.id.content;
        replaceFragment(fragment, content);
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        saveActiveFragment(fragment);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void saveActiveFragment(Fragment fragment) {
        if(fragment instanceof SignUpFragment){
            activeFragment = REGISTER_FRAGMENT;
        } else {
            activeFragment = LOGIN_FRAGMENT;
        }
    }
}
