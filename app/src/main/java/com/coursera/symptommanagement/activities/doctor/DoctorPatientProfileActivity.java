package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;

import java.util.ArrayList;
import java.util.List;

public class DoctorPatientProfileActivity extends Activity {

    private Button btnUpdateMedications;
    private Button btnViewPain;
    private Button btnViewAppetite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_profile);

        Intent intent = getIntent();
        final Patient patient = (Patient) intent.getSerializableExtra("PATIENT");

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
                    new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView tvMedication = new TextView(this);
            tvMedication.setText(medication.getName());
            row.addView(tvMedication);
            medTable.addView(row,
                    new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }

        // buttons
        btnUpdateMedications = (Button) findViewById(R.id.buttonPatientProfileUpdateMedication);
        btnUpdateMedications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medIntent = new Intent(getApplicationContext(), DoctorUpdateMedicationsActivity.class);
                medIntent.putExtra("PATIENT", patient);
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

    public List<String> getMedicationList() {
        List<String> medications = new ArrayList<String>();
        medications.add("Oxycontin");
        medications.add("Morphine");
        medications.add("Advil");
        //medications.add("Tylenol");
        //medications.add("Seroquel");
        //medications.add("Lithium");
        //medications.add("Levothyroxine");
        return medications;
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
