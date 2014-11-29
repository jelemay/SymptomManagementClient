package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.content.Intent;
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
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.models.PatientRequest;
import com.coursera.symptommanagement.services.DoctorServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class DoctorPatientAddActivity extends Activity {

    public static final String ACTIVITY_NAME = "Doctor Patient Add Activity: ";
    private final String MEDICATION_TAG = "tableMedication";
    private final String MED_EXISTS_MESSAGE = "This medication has already been added.";

    private Doctor doctor;
    private Spinner medicationSpinner;
    private List<Medication> medicationList = new ArrayList<Medication>();
    private Map<String,Medication> medicationMap = new HashMap<String, Medication>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_add);

        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("DOCTOR");

        // spinner with all available medications
        medicationSpinner = (Spinner) findViewById(R.id.spinnerAddMedications);

        // get all medications from server
        getAllMedications();

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
                        DoctorPatientAddActivity.this,
                        "Could not get medication list.",
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

    public void saveNewPatient(View v) {
        TextView tvPatientFirstName = (TextView) findViewById(R.id.patientFirstName);
        TextView tvPatientLastName = (TextView) findViewById(R.id.patientLastName);
        TextView tvPatientUsername = (TextView) findViewById(R.id.patientUsername);
        TextView tvPatientPassword = (TextView) findViewById(R.id.patientPassword);
        TextView tvPatientMedRecId = (TextView) findViewById(R.id.patientMedicalRecordId);

        String patFirstName = tvPatientFirstName.getText().toString();
        String patLastName = tvPatientLastName.getText().toString();
        String patUsername = tvPatientUsername.getText().toString();
        String patPassword = tvPatientPassword.getText().toString();
        Long patMedRecId = Long.parseLong(tvPatientMedRecId.getText().toString());

        Log.d(ACTIVITY_NAME, "New Patient: " + patFirstName + " " + patLastName + " " +
                                patUsername + " " + patPassword + " " + patMedRecId);

        List<Medication> medList = new ArrayList<Medication>();

        final TableLayout medTable = (TableLayout) findViewById(R.id.addMedicationsTable);
        for (int i = 0; i < medTable.getChildCount(); i++) {
            TableRow row = (TableRow) medTable.getChildAt(i);
            TextView foundMed = (TextView) row.findViewWithTag(MEDICATION_TAG);
            String foundMedName = foundMed.getText().toString();
            medList.add(medicationMap.get(foundMedName));
            Log.d(ACTIVITY_NAME, "Found medication " + foundMedName);
        }

        final DoctorServiceAPI doctorService = SvcStore.getDoctorService();
        final PatientRequest request = new PatientRequest();
        request.setFirstName(patFirstName);
        request.setLastName(patLastName);
        request.setUsername(patUsername);
        request.setPassword(patPassword);
        request.setMedicalRecordId(patMedRecId);
        request.setMedications(medList);

        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return doctorService.addPatientToDoctor(doctor.getId(), request);
            }
        }, new TaskCallback<Patient>() {
            @Override
            public void success(Patient medications) {
                Toast.makeText(
                        DoctorPatientAddActivity.this,
                        "Saved patient.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DoctorPatientListActivity.class);
                intent.putExtra("DOCTOR", doctor);
                startActivity(intent);
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error saving new patient.", e);
                Toast.makeText(
                        DoctorPatientAddActivity.this,
                        "Could not save patient.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);

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
