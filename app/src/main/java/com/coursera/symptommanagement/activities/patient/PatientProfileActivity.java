package com.coursera.symptommanagement.activities.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;

import java.util.ArrayList;

public class PatientProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent intent = getIntent();
        final Patient patient = (Patient) intent.getSerializableExtra("PATIENT");
        final Doctor doctor = patient.getDoctor();

        // text fields (name and medicalRecordId)
        TextView tvFirstName = (TextView) findViewById(R.id.patientProfileFirstName);
        tvFirstName.setText(patient.getFirstName());

        TextView tvLastName = (TextView) findViewById(R.id.patientProfileLastName);
        tvLastName.setText(patient.getLastName());

        TextView tvDocFirstName = (TextView) findViewById(R.id.patProfileDoctorFirstName);
        tvDocFirstName.setText(doctor.getFirstName());

        TextView tvDocLastName = (TextView) findViewById(R.id.patProfileDoctorLastName);
        tvDocLastName.setText(doctor.getLastName());

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_profile, menu);
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
