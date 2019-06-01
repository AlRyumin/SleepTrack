package com.study.alryumin.sleeptrack.view.main.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.alryumin.sleeptrack.R;
import com.study.alryumin.sleeptrack.model.SleepTime;
import com.study.alryumin.sleeptrack.view.main.presenter.SleepTimePresenter;
import com.study.alryumin.sleeptrack.view.main.view.adapater.SleepTimeRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;


public class SleepTimeView extends Fragment {
    private SleepTimePresenter sleepTimePresenter;

    private OnListFragmentInteractionListener mListener;

    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sleepTimePresenter = new SleepTimePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sleep_time_view, container, false);
        View list = rootView.findViewById(R.id.list);

        // Set the adapter
        if (list instanceof RecyclerView) {
            Context context = list.getContext();
            RecyclerView recyclerView = (RecyclerView) list;

                recyclerView.setLayoutManager(new LinearLayoutManager(context));


            recyclerView.setAdapter(new SleepTimeRecyclerViewAdapter(sleepTimePresenter.getItems(), mListener));
        }

        ButterKnife.bind(this, rootView);

        return rootView;
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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

    @OnItemSelected(R.id.spinner)
    void onItemSelected(int position) {
        Log.d("SPINNERPOS", Integer.toString(position));
    }
}
