package com.study.alryumin.sleeptrack.view.activity_track.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.utils.DateFormatHelper;
import com.study.alryumin.sleeptrack.view.activity_track.view.ActivityTrackView.OnListFragmentInteractionListener;
import com.study.alryumin.sleeptrack.model.ActivityTrack;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ActivityTrack} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ActivityTrackRecyclerViewAdapter extends RecyclerView.Adapter<ActivityTrackRecyclerViewAdapter.ViewHolder> {

    private final List<ActivityTrack> values;
    private final OnListFragmentInteractionListener listener;

    public ActivityTrackRecyclerViewAdapter(List<ActivityTrack> items, OnListFragmentInteractionListener listener) {
        values = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_track_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ActivityTrack activityTrack = values.get(position);
        holder.item = activityTrack;

        holder.activityTime.setText(DateFormatHelper.getTimeFormat(activityTrack.getActivityTime(), true));
        holder.activityStart.setText(DateFormatHelper.getTimeDateFormat(activityTrack.getStartAt()));
        holder.activityFinish.setText(DateFormatHelper.getTimeDateFormat(activityTrack.getFinishAt()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListFragmentInteraction(holder.item);
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
        public final TextView activityTime;
        public final TextView activityStart;
        public final TextView activityFinish;
        public ActivityTrack item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            activityTime = view.findViewById(R.id.activityTime);
            activityStart = view.findViewById(R.id.activityStart);
            activityFinish = view.findViewById(R.id.activityFinish);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + activityTime.getText() + "'";
        }
    }
}
