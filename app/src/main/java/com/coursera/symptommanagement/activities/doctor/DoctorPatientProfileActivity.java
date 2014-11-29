package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class DoctorPatientProfileActivity extends Activity {

    private static final String ACTIVITY_NAME = "Doctor Patient Profile Activity: ";

    private Button btnUpdateMedications;
    private Button btnViewPain;
    private Button btnViewAppetite;
    private Doctor doctor;
    private Patient patient;
    private List<Medication> medicationList = new ArrayList<Medication>();
    private Map<Long,Medication> medicationMap = new HashMap<Long,Medication>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_profile);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT");
        doctor = patient.getDoctor();

        // pull medications from server
        getMedicationList(patient);

        // add medications to patient
        patient.setMedications(medicationList);

        Log.d(ACTIVITY_NAME, "Entered patient profile: " + patient.getFirstName() + " " +
                                patient.getLastName() + " with Doctor: " + doctor.getFirstName()
                                + " " + doctor.getLastName());

        // text fields (name and medicalRecordId)
        TextView tvFirstName = (TextView) findViewById(R.id.patientProfileFirstName);
        tvFirstName.setText(patient.getFirstName());

        TextView tvLastName = (TextView) findViewById(R.id.patientProfileLastName);
        tvLastName.setText(patient.getLastName());

        TextView tvMedicalRecordId = (TextView) findViewById(R.id.patientProfileMedicalRecordId);
        tvMedicalRecordId.setText(patient.getMedicalRecordId());

        // buttons
        btnUpdateMedications = (Button) findViewById(R.id.buttonPatientProfileUpdateMedication);
        btnUpdateMedications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medIntent = new Intent(getApplicationContext(), DoctorUpdateMedicationsActivity.class);
                medIntent.putExtra("PATIENT", patient);
                medIntent.putExtra("DOCTOR", doctor);
                startActivity(medIntent);
            }
        });

        btnViewPain = (Button) findViewById(R.id.buttonPatientPain);
        btnViewPain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnViewAppetite = (Button) findViewById(R.id.buttonPatientAppetite);
        btnViewAppetite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void getMedicationList(final Patient patient) {
        final PatientServiceAPI patientService = SvcStore.getPatientService();

        CallableTask.invoke(new Callable<List<Medication>>() {
            @Override
            public List<Medication> call() throws Exception {
                return patientService.getPatientMedications(patient.getId());
            }
        }, new TaskCallback<List<Medication>>() {

            @Override
            public void success(List<Medication> medications) {
                medicationMap = createMedicationMap(medications);
                medicationList.addAll(medications);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUpTableView(getBaseContext());
                    }
                });
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error pulling medication list from patient.", e);
                Toast.makeText(
                        DoctorPatientProfileActivity.this,
                        "Could not pull patient profile.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    public Map<Long,Medication> createMedicationMap(List<Medication> medications) {
        Map<Long,Medication> medMap = new HashMap<Long,Medication>();
        for (Medication med : medications) {
            medMap.put(med.getId(), med);
        }
        return medMap;
    }

    public void setUpTableView(Context context) {
        // medication table
        TableLayout medTable = (TableLayout) findViewById(R.id.patientProfileMedicationTable);
        for (Medication medication : medicationList) {
            Log.d(ACTIVITY_NAME, "Found medication " + medication.getName());
            TableRow row = new TableRow(context);
            row.setLayoutParams(
                    new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView tvMedication = new TextView(context);
            tvMedication.setTextColor(Color.BLACK);
            tvMedication.setText(medication.getName());
            row.addView(tvMedication);
            medTable.addView(row,
                    new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_patient_profile, menu);
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
