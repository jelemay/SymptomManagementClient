package com.coursera.symptommanagement.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

import com.coursera.symptommanagement.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.coursera.symptommanagement.fragments.DatePickerFragment.DatePickerFragmentListener} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DatePickerFragment extends DialogFragment
                        implements DatePickerDialog.OnDateSetListener {

    public static final String FRAGMENT_NAME = "Date Picker Fragment: ";

    private static final String BUTTON_ID = "BUTTON_ID";
    private static final String BUTTON_TAG = "BUTTON_TAG";

    private int buttonId;
    private String btnTag;

    private DatePickerFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param v Parameter 1.
     * @return A new instance of fragment DatePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatePickerFragment newInstance(View v) {
        DatePickerFragment fragment = new DatePickerFragment();
        Log.d(FRAGMENT_NAME, "Entered DatePickerFragment newInstance()");
        Log.d(FRAGMENT_NAME, "This is button id: " + v.getId());
        int buttonId = v.getId();
        Log.d(FRAGMENT_NAME, "this is btnTag value: " + v.getTag().toString());
        String btnTag = v.getTag().toString();
        Log.d(FRAGMENT_NAME, "Button Id " + buttonId + " has been pressed.");
        Bundle args = new Bundle();
        args.putInt(BUTTON_ID, buttonId);
        args.putString(BUTTON_TAG, btnTag);
        fragment.setArguments(args);
        return fragment;
    }
    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Log.d(FRAGMENT_NAME, "Entered onDateSet().");
        if (mListener != null) {
            if (btnTag.equalsIgnoreCase("Reminder")) {
                mListener.getDateString(buttonId, year, month, day);
                Log.d(FRAGMENT_NAME, "Called getDateString() with buttonId");
            }
            else {
                mListener.getDateString(btnTag, year, month, day);
                Log.d(FRAGMENT_NAME, "Called getDateString() with buttonTag");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            buttonId = getArguments().getInt(BUTTON_ID);
            btnTag = getArguments().getString(BUTTON_TAG);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DatePickerFragmentListener) activity;
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
    public interface DatePickerFragmentListener {
        public String getDateString(int buttonId, int year, int month, int day);
        public String getDateString(String btnTag, int year, int month, int day);
    }

}
