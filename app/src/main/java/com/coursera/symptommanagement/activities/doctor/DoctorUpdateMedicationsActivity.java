package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.adapters.MedicationListAdapter;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class DoctorUpdateMedicationsActivity extends Activity {

    public static final String ACTIVITY_NAME = "DoctorUpdateMedications Activity: ";

    private final String MEDICATION_TAG = "tableMedication";
    private final String MED_EXISTS_MESSAGE = "This medication has already been added.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update_medications);

        Intent intent = getIntent();
        final Patient patient = (Patient) intent.getSerializableExtra("PATIENT");

        // spinner with all available medications
        Spinner medicationSpinner = (Spinner) findViewById(R.id.spinnerMedications);
        ArrayList<Medication> medications = (ArrayList<Medication>) getMedicationList();

        medicationSpinner.setAdapter(new MedicationListAdapter(this, R.layout.item_medication,
                medications));

        medicationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // table containing medications assigned to patient
        ArrayList<Medication> patientMeds = (ArrayList<Medication>) patient.getMedications();
        final TableLayout medTable = (TableLayout) findViewById(R.id.updateMedicationsTable);
        medTable.setColumnStretchable(0, true);
        for (Medication med : patientMeds) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView tvMedication = new TextView(this);
            tvMedication.setTag(MEDICATION_TAG);
            tvMedication.setText(med.getName());
            tvMedication.setPadding(0,0,0,0);

            Button button = new Button(this);
            button.setText(R.string.button_delete_medication);
            button.setTextSize(12);
            button.setPadding(10,0,0,0);
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
    }

    public void addMedicationButtonClicked(View v) {
        Spinner medicationSpinner = (Spinner) findViewById(R.id.spinnerMedications);
        Medication medication = (Medication) medicationSpinner.getSelectedItem();
        String medName = medication.getName();

        Log.d(ACTIVITY_NAME, "Update medications spinner value: " + medName);

        final TableLayout medTable = (TableLayout) findViewById(R.id.updateMedicationsTable);
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

    public void savePatientMedications(View view) {
        Log.d(ACTIVITY_NAME, "Saving patient medications...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_update_medications, menu);
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
