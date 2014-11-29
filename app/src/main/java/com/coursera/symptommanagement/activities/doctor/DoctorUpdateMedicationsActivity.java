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
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.models.PatientMedicationRequest;
import com.coursera.symptommanagement.services.DoctorServiceAPI;
import com.coursera.symptommanagement.services.PatientService;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class DoctorUpdateMedicationsActivity extends Activity {

    public static final String ACTIVITY_NAME = "DoctorUpdateMedications Activity: ";

    private final String MEDICATION_TAG = "tableMedication";
    private final String MED_EXISTS_MESSAGE = "This medication has already been added.";

    private static final int DASH_BUTTON = 0;

    private Patient patient;
    private Doctor doctor;
    private Spinner medicationSpinner;
    private List<Medication> medicationList = new ArrayList<Medication>();
    private Map<String,Medication> medicationMap = new HashMap<String, Medication>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_update_medications);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT");
        doctor = (Doctor) intent.getSerializableExtra("DOCTOR");

        // spinner with all available medications
        medicationSpinner = (Spinner) findViewById(R.id.spinnerMedications);

        // grab all medications from server
        getAllMedications();

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

    public void getAllMedications() {
        final DoctorServiceAPI doctorService = SvcStore.getDoctorService();

        CallableTask.invoke(new Callable<List<Medication>>() {
            @Override
            public List<Medication> call() throws Exception {
                return doctorService.getMedicationList(doctor.getId());
            }
        }, new TaskCallback<List<Medication>>() {
            @Override
            public void success(List<Medication> medications) {
                medicationList.addAll(medications);
                medicationMap = createMedicationMap(medications);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        medicationSpinner.setAdapter(new MedicationListAdapter(getBaseContext(),
                                R.layout.item_medication,
                                (ArrayList<Medication>) medicationList));
                    }
                });
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error pulling medication list from server.", e);
                Toast.makeText(
                        DoctorUpdateMedicationsActivity.this,
                        "Could not pull medications.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    public Map<String,Medication> createMedicationMap(List<Medication> medications) {
        Map<String,Medication> medMap = new HashMap<String,Medication>();
        for (Medication med : medications) {
            medMap.put(med.getName(), med);
        }
        return medMap;
    }


    public void savePatientMedications(View view) {
        Log.d(ACTIVITY_NAME, "Saving patient medications...");
        final PatientServiceAPI patientService = SvcStore.getPatientService();

        List<Medication> newMedList = new ArrayList<Medication>();

        final TableLayout medTable = (TableLayout) findViewById(R.id.updateMedicationsTable);
        for (int i = 0; i < medTable.getChildCount(); i++) {
            TableRow row = (TableRow) medTable.getChildAt(i);
            TextView foundMed = (TextView) row.findViewWithTag(MEDICATION_TAG);
            String medName = foundMed.getText().toString();
            newMedList.add(medicationMap.get(medName));
        }

        for (Medication m : newMedList) {
            Log.d(ACTIVITY_NAME, "Added medication " + m.getName() + " to new saved list.");
        }

        // create request
        final PatientMedicationRequest request = new PatientMedicationRequest(newMedList);

        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return patientService.savePatientMedications(patient.getId(), request);
            }
        }, new TaskCallback<Patient>() {
            @Override
            public void success(Patient patient) {

                Log.d(ACTIVITY_NAME, "Successfully saved patient " + patient.getFirstName()
                                            + " " + patient.getLastName());
                Toast.makeText(
                        DoctorUpdateMedicationsActivity.this,
                        "Saved medications.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DoctorPatientProfileActivity.class);
                patient.setDoctor(doctor);
                intent.putExtra("PATIENT", patient);
                startActivity(intent);
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error saving new medication list.", e);
                Toast.makeText(
                        DoctorUpdateMedicationsActivity.this,
                        "Could not save medications.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_update_medications, menu);
        menu.add(0, DASH_BUTTON, 0, R.string.menu_doctor_dashboard);
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
            goToDoctorDashboard();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToDoctorDashboard() {
        Intent intent = new Intent(this, DoctorDashboardActivity.class);
        intent.putExtra("DOCTOR", doctor);
        startActivity(intent);
    }
}
