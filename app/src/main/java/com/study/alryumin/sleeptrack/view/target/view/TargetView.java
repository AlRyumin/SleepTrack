package com.study.alryumin.sleeptrack.view.target.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.target.contract.TargetContract;
import com.study.alryumin.sleeptrack.view.target.presenter.TargetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TargetView extends Fragment implements TargetContract.View {
    TargetContract.Presenter presenter;

    @BindView(R.id.startAt)
    EditText startAt;
    @BindView(R.id.finishAt)
    EditText finishAt;
    @BindView(R.id.sleepTime)
    EditText sleepTime;
    @BindView(R.id.buttonReset)
    Button buttonReset;
    @BindView(R.id.buttonSave)
    Button buttonSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = TargetPresenter.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.target_view, container, false);

        ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }

    @OnClick({R.id.startAt, R.id.finishAt, R.id.sleepTime, R.id.buttonReset, R.id.buttonSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startAt:
                pickSleepTime(startAt);
                break;
            case R.id.finishAt:
                pickSleepTime(finishAt);
                break;
            case R.id.sleepTime:
                pickSleepTime(sleepTime);
                break;
            case R.id.buttonReset:
                resetTarget();
                break;
            case R.id.buttonSave:
                saveTarget();
                break;
        }
    }

    public void pickSleepTime(EditText editText) {
        int hour = DateFormatHelper.getHour(editText.getText().toString());
        int minute = DateFormatHelper.getMinute(editText.getText().toString());

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
//                presenter.setMinSleepTime(hourOfDay, minutes);
                if (editText == startAt) {
                    presenter.setStartAt(hourOfDay, minutes);
                } else if (editText == finishAt) {
                    presenter.setFinishAt(hourOfDay, minutes);
                } else if (editText == sleepTime) {
                    presenter.setSleepTime(hourOfDay, minutes);
                }

                initView();
            }
        }, hour, minute, true);

        timePickerDialog.show();
        if (editText == sleepTime) {
            hideAmPmLayout(timePickerDialog);
        }
    }

    private void hideAmPmLayout(TimePickerDialog picker) {
        final int id = getResources().getSystem().getIdentifier("ampm_layout", "id", "android");
        final View amPmLayout = picker.findViewById(id);
        if (amPmLayout != null) {
            amPmLayout.setVisibility(View.GONE);
        }
    }

    private void initView() {
        startAt.setText(DateFormatHelper.getTimeFormat(presenter.getStartAt()));
        finishAt.setText(DateFormatHelper.getTimeFormat(presenter.getFinishAt()));
        sleepTime.setText(DateFormatHelper.getTimeFormat(presenter.getSleepTime()));
    }

    private void resetTarget() {
        presenter.resetTarget();
        showToast(getContext().getString(R.string.reset_success));
        initView();
    }

    private void saveTarget() {
        presenter.saveTarget();
        showToast(getContext().getString(R.string.save_success));
        initView();
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getContext(),
                message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}
