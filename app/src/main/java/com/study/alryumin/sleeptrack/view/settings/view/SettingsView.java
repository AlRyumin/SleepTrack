package com.study.alryumin.sleeptrack.view.settings.view;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.helper.SettingsHelper;
import com.study.alryumin.sleeptrack.model.AppSettings;
import com.study.alryumin.sleeptrack.view.settings.contract.SettingsContract;
import com.study.alryumin.sleeptrack.view.settings.presenter.SettingsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsView extends Fragment implements SettingsContract.View {
    SettingsContract.Presenter presenter;
    private ProgressDialog progressDialog;


    @BindView(R.id.sleepTimePick)
    EditText sleepTimePick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_view, container, false);

        ButterKnife.bind(this, rootView);
        initView(rootView);

        return rootView;
    }

    @OnClick({R.id.sleepTimePick})
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.sleepTimePick:
                pickSleepTime();
                break;
        }
    }

    private void initView(View view){
        presenter = SettingsPresenter.getInstance();
        sleepTimePick.setText(presenter.getMinSleepTime());
    }

    public void pickSleepTime(){
        Long currentTime = presenter.getAppSettings().getMinSleepTime();
        int currentHour = SettingsHelper.hourFromMilliseconds(currentTime);
        int currentMinute = SettingsHelper.minuteFromMilliseconds(currentTime);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                presenter.setMinSleepTime(hourOfDay, minutes);
                sleepTimePick.setText(presenter.getMinSleepTime());
            }
        }, currentHour, currentMinute, false);

        timePickerDialog.show();
        hideAmPmLayout(timePickerDialog);
    }

    private void hideAmPmLayout(TimePickerDialog picker) {
        final int id = getResources().getSystem().getIdentifier("ampm_layout", "id", "android");
        final View amPmLayout = picker.findViewById(id);
        if(amPmLayout != null) {
            amPmLayout.setVisibility(View.GONE);
        }
    }
}
