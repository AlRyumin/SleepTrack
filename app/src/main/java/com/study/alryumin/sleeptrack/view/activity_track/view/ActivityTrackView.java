package com.study.alryumin.sleeptrack.view.activity_track.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.ActivityTrack;
import com.study.alryumin.sleeptrack.view.activity_track.contract.ActivityTrackContract;
import com.study.alryumin.sleeptrack.view.activity_track.presenter.ActivityTrackPresenter;
import com.study.alryumin.sleeptrack.view.activity_track.view.adapter.ActivityTrackRecyclerViewAdapter;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ActivityTrackView extends Fragment implements ActivityTrackContract.View {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ActivityTrackContract.Presenter activityTrackPresenter;
    private RecyclerView recyclerView;

    Calendar dateAndTime = Calendar.getInstance();

    @BindView(R.id.dateButton)
    Button dateButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTrackPresenter = new ActivityTrackPresenter();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activitytrack_list, container, false);
        View list = rootView.findViewById(R.id.list);

        if (list instanceof RecyclerView) {
            Context context = list.getContext();
            recyclerView = (RecyclerView) list;

//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            List<ActivityTrack> items = activityTrackPresenter.getItems(dateAndTime);
            Collections.reverse(items);

            recyclerView.setAdapter(new ActivityTrackRecyclerViewAdapter(items, mListener));
        }

        ButterKnife.bind(this, rootView);

        setInitialDateTime();

        return rootView;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ActivityTrack item);
    }

    public void setDate() {
        new DatePickerDialog(this.getContext(), R.style.DialogTheme, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

            List<ActivityTrack> items = activityTrackPresenter.getItems(dateAndTime);
            Collections.reverse(items);

            recyclerView.setAdapter(new ActivityTrackRecyclerViewAdapter(items, mListener));

        }
    };

    private void setInitialDateTime() {
        dateButton.setText(DateUtils.formatDateTime(this.getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    @OnClick(R.id.dateButton)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dateButton:
                setDate();
                break;
        }
    }
}
