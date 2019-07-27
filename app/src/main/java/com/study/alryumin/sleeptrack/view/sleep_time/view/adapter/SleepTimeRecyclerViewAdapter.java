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
        holder.sleepStart.setText(DateFormatHelper.getTimeDateFormat(sleepTime.getStartAt()));
        holder.sleepFinish.setText(DateFormatHelper.getTimeDateFormat(sleepTime.getFinishAt()));
        holder.sleepTime.setText(DateFormatHelper.getTimeFormat(sleepTime.getSleepTime()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
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
}
