package com.coursera.symptommanagement.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.activities.doctor.DoctorDashboardActivity;
import com.coursera.symptommanagement.activities.patient.PatientProfileActivity;
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victorialemay on 10/10/14.
 */
public class LoginActivity extends Activity {

    public static final String ACTIVITY_NAME = "Login Activity: ";

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(ACTIVITY_NAME, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ACTIVITY_NAME, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ACTIVITY_NAME, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(ACTIVITY_NAME, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ACTIVITY_NAME, "The onDestroy() event");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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

    public void startUserLogin(View view) {
        // TODO: hardcode username and password, clientID, everything for credentials

        // TODO: create a login client

        // TODO: determine whether doctor or patient

        // TODO: add doctor client to startDoctorPath(), add patient client to startPatientPath()

        // TODO: if doctor, create doctor client and authenticate it, then call startDoctorPath()

        // TODO: if patient, create patient client and authenticate it, then call startPatientPath()

        //startDoctorPath(view);
        startPatientPath(view);
    }

    public void startDoctorPath(View view) {
        Intent doctorIntent = new Intent(this, DoctorDashboardActivity.class);
        startActivity(doctorIntent);
    }

    public void startPatientPath(View view) {
        // get patient from user info
        Patient patient = new Patient("1", "David", "Bowie", "145", getMedicationList());
        Doctor doctor = new Doctor("Freddy", "Mercury");
        patient.setDoctor(doctor);

        Intent patientIntent = new Intent(this, PatientProfileActivity.class);
        patientIntent.putExtra("PATIENT", patient);
        startActivity(patientIntent);
    }

    public List<Medication> getMedicationList() {
        List<Medication> medications = new ArrayList<Medication>();
        medications.add(new Medication(1L, "OxyContin"));
        medications.add(new Medication(2L, "Hydrocodone"));
        medications.add(new Medication(3L, "Percocet"));
        medications.add(new Medication(4L, "Oxycodone"));
        medications.add(new Medication(5L, "Vicodin"));
        medications.add(new Medication(6L, "Methadone"));
        medications.add(new Medication(7L, "Naproxen"));
        return medications;
    }

}
