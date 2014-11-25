package com.coursera.symptommanagement.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.coursera.symptommanagement.fragments.TimePickerFragment.TimePickerFragmentListener} interface
 * to handle interaction events.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TimePickerFragment extends DialogFragment
                                    implements TimePickerDialog.OnTimeSetListener {

    public static final String FRAGMENT_NAME = "Time Picker Fragment: ";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BUTTON_ID = "BUTTON_ID";

    private int buttonId;

    private TimePickerFragmentListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param v Parameter 1.
     * @return A new instance of fragment TimePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimePickerFragment newInstance(View v) {
        TimePickerFragment fragment = new TimePickerFragment();
        int buttonId = v.getId();
        Log.d(FRAGMENT_NAME, "Button Id " + buttonId + " has been pressed.");
        Bundle args = new Bundle();
        args.putInt(BUTTON_ID, buttonId);
        fragment.setArguments(args);
        return fragment;
    }
    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.d(FRAGMENT_NAME, "Creating new dialog.");

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        Log.d(FRAGMENT_NAME, "Entered onTimeSet().");
        if (mListener != null) {
            mListener.getTimeString(buttonId, hourOfDay, minute);
            Log.d(FRAGMENT_NAME, "Called getTimeString()");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            buttonId = getArguments().getInt(BUTTON_ID);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TimePickerFragmentListener) activity;
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
    public interface TimePickerFragmentListener {
        public String getTimeString(int buttonId, int hourOfDay, int minute);
    }

}
