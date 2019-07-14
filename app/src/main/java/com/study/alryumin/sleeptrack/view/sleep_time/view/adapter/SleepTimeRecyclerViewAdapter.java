package com.study.alryumin.sleeptrack.view.sleep_time.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.helper.DatabaseHelper;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.repository.room.SleepTimeDao;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.App;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeListView;

import java.util.List;

public class SleepTimeRecyclerViewAdapter extends RecyclerView.Adapter<SleepTimeRecyclerViewAdapter.ViewHolder> {

    private final List<SleepTime> values;
    private final SleepTimeListView.OnListFragmentInteractionListener mListener;

    private DatabaseHelper database;
    private SleepTimeDao sleepTimeDao;

    public SleepTimeRecyclerViewAdapter(List<SleepTime> items, SleepTimeListView.OnListFragmentInteractionListener listener) {
        values = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sleep_time_list_view, parent, false);

        database = App.getInstance().getDatabase();
        sleepTimeDao = database.getSleepTimeDao();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SleepTime sleepTime = values.get(position);
        holder.item = sleepTime;
        holder.sleepStart.setText(DateFormatHelper.getDateFormat(sleepTime.getStartAt()));
        holder.sleepFinish.setText(DateFormatHelper.getDateFormat(sleepTime.getFinishAt()));
        holder.sleepTime.setText(DateFormatHelper.getTimeFormat(sleepTime.getSleepTime()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView sleepStart;
        public final TextView sleepFinish;
        public final TextView sleepTime;
        public SleepTime item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            sleepStart = view.findViewById(R.id.sleepStart);
            sleepFinish = view.findViewById(R.id.sleepFinish);
            sleepTime = view.findViewById(R.id.sleepTime);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + sleepStart.getText() + "'";
        }
    }

    public List<SleepTime> getValues() {
        return values;
    }

    public void removeItem(int position) {
        sleepTimeDao.delete(values.get(position));
        values.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(int position, SleepTime item) {
        sleepTimeDao.add(item);
        values.add(position, item);
        notifyItemInserted(position);
    }
//
//    public void changeItem(int position, SleepTime item, Context context) {
//        PopupWindow popUp = new PopupWindow(context);
////        initLayout(context, item);
//
//
//        context.startActivity(new Intent(context, EditView.class));
////        popUp.setContentView(editLayout);
////
////        popUp.showAtLocation(editLayout, Gravity.NO_GRAVITY, 0, 0);
////        popUp.setFocusable(true);
////        popUp.update();
////
////        saveButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                popUp.dismiss();
////                notifyItemChanged(position);
////            }
////        });
////
////        cancelButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                popUp.dismiss();
////                notifyItemChanged(position);
////            }
////        });
//    }
//
//    private void initLayout(Context context, SleepTime item) {
//        editLayout = new LinearLayout(context);
//        editLayoutChild = new LinearLayout(context);
//        editLayoutChild2 = new LinearLayout(context);
//
//        editTitle = new TextView(context);
//        editSleepTime = new EditText(context);
//        saveButton = new Button(context);
//        cancelButton = new Button(context);
//        chart = new PieChart(context);
//
//        int layoutPadding = 30;
//
//        editLayout.setPadding(layoutPadding, layoutPadding, layoutPadding, layoutPadding);
//
//        editLayoutChild = new LinearLayout(context);
//
//        editTitle.setText(R.string.sleep_time_edit_title);
//        editTitle.setGravity(Gravity.CENTER);
//        saveButton.setText(R.string.sleep_time_edit_button_save);
//        cancelButton.setBackground(context.getDrawable(R.drawable.ic_back));
//
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;
//
//        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        PieChart.LayoutParams chartParams = new PieChart.LayoutParams(width / 2, width / 2);
//
//        editLayout.setOrientation(LinearLayout.VERTICAL);
//        editLayout.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
//        editLayout.setMinimumWidth(width);
//        editLayout.setMinimumHeight(height);
//
//        editLayoutChild2.setGravity(Gravity.CENTER_VERTICAL);
//        editLayoutChild2.setPadding(width / 4 - layoutPadding, 100,0,0);
//
//        editLayoutChild.addView(cancelButton, new LinearLayout.LayoutParams(80, 80));
//        editLayoutChild.addView(editTitle, textParams);
//
//        editLayoutChild2.addView(chart, chartParams);
//
//        editLayout.addView(editLayoutChild);
//        editLayout.addView(editLayoutChild2, buttonParams);
//        editLayout.addView(editSleepTime, textParams);
//        editLayout.addView(saveButton, buttonParams);
//
//        chartData(context, item);
//    }
//
//
//    private void chartData(Context context, SleepTime item) {
////        chart.setDescription("Sales by employee (In Thousands $) ");
//        chart.setRotationEnabled(true);
//        //pieChart.setUsePercentValues(true);
//        //pieChart.setHoleColor(Color.BLUE);
//        //pieChart.setCenterTextColor(Color.BLACK);
//        chart.setHoleRadius(50f);
//        chart.setTransparentCircleAlpha(0);
//        chart.setCenterText(context.getText(R.string.sleep_time));
//        chart.setCenterTextSize(10);
//
//        addDataSet(context, item);
//    }
//
//    private void addDataSet(Context context, SleepTime item) {
//        float sleepTime = Float.parseFloat(DateFormatHelper.getTimeDoubleFormat(item.getSleepTime()));
//        float targetTime = Float.parseFloat(DateFormatHelper.getTimeDoubleFormat(target.getSleepTime()));
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//
//        entries.add(new PieEntry(sleepTime, sleepTime));
//        entries.add(new PieEntry(targetTime, targetTime));
//
//        //create the data set
//        PieDataSet pieDataSet = new PieDataSet(entries, "");
//        pieDataSet.setSliceSpace(0);
//        pieDataSet.setValueTextSize(12);
//
//        //add colors to dataset
//        ArrayList<Integer> colors = new ArrayList<>();
//
//        if (sleepTime > targetTime) {
//            bgColor = ContextCompat.getColor(context, R.color.colorErrorBackground);
//        } else if (sleepTime < targetTime) {
//            bgColor = ContextCompat.getColor(context, R.color.colorNoticeBackground);
//        } else {
//            bgColor = ContextCompat.getColor(context, R.color.colorSuccessBackground);
//        }
//
//        colors.add(bgColor);
//        colors.add(ContextCompat.getColor(context, R.color.colorPrimary));
//
//        pieDataSet.setColors(colors);
//        pieDataSet.setValueTextColor(ContextCompat.getColor(context, R.color.colorPrimaryAccent));
//
//        pieDataSet.setValueFormatter(new LabelValueFormatter());
//
//        chart.getLegend().setEnabled(false);
//        Description description = new Description();
//        description.setEnabled(false);
//        chart.setDescription(description);
//
//        //create pie data object
//        PieData pieData = new PieData(pieDataSet);
//        chart.setData(pieData);
//        chart.invalidate();
//    }
//
//
//
//    class LabelValueFormatter extends ValueFormatter {
//        @Override
//        public String getFormattedValue(float value) {
//            int hour = DateFormatHelper.getHour(value);
//            int minute = DateFormatHelper.getMinute(value);
//            String hourString, minuteString;
//
//            if (hour > 9) {
//                hourString = "" + hour;
//            } else {
//                hourString = "0" + hour;
//            }
//
//            if(minute > 9){
//                minuteString = minute + "";
//            } else {
//                minuteString = minute  + "0";
//            }
//            return hourString + ":" + minuteString;
//        }
//    }
}
