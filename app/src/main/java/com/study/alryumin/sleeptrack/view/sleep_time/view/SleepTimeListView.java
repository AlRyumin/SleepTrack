package com.study.alryumin.sleeptrack.view.sleep_time.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.study.alryumin.sleeptrack.Constants;
import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.view.sleep_time.presenter.SleepTimePresenter;
import com.study.alryumin.sleeptrack.view.sleep_time.view.adapter.SleepTimeRecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SleepTimeListView extends Fragment {
    private SleepTimePresenter sleepTimePresenter;
    private SleepTimeRecyclerViewAdapter adapter;
    private OnListFragmentInteractionListener listener;
    final String TAG = "SleepTimeListView";

    @BindView(R.id.fab)
    FloatingActionButton fab;

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

        View rootView = inflater.inflate(R.layout.sleep_time_viewholder, container, false);
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

    @OnClick({R.id.fab})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab:
                addSleepTime();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    private void addSleepTime(){
        Intent intent = new Intent(getContext(), SleepTimeAddView.class);
        startActivity(intent);
    }
}
