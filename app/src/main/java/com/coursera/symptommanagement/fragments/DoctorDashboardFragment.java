package com.coursera.symptommanagement.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;

import com.coursera.symptommanagement.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorDashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoctorDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DoctorDashboardFragment extends Fragment implements View.OnClickListener {

    public static final String FRAGMENT_NAME = "Doctor Info Fragment: ";

    private OnFragmentInteractionListener mListener;

    private Button btnPatientSearch;
    private Button btnPatientList;
    private Button btnPatientAdd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorDashboardFragment newInstance(String param1, String param2) {
        DoctorDashboardFragment fragment = new DoctorDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public DoctorDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(FRAGMENT_NAME, "Entered onCreateView()");

        View doctorInfoView = inflater.inflate(R.layout.fragment_doctor_info, container, false);

        btnPatientSearch = (Button) doctorInfoView.findViewById(R.id.buttonPatientSearch);
        btnPatientList = (Button) doctorInfoView.findViewById(R.id.buttonPatientList);
        btnPatientAdd = (Button) doctorInfoView.findViewById(R.id.buttonPatientAdd);

        btnPatientSearch.setOnClickListener(this);
        btnPatientList.setOnClickListener(this);
        btnPatientAdd.setOnClickListener(this);

        // Inflate the layout for this fragment
        return doctorInfoView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    //}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPatientSearch:
                Log.d(FRAGMENT_NAME, "Patient search button pressed.");
                mListener.onPatientSearchClicked(v);
                break;
            case R.id.buttonPatientList:
                Log.d(FRAGMENT_NAME, "Patient list button pressed.");
                mListener.onPatientListClicked(v);
                break;
            case R.id.buttonPatientAdd:
                Log.d(FRAGMENT_NAME, "Patient add button pressed.");
                mListener.onPatientAddClicked(v);
                break;
            default:
                Log.d(FRAGMENT_NAME, "Unknown button pressed.");
                break;
        }

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
    public interface OnFragmentInteractionListener {
        public void onPatientSearchClicked(View v);
        public void onPatientListClicked(View v);
        public void onPatientAddClicked(View v);
    }

}
