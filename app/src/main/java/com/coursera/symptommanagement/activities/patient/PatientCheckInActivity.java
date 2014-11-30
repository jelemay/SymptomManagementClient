package com.coursera.symptommanagement.activities.patient;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.adapters.AppetiteListAdapter;
import com.coursera.symptommanagement.adapters.PainListAdapter;
import com.coursera.symptommanagement.fragments.DatePickerFragment;
import com.coursera.symptommanagement.fragments.TimePickerFragment;
import com.coursera.symptommanagement.models.Appetite;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Pain;
import com.coursera.symptommanagement.models.Patient;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientCheckInActivity extends Activity
        implements TimePickerFragment.TimePickerFragmentListener,
                    DatePickerFragment.DatePickerFragmentListener {

    public static final String ACTIVITY_NAME = "Patient Check In Activity: ";

    private static final int DASH_BUTTON = 0;

    private Patient patient;

    private Spinner painSpinner;
    private Spinner appetiteSpinner;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_check_in);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT");

        inflater = LayoutInflater.from(this);

        // spinner with all available pain designations
        painSpinner = (Spinner) findViewById(R.id.spinnerPain);
        ArrayList<Pain> painList = (ArrayList<Pain>) getPainList();

        painSpinner.setAdapter(new PainListAdapter(this, R.layout.item_pain, painList));

        // spinner with all available appetite designations
        appetiteSpinner = (Spinner) findViewById(R.id.spinnerAppetite);
        ArrayList<Appetite> appetiteList = (ArrayList<Appetite>) getAppetiteList();

        appetiteSpinner.setAdapter(new AppetiteListAdapter(this, R.layout.item_appetite,
                                    appetiteList));

        // set pain question
        TextView painTextQuestion = (TextView) findViewById(R.id.painQuestion);
        painTextQuestion.setText("How bad is your mouth pain/sore throat?");

        // set appetite question
        TextView appTextQuestion = (TextView) findViewById(R.id.appetiteQuestion);
        appTextQuestion.setText("Does your pain stop you from eating/drinking?");

        // pain medication yes/no questions
        ArrayList<Medication> medList = (ArrayList<Medication>) getMedicationList();
        final TableLayout table = (TableLayout) findViewById(R.id.tableMedQuestions);

        for (Medication med : medList) {

            final View row = inflater.inflate(R.layout.item_medication_question, table, false);

            final TextView medName = (TextView) row.findViewById(R.id.txtMedName);
            medName.setText(med.getName() + "?");

            final LinearLayout medTimeDate = (LinearLayout) row.findViewById(R.id.medDateTimeLayout);
            medTimeDate.setTag("MedTimeDate" + medName);
            medTimeDate.setVisibility(View.GONE);

            // assign IDs for date/time fields to keep them unique
            final TextView txtTime = (TextView) medTimeDate.findViewById(R.id.txtTime);
            txtTime.setTag("txtTime" + med.getName());
            final Button btnTime = (Button) medTimeDate.findViewById(R.id.btnTime);
            btnTime.setTag("btnTime" + med.getName());
            btnTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTimePickerDialog(view);
                }
            });

            final TextView txtDate = (TextView) medTimeDate.findViewById(R.id.txtDate);
            txtDate.setTag("txtDate" + med.getName());
            final Button btnDate = (Button) medTimeDate.findViewById(R.id.btnDate);
            btnDate.setTag("btnDate" + med.getName());
            btnDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDatePickerDialog(view);
                }
            });

            final RadioGroup radioGroup = (RadioGroup) row.findViewById(R.id.radGroupMed);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    if (checkedId == R.id.radMedYes) {
                        medTimeDate.setVisibility(View.VISIBLE);
                    }
                    else if (checkedId == R.id.radMedNo) {
                        medTimeDate.setVisibility(View.GONE);
                    }
                }
            });

            table.addView(row);
        }

    }

    public void showTimePickerDialog(View v) {
        DialogFragment timePicker = TimePickerFragment.newInstance(v);
        timePicker.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment datePicker = DatePickerFragment.newInstance(v);
        datePicker.show(getFragmentManager(), "datePicker");
    }

    public String getTimeString(String buttonTag, int hourOfDay, int minute) {
        String am_pm = "";

        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
        time.set(Calendar.MINUTE, minute);

        if (time.get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
        } else {
            am_pm = "PM";
        }

        String hourString = (time.get(Calendar.HOUR) == 0) ? "12" : time.get(Calendar.HOUR) + "";
        String minuteString = (time.get(Calendar.MINUTE) == 0) ? "00" : time.get(Calendar.MINUTE) + "";

        Log.d(ACTIVITY_NAME, "Minute String is: " + minuteString + " Length is: " + minuteString.length());

        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
            Log.d(ACTIVITY_NAME, "New Minute String is: " + minuteString + " Length is: " + minuteString.length());
        }

        String timeString = hourString + ":" + minuteString + " " + am_pm;

        String medication = buttonTag.replace("btnTime", "");
        String txtTag = "txtTime" + medication;

        final TableLayout table = (TableLayout) findViewById(R.id.tableMedQuestions);
        TextView txtTime = (TextView) table.findViewWithTag(txtTag);
        txtTime.setTextSize(12);
        txtTime.setText(timeString);

        return timeString;
    }

    public String getDateString(String btnTag, int year, int month, int day) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, day);

        String dateString = (date.get(Calendar.MONTH) + 1) + "/" +
                                date.get(Calendar.DAY_OF_MONTH) +
                                "/" + date.get(Calendar.YEAR);

        String medication = btnTag.replace("btnDate", "");
        String txtTag = "txtDate" + medication;

        final TableLayout table = (TableLayout) findViewById(R.id.tableMedQuestions);
        TextView txtDate = (TextView) table.findViewWithTag(txtTag);
        txtDate.setTextSize(12);
        txtDate.setText(dateString);

        return dateString;
    }

    public void getTimeString(int buttonId, int hourOfDay, int minute) {}

    public String getDateString(int buttonId, int year, int month, int day) {
        return null;
    }

    public List<Pain> getPainList() {
        List<Pain> painList = new ArrayList<Pain>();
        painList.add(new Pain(1L, "Well-Controlled"));
        painList.add(new Pain(2L, "Moderate"));
        painList.add(new Pain(3L, "Severe"));
        return painList;
    }

    public List<Appetite> getAppetiteList() {
        List<Appetite> appetiteList = new ArrayList<Appetite>();
        appetiteList.add(new Appetite(1L, "No"));
        appetiteList.add(new Appetite(2L, "Some"));
        appetiteList.add(new Appetite(3L, "I Can't Eat"));
        return appetiteList;
    }

    public List<Medication> getMedicationList() {
        List<Medication> medications = new ArrayList<Medication>();
        medications.add(new Medication(1L, "OxyContin"));
        medications.add(new Medication(2L, "Hydrocodone"));
        medications.add(new Medication(3L, "Percocet"));
        medications.add(new Medication(4L, "Vicodin"));
        medications.add(new Medication(5L, "Methadone"));
        return medications;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_check_in, menu);
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
