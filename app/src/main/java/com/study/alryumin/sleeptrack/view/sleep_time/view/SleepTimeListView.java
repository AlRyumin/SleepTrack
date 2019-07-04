package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.view.sleep_time.presenter.SleepTimePresenter;
import com.study.alryumin.sleeptrack.view.sleep_time.view.adapter.SleepTimeRecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SleepTimeListView extends Fragment {
    private SleepTimePresenter sleepTimePresenter;
    private SleepTimeRecyclerViewAdapter adapter;
    private OnListFragmentInteractionListener listener;
    final String TAG = "SleepTimeListView";

//    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

//    @BindView(R.id.spinner)
//    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sleepTimePresenter = new SleepTimePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_sleep_time_view, container, false);
        View list = rootView.findViewById(R.id.list);
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);

        ButterKnife.bind(this, rootView);

        if (list instanceof RecyclerView) {
            Context context = list.getContext();
            RecyclerView recyclerView = (RecyclerView) list;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            List<SleepTime> items = sleepTimePresenter.getItems();
            Collections.reverse(items);

            adapter = new SleepTimeRecyclerViewAdapter(items, listener);

            recyclerView.setAdapter(adapter);

            SleepTimeTouchHelper sleepTimeTouchHelper = new SleepTimeTouchHelper(getContext());
            sleepTimeTouchHelper.setRecyclerView(recyclerView);
            sleepTimeTouchHelper.setAdapter(adapter);
            sleepTimeTouchHelper.setCoordinatorLayout(coordinatorLayout);

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(sleepTimeTouchHelper);
            itemTouchhelper.attachToRecyclerView(recyclerView);
        }

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            listener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(SleepTime item);
    }

//    @OnItemSelected(R.id.spinner)
//    void onItemSelected(int position) {
//        Log.d("SPINNERPOS", Integer.toString(position));
//    }
}
