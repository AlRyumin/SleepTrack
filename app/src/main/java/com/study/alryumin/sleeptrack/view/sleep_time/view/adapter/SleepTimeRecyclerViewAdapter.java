package com.study.alryumin.sleeptrack.view.sleep_time.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.view.sleep_time.view.SleepTimeView;

import java.util.List;

public class SleepTimeRecyclerViewAdapter extends RecyclerView.Adapter<SleepTimeRecyclerViewAdapter.ViewHolder> {

    private final List<SleepTime> values;
    private final SleepTimeView.OnListFragmentInteractionListener mListener;

    public SleepTimeRecyclerViewAdapter(List<SleepTime> items, SleepTimeView.OnListFragmentInteractionListener listener) {
        values = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sleep_time_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        SleepTime sleepTime = values.get(position);
        holder.item = sleepTime;
        holder.sleepStart.setText(sleepTime.getStartAtFormat());
        holder.sleepFinish.setText(sleepTime.getFinishAtFormat());
        holder.sleepTime.setText(sleepTime.getSleepTimeFormat(true));

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
}
