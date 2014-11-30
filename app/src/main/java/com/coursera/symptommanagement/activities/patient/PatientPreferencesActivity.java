package com.coursera.symptommanagement.activities.patient;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.fragments.TimePickerFragment;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.models.PatientReminderRequest;
import com.coursera.symptommanagement.models.Reminder;
import com.coursera.symptommanagement.receivers.ReminderAlarmReceiver;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.Date;
import java.text.ParseException;

public class PatientPreferencesActivity extends Activity
            implements TimePickerFragment.TimePickerFragmentListener {

    public static final String ACTIVITY_NAME = "Patient Preferences Activity: ";

    private static final int DASH_BUTTON = 0;

    private int uniqueAlarmIndex = 0;

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_preferences);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT");

        // pull saved reminders from server
        getCurrentReminders(patient);

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

    public void getCurrentReminders(final Patient patient) {
        final PatientServiceAPI patientService = SvcStore.getPatientService();

        CallableTask.invoke(new Callable<List<Reminder>>() {
            @Override
            public List<Reminder> call() throws Exception {
                return patientService.getPatientReminders(patient.getId());
            }
        }, new TaskCallback<List<Reminder>>() {

            @Override
            public void success(final List<Reminder> reminders) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ArrayList<Reminder> reminderList = new ArrayList<Reminder>(reminders);

                        Reminder reminderOne = reminderList.get(0);
                        TextView txtReminderOne = (TextView) findViewById(R.id.txtReminderOne);
                        txtReminderOne.setText(getTimeFromLong(reminderOne.getTime()));
                        TextView txtReminderOneLong = (TextView) findViewById(R.id.txtReminderOneLong);
                        txtReminderOneLong.setText(String.valueOf(reminderOne.getTime()));

                        Reminder reminderTwo = reminderList.get(1);
                        TextView txtReminderTwo = (TextView) findViewById(R.id.txtReminderTwo);
                        txtReminderTwo.setText(getTimeFromLong(reminderTwo.getTime()));
                        TextView txtReminderTwoLong = (TextView) findViewById(R.id.txtReminderTwoLong);
                        txtReminderTwoLong.setText(String.valueOf(reminderTwo.getTime()));

                        Reminder reminderThree = reminderList.get(2);
                        TextView txtReminderThree = (TextView) findViewById(R.id.txtReminderThree);
                        txtReminderThree.setText(getTimeFromLong(reminderThree.getTime()));
                        TextView txtReminderThreeLong = (TextView) findViewById(R.id.txtReminderThreeLong);
                        txtReminderThreeLong.setText(String.valueOf(reminderThree.getTime()));

                        Reminder reminderFour = reminderList.get(3);
                        TextView txtReminderFour = (TextView) findViewById(R.id.txtReminderFour);
                        txtReminderFour.setText(getTimeFromLong(reminderFour.getTime()));
                        TextView txtReminderFourLong = (TextView) findViewById(R.id.txtReminderFourLong);
                        txtReminderFourLong.setText(String.valueOf(reminderFour.getTime()));

                    }
                });
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error pulling reminders from patient.", e);
                Toast.makeText(
                        PatientPreferencesActivity.this,
                        "Could not pull reminders.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    private String getTimeFromLong(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String am_pm = "";

        if (cal.get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
            cal.set(Calendar.AM_PM, Calendar.AM);
        }
        else {
            am_pm = "PM";
            cal.set(Calendar.AM_PM, Calendar.PM);
        }

        // prepare time string to pass to text field
        String hourString = (cal.get(Calendar.HOUR) == 0) ? "12" : cal.get(Calendar.HOUR) + "";
        String minuteString = (cal.get(Calendar.MINUTE) == 0) ? "00" : cal.get(Calendar.MINUTE) + "";

        if (minuteString.length() < 2) {
            minuteString = "0" + minuteString;
        }

        return hourString + ":" + minuteString + " " + am_pm;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timePicker = TimePickerFragment.newInstance(v);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void getTimeString(int buttonId, int hourOfDay, int minute) {
        String am_pm = "";

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
        time.set(Calendar.MINUTE, minute);
        time.set(Calendar.SECOND, 0);

        if (time.get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
            time.set(Calendar.AM_PM, Calendar.AM);
        } else {
            am_pm = "PM";
            time.set(Calendar.AM_PM, Calendar.PM);
        }

        // prepare time string to pass to text field
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
                TextView reminderOneLong = (TextView) findViewById(R.id.txtReminderOneLong);
                reminderOneLong.setText(String.valueOf(time.getTimeInMillis()));
                break;
            case R.id.btnReminderTwo:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder Two: " + timeString);
                TextView reminderTwo = (TextView) findViewById(R.id.txtReminderTwo);
                reminderTwo.setText(timeString);
                TextView reminderTwoLong = (TextView) findViewById(R.id.txtReminderTwoLong);
                reminderTwoLong.setText(String.valueOf(time.getTimeInMillis()));
                break;
            case R.id.btnReminderThree:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder Three: " + timeString);
                TextView reminderThree = (TextView) findViewById(R.id.txtReminderThree);
                reminderThree.setText(timeString);
                TextView reminderThreeLong = (TextView) findViewById(R.id.txtReminderThreeLong);
                reminderThreeLong.setText(String.valueOf(time.getTimeInMillis()));
                break;
            case R.id.btnReminderFour:
                Log.d(ACTIVITY_NAME, "Updating time in Reminder Four: " + timeString);
                TextView reminderFour = (TextView) findViewById(R.id.txtReminderFour);
                reminderFour.setText(timeString);
                TextView reminderFourLong = (TextView) findViewById(R.id.txtReminderFourLong);
                reminderFourLong.setText(String.valueOf(time.getTimeInMillis()));
                break;
            default:
                break;
        }

        uniqueAlarmIndex++;

    }

    public String getTimeString(String buttonTag, int hourOfDay, int minute) {
        return null;
    }

    public void savePatientReminders(View v) {
        final PatientServiceAPI patientService = SvcStore.getPatientService();

        TextView tvReminderOneLong = (TextView) findViewById(R.id.txtReminderOneLong);
        TextView tvReminderTwoLong = (TextView) findViewById(R.id.txtReminderTwoLong);
        TextView tvReminderThreeLong = (TextView) findViewById(R.id.txtReminderThreeLong);
        TextView tvReminderFourLong = (TextView) findViewById(R.id.txtReminderFourLong);

        String reminderOne = tvReminderOneLong.getText().toString();
        String reminderTwo = tvReminderTwoLong.getText().toString();
        String reminderThree = tvReminderThreeLong.getText().toString();
        String reminderFour = tvReminderFourLong.getText().toString();

        // set up alarm manager for each time
        Intent checkInIntent = new Intent(this, ReminderAlarmReceiver.class);

        // Reminder One
        PendingIntent operationOne = PendingIntent.getBroadcast(this, uniqueAlarmIndex, checkInIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManagerOne = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerOne.set(AlarmManager.RTC, Long.parseLong(reminderOne), operationOne);
        uniqueAlarmIndex++;

        // Reminder Two
        PendingIntent operationTwo = PendingIntent.getBroadcast(this, uniqueAlarmIndex, checkInIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManagerTwo = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerTwo.set(AlarmManager.RTC, Long.parseLong(reminderTwo), operationTwo);
        uniqueAlarmIndex++;

        // Reminder Three
        PendingIntent operationThree = PendingIntent.getBroadcast(this, uniqueAlarmIndex, checkInIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManagerThree = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerThree.set(AlarmManager.RTC, Long.parseLong(reminderThree), operationThree);
        uniqueAlarmIndex++;

        // Reminder Four
        PendingIntent operationFour = PendingIntent.getBroadcast(this, uniqueAlarmIndex, checkInIntent,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManagerFour = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerFour.set(AlarmManager.RTC, Long.parseLong(reminderThree), operationFour);
        uniqueAlarmIndex++;

        Log.d(ACTIVITY_NAME, "Found reminder: " + reminderOne);
        Log.d(ACTIVITY_NAME, "Found reminder: " + reminderTwo);
        Log.d(ACTIVITY_NAME, "Found reminder: " + reminderThree);
        Log.d(ACTIVITY_NAME, "Found reminder: " + reminderFour);

        List<Reminder> newReminders = new ArrayList<Reminder>();
        newReminders.add(new Reminder(Long.parseLong(reminderOne)));
        newReminders.add(new Reminder(Long.parseLong(reminderTwo)));
        newReminders.add(new Reminder(Long.parseLong(reminderThree)));
        newReminders.add(new Reminder(Long.parseLong(reminderFour)));

        final PatientReminderRequest request = new PatientReminderRequest(newReminders);

        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return patientService.savePatientReminders(patient.getId(), request);
            }
        }, new TaskCallback<Patient>() {

            @Override
            public void success(final Patient patient) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(
                                PatientPreferencesActivity.this,
                                "Saved reminders.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error saving new patient reminders.", e);
                Toast.makeText(
                        PatientPreferencesActivity.this,
                        "Could not save reminders.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_preferences, menu);
        menu.add(0, DASH_BUTTON, 0, R.string.menu_patient_profile);
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
        if (id == DASH_BUTTON) {
            goToPatientDashboard();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToPatientDashboard() {
        Intent intent = new Intent(this, PatientProfileActivity.class);
        intent.putExtra("PATIENT", patient);
        startActivity(intent);
    }
}
