package com.coursera.symptommanagement.activities.patient;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.fragments.TimePickerFragment;

import java.util.Calendar;

public class PatientPreferencesActivity extends Activity
            implements TimePickerFragment.TimePickerFragmentListener {

    public static final String ACTIVITY_NAME = "Patient Preferences Activity: ";

    private Button btnReminderOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_preferences);

        // set each button tag to "reminder"
        Button btn1 = (Button) findViewById(R.id.btnReminderOne);
        btn1.setTag("Reminder");

        Button btn2 = (Button) findViewById(R.id.btnReminderTwo);
        btn2.setTag("Reminder");

        Button btn3 = (Button) findViewById(R.id.btnReminderThree);
        btn3.setTag("Reminder");

        Button btn4 = (Button) findViewById(R.id.btnReminderFour);
        btn4.setTag("Reminder");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timePicker = TimePickerFragment.newInstance(v);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public String getTimeString(int buttonId, int hourOfDay, int minute) {
        String am_pm = "";

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
        time.set(Calendar.MINUTE, minute);

        if (time.get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
        }
        else {
            am_pm = "PM";
        }

        String hourString = (time.get(Calendar.HOUR) == 0) ? "12" : time.get(Calendar.HOUR) + "";
        String minuteString = (time.get(Calendar.MINUTE) == 0) ? "00" : String.valueOf(minute);

        if (minuteString.length() < 2) {
            minuteString = "0" + minuteString;
        }

        String timeString = hourString + ":" + minuteString + " " + am_pm;

        switch (buttonId) {
            case R.id.btnReminderOne:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder One: " + timeString);
                TextView reminderOne = (TextView) findViewById(R.id.txtReminderOne);
                reminderOne.setText(timeString);
                break;
            case R.id.btnReminderTwo:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder Two: " + timeString);
                TextView reminderTwo = (TextView) findViewById(R.id.txtReminderTwo);
                reminderTwo.setText(timeString);
                break;
            case R.id.btnReminderThree:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder Three: " + timeString);
                TextView reminderThree = (TextView) findViewById(R.id.txtReminderThree);
                reminderThree.setText(timeString);
                break;
            case R.id.btnReminderFour:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder Four: " + timeString);
                TextView reminderFour = (TextView) findViewById(R.id.txtReminderFour);
                reminderFour.setText(timeString);
                break;
        }

        return timeString;
    }

    public String getTimeString(String buttonTag, int hourOfDay, int minute) {
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
