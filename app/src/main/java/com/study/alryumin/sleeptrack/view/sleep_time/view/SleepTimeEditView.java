package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.study.alryumin.sleeptrack.Constants;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.helper.TargetHelper;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.model.Target;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.sleep_time.contract.EditSleepTimeContract;
import com.study.alryumin.sleeptrack.view.sleep_time.presenter.EditSleepTimePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SleepTimeEditView extends AppCompatActivity {
    private Target target;
    private SleepTime sleepTime;
    private EditSleepTimeContract.Presenter presenter;
    private DatabaseHelper database;
    private SleepTimeDao sleepTimeDao;

    @BindView(R.id.chart)
    PieChart chart;
    @BindView(R.id.actualSleepTime)
    TextView actualSleepTime;
    @BindView(R.id.buttonBack)
    ImageButton buttonBack;
    @BindView(R.id.startAt)
    EditText startAt;
    @BindView(R.id.finishAt)
    EditText finishAt;
    @BindView(R.id.sleepTimeEdit)
    EditText sleepTimeEdit;
    @BindView(R.id.buttonReset)
    Button buttonReset;
    @BindView(R.id.buttonSave)
    ImageButton buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_time_edit_view);

        ButterKnife.bind(this);

        initView();
    }

    @OnClick({R.id.buttonBack, R.id.startAt, R.id.finishAt, R.id.sleepTimeEdit, R.id.buttonReset, R.id.buttonSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.buttonBack:
                finish();
                break;
            case R.id.startAt:
                pickSleepTime(startAt);
                break;
            case R.id.finishAt:
                pickSleepTime(finishAt);
                break;
            case R.id.sleepTimeEdit:
                pickSleepTime(sleepTimeEdit);
                break;
            case R.id.buttonReset:
                resetSleepTime();
                break;
            case R.id.buttonSave:
                saveSleepTime();
                break;
        }
    }

    public void pickSleepTime(EditText editText) {
        int hour = DateFormatHelper.getHour(editText.getText().toString());
        int minute = DateFormatHelper.getMinute(editText.getText().toString());

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if (editText == startAt) {
                    presenter.setStartAt(hourOfDay, minutes);
                } else if (editText == finishAt) {
                    presenter.setFinishAt(hourOfDay, minutes);
                } else if (editText == sleepTimeEdit) {
                    presenter.setSleepTime(hourOfDay, minutes);
                }

                setSleepTimeFields();
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
        presenter = EditSleepTimePresenter.getInstance();
        long sleepTimeId = getIntent().getLongExtra(Constants.SLEEP_TIME_ID, 0);
        presenter.setSleepTime(sleepTimeId);
        target = TargetHelper.getInstance().getTarget();
        sleepTime = presenter.getSleepTime();

//        chartData(sleepTime);

        setSleepTimeFields();
    }

    private void setSleepTimeFields(){
        startAt.setText(DateFormatHelper.getTimeFormat(sleepTime.getStartAt()));
        finishAt.setText(DateFormatHelper.getTimeFormat(sleepTime.getFinishAt()));
        sleepTimeEdit.setText(DateFormatHelper.getTimeFormat(sleepTime.getSleepTime()));

        chartData(sleepTime);
    }

    private void chartData(SleepTime item) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        ViewGroup.LayoutParams params = chart.getLayoutParams();

        params.width = width / 2;
        params.height = width / 2;

        chart.requestLayout();

        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) chart.getLayoutParams();
        marginParams.setMargins(width / 4, 0,0,0);

        chart.requestLayout();

        chart.setRotationEnabled(true);

        chart.setHoleRadius(50f);
        chart.setTransparentCircleAlpha(0);
        chart.setCenterText(getText(R.string.nav_sleep_time));
        chart.setCenterTextSize(10);

        addDataSet(item);
    }

    private void addDataSet(SleepTime item) {
        float sleepTime = Float.parseFloat(DateFormatHelper.getTimeDoubleFormat(item.getSleepTime()));
        float targetTime = Float.parseFloat(DateFormatHelper.getTimeDoubleFormat(target.getSleepTime()));
        int bgColor;

        float rotationAngle = getChartRotationAngle(chart.getRotationAngle(), sleepTime, targetTime);
        chart.setRotationAngle(rotationAngle);

        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(sleepTime, sleepTime));
        entries.add(new PieEntry(targetTime, targetTime));

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(0);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();

        if (sleepTime > targetTime) {
            bgColor = ContextCompat.getColor(this, R.color.colorErrorBackground);
            actualSleepTime.setBackgroundColor(bgColor);
        } else if (sleepTime < targetTime) {
            bgColor = ContextCompat.getColor(this, R.color.colorNoticeBackground);
            actualSleepTime.setBackgroundColor(bgColor);
        } else {
            bgColor = ContextCompat.getColor(this, R.color.colorSuccessBackground);
        }

        colors.add(bgColor);
        colors.add(ContextCompat.getColor(this, R.color.colorPrimary));

        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(ContextCompat.getColor(this, R.color.colorPrimaryAccent));

        pieDataSet.setValueFormatter(new SleepTimeEditView.LabelValueFormatter());

        chart.getLegend().setEnabled(false);
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        chart.setNoDataText("Description that you want");

        chart.setData(pieData);
        chart.invalidate();
    }

    private float getChartRotationAngle(Float rotationAngle, float sleepTime, float targetTime){
        Float defaultRotationAngle = 270f;
        float circumference = sleepTime + targetTime;
        double percent = Math.ceil((targetTime - sleepTime) / circumference * 100);
        double angle = Math.ceil(360 * (int) percent / 100);

        return defaultRotationAngle + (int) angle / 2;
    }

    private void resetSleepTime() {
        presenter.resetSleepTime();
        showToast(getString(R.string.reset_success));
        setSleepTimeFields();
    }

    private void saveSleepTime() {
        presenter.saveSleepTime();
        showToast(getString(R.string.save_success));
        setSleepTimeFields();
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    class LabelValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            int hour = DateFormatHelper.getHour(value);
            int minute = DateFormatHelper.getMinute(value);
            String hourString, minuteString;

            if (hour > 9) {
                hourString = "" + hour;
            } else {
                hourString = "0" + hour;
            }

            if (minute > 9) {
                minuteString = minute + "";
            } else {
                minuteString = minute + "0";
            }
            return hourString + ":" + minuteString;
        }
    }
}
