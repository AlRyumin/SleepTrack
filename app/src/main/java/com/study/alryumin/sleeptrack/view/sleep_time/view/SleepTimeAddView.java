package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.study.alryumin.sleeptrack.Constants;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.model.Target;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.repository.room.TargetDao;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.sleep_time.contract.AddSleepTimeContract;
import com.study.alryumin.sleeptrack.view.sleep_time.presenter.AddSleepTimePresenter;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepTimeAddView extends AppCompatActivity {
    private SleepTime sleepTime;
    private Target target;
    private AddSleepTimeContract.Presenter presenter;
    private DatabaseHelper database;
    private SleepTimeDao sleepTimeDao;
    private TargetDao targetDao;
    private EditText editDate;
    Calendar dateAndTime = Calendar.getInstance();

    @BindView(R.id.buttonBack)
    ImageButton buttonBack;
    @BindView(R.id.startAtDate)
    EditText startAtDate;
    @BindView(R.id.startAtTime)
    EditText startAtTime;
    @BindView(R.id.finishAtDate)
    EditText finishAtDate;
    @BindView(R.id.finishAtTime)
    EditText finishAtTime;
    @BindView(R.id.sleepTimeEdit)
    EditText sleepTimeEdit;
    @BindView(R.id.buttonSave)
    ImageButton buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_time_add_view);
        ButterKnife.bind(this);

        initView();
    }

    @OnClick({R.id.buttonBack, R.id.startAtDate, R.id.startAtTime, R.id.finishAtDate, R.id.finishAtTime, R.id.sleepTimeEdit, R.id.buttonSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonBack:
                finish();
                break;
            case R.id.startAtTime:
                pickSleepTime(startAtTime);
                break;
            case R.id.finishAtTime:
                pickSleepTime(finishAtTime);
                break;
            case R.id.sleepTimeEdit:
                pickSleepTime(sleepTimeEdit);
                break;
            case R.id.buttonSave:
                saveSleepTime();
                break;
            case R.id.startAtDate:
                pickSleepDate(startAtDate);
                break;
            case R.id.finishAtDate:
                pickSleepDate(finishAtDate);
                break;
        }
    }

    private void pickSleepDate(EditText editText) {
        editDate = editText;

        if (editText == startAtDate) {
            dateAndTime.setTime(sleepTime.getStartAt());
        } else if (editText == finishAtDate) {
            dateAndTime.setTime(sleepTime.getFinishAt());
        }

        new DatePickerDialog(this, R.style.DialogTheme, dateSetListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if (editDate == startAtDate) {
                sleepTime.setStartAt(dateAndTime.getTime());
            } else if (editDate == finishAtDate) {
                sleepTime.setFinishAt(dateAndTime.getTime());
            }

            editDate.setText(DateFormatHelper.getDateFormat(dateAndTime.getTime()));
        }


    };

    private void pickSleepTime(EditText editText) {
        int hour = DateFormatHelper.getHour(editText.getText().toString());
        int minute = DateFormatHelper.getMinute(editText.getText().toString());

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if (editText == startAtTime) {
                    presenter.setStartAt(hourOfDay, minutes);
                } else if (editText == finishAtTime) {
                    presenter.setFinishAt(hourOfDay, minutes);
                } else if (editText == sleepTimeEdit) {
                    presenter.setSleepTime(hourOfDay, minutes);
                }

                setTimeDate();
            }
        }, hour, minute, true);

        timePickerDialog.show();
        if (editText == sleepTimeEdit) {
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
        database = App.getInstance().getDatabase();
        sleepTimeDao = database.getSleepTimeDao();
        targetDao = database.getTargetDao();
        presenter = AddSleepTimePresenter.getInstance();
        sleepTime = new SleepTime();
        presenter.setSleepTime(sleepTime);
        target = targetDao.getLast();
        initTimeDate();
    }

    private void initTimeDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, target.getFinishAt().getHours());
        calendar.set(Calendar.MINUTE, target.getFinishAt().getMinutes());

        sleepTime.setFinishAt(calendar.getTime());

        Long d = sleepTime.getFinishAt().getTime() - target.getSleepTime();
        calendar.setTime(new Date(d));

        sleepTime.setStartAt(calendar.getTime());

        startAtTime.setText(DateFormatHelper.getTimeFormat(target.getStartAt()));
        startAtDate.setText(DateFormatHelper.getDateFormat(sleepTime.getStartAt()));
        finishAtTime.setText(DateFormatHelper.getTimeFormat(target.getFinishAt()));
        finishAtDate.setText(DateFormatHelper.getDateFormat(sleepTime.getFinishAt()));

        sleepTimeEdit.setText(DateFormatHelper.getTimeFormat(sleepTime.getSleepTime()));
    }

    private void saveSleepTime() {
        if (sleepTime.getStartAt().compareTo(sleepTime.getFinishAt()) == 1
                || sleepTime.getStartAt().compareTo(sleepTime.getFinishAt()) == 1) {

            Toast toast = Toast.makeText(this,
                    getText(R.string.error_sleep_time_duaration),
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        } else {
            sleepTimeDao.add(sleepTime);
            Toast toast = Toast.makeText(this,
                    getText(R.string.save_success),
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            finish();
        }
    }

    private void setTimeDate() {
        startAtTime.setText(DateFormatHelper.getTimeFormat(sleepTime.getStartAt()));
        finishAtTime.setText(DateFormatHelper.getTimeFormat(sleepTime.getFinishAt()));
        startAtDate.setText(DateFormatHelper.getDateFormat(sleepTime.getStartAt()));
        finishAtDate.setText(DateFormatHelper.getDateFormat(sleepTime.getFinishAt()));
        sleepTimeEdit.setText(DateFormatHelper.getTimeFormat(sleepTime.getSleepTime()));
    }
}
