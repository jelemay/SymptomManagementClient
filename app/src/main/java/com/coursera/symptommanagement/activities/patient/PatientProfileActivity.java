package com.coursera.symptommanagement.activities.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.activities.doctor.DoctorDashboardActivity;
import com.coursera.symptommanagement.activities.doctor.DoctorPatientProfileActivity;
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PatientProfileActivity extends Activity {

    private Patient patient;
    private Doctor doctor;

    private static final String ACTIVITY_NAME = "Patient Profile Activity: ";

    private static final int DASH_BUTTON = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("PATIENT");

        // get doctor from server
        getDoctorFromPatient(patient);

        // text fields (name and medicalRecordId)
        TextView tvFirstName = (TextView) findViewById(R.id.patientProfileFirstName);
        tvFirstName.setText(patient.getFirstName());

        TextView tvLastName = (TextView) findViewById(R.id.patientProfileLastName);
        tvLastName.setText(patient.getLastName());

        TextView tvMedicalRecordId = (TextView) findViewById(R.id.patientProfileMedicalRecordId);
        tvMedicalRecordId.setText(patient.getMedicalRecordId());

        // medication table
        ArrayList<Medication> medications = (ArrayList<Medication>) patient.getMedications();
        TableLayout medTable = (TableLayout) findViewById(R.id.patientProfileMedicationTable);
        for (Medication medication : medications) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView tvMedication = new TextView(this);
            tvMedication.setText(medication.getName());
            row.addView(tvMedication);
            medTable.addView(row,
                    new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // buttons
        Button btnCheckIn = (Button) findViewById(R.id.buttonCheckIn);
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientCheckInActivity.class);
                intent.putExtra("PATIENT", patient);
                startActivity(intent);
            }
        });

        Button btnUpdatePreferences = (Button) findViewById(R.id.buttonUpdatePreferences);
        btnUpdatePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PatientPreferencesActivity.class);
                intent.putExtra("PATIENT", patient);
                startActivity(intent);
            }
        });

    }

    public void getDoctorFromPatient(final Patient patient) {
        final PatientServiceAPI patientService = SvcStore.getPatientService();

        CallableTask.invoke(new Callable<Doctor>() {
            @Override
            public Doctor call() throws Exception {
                return patientService.getDoctorFromPatient(patient.getId());
            }
        }, new TaskCallback<Doctor>() {

            @Override
            public void success(Doctor doctor1) {
                doctor = doctor1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvDocFirstName = (TextView) findViewById(R.id.patProfileDoctorFirstName);
                        tvDocFirstName.setText(doctor.getFirstName());

                        TextView tvDocLastName = (TextView) findViewById(R.id.patProfileDoctorLastName);
                        tvDocLastName.setText(doctor.getLastName());
                    }
                });
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error pulling doctor from patient.", e);
                Toast.makeText(
                        PatientProfileActivity.this,
                        "Could not pull doctor information.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_profile, menu);
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
