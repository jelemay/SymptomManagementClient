package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.adapters.MedicationListAdapter;
import com.coursera.symptommanagement.models.Medication;

import java.util.ArrayList;
import java.util.List;

public class DoctorPatientAddActivity extends Activity {

    public static final String ACTIVITY_NAME = "Doctor Patient Add Activity: ";
    private final String MEDICATION_TAG = "tableMedication";
    private final String MED_EXISTS_MESSAGE = "This medication has already been added.";

    private Spinner medicationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_add);

        // spinner with all available medications
        medicationSpinner = (Spinner) findViewById(R.id.spinnerAddMedications);
        ArrayList<Medication> medications = (ArrayList<Medication>) getMedicationList();

        medicationSpinner.setAdapter(new MedicationListAdapter(this, R.layout.item_medication,
                medications));

        // handle outer/inner scroll views (whole page and medication table)
        final ScrollView outer = (ScrollView) findViewById(R.id.scrollViewWholePage);
        final ScrollView inner = (ScrollView) findViewById(R.id.scrollViewMedications);

        outer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                inner.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        inner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Log.d(ACTIVITY_NAME, "Finished onCreate()");
    }

    public void addMedicationToTable(View view) {

        // get medication from spinner
        Spinner medicationSpinner = (Spinner) findViewById(R.id.spinnerAddMedications);
        Medication medication = (Medication) medicationSpinner.getSelectedItem();
        String medName = medication.getName();

        Log.d(ACTIVITY_NAME, "Update medications spinner value: " + medName);

        // add medication to table
        final TableLayout medTable = (TableLayout) findViewById(R.id.addMedicationsTable);
        medTable.setColumnStretchable(0, true);
        Boolean medPresent = false;

        // check that medication is not already in table
        for (int i = 0; i < medTable.getChildCount(); i++) {
            TableRow row = (TableRow) medTable.getChildAt(i);
            TextView existingMedication = (TextView) row.findViewWithTag(MEDICATION_TAG);
            String existingMedName = existingMedication.getText().toString();
            if (existingMedName.equals(medName)) {
                medPresent = true;
            }
        }

        // if medication not in table, add it
        if (!medPresent) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tvMedication = new TextView(this);
            tvMedication.setTag(MEDICATION_TAG);
            tvMedication.setText(medication.getName());
            tvMedication.setPadding(0, 0, 0, 0);

            Button button = new Button(this);
            button.setText(R.string.button_delete_medication);
            button.setTextSize(12);
            button.setPadding(10, 0, 0, 0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final TableRow parent = (TableRow) view.getParent();
                    medTable.removeView(parent);
                }
            });

            row.addView(tvMedication);
            row.addView(button);
            medTable.addView(row);
        }
        else {
            Toast toast = Toast.makeText(this, MED_EXISTS_MESSAGE, Toast.LENGTH_SHORT);
            toast.show();
        }

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
        getMenuInflater().inflate(R.menu.doctor_patient_add, menu);
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
