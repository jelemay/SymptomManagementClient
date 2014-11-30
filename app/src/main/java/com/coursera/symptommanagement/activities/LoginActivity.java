package com.coursera.symptommanagement.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.activities.doctor.DoctorDashboardActivity;
import com.coursera.symptommanagement.activities.patient.PatientProfileActivity;
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.models.UserMedical;
import com.coursera.symptommanagement.services.DoctorService;
import com.coursera.symptommanagement.services.DoctorServiceAPI;
import com.coursera.symptommanagement.services.PatientService;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.services.UserService;
import com.coursera.symptommanagement.services.UserServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * Created by victorialemay on 10/10/14.
 */
public class LoginActivity extends Activity {

    public static final String ACTIVITY_NAME = "Login Activity: ";

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText doctorLoginPassword = (EditText) findViewById(R.id.doctorLoginPassword);
        EditText doctorLoginUsername = (EditText) findViewById(R.id.doctorLoginUsername);

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

        // This is the emulator's version of localhost
        final String SERVER = "https://10.0.2.2:8443";
//        final String SERVER = "https://192.168.1.132:8443";

        // get the username and password
        EditText doctorLoginPassword = (EditText) findViewById(R.id.doctorLoginPassword);
        EditText doctorLoginUsername = (EditText) findViewById(R.id.doctorLoginUsername);

        final String username = doctorLoginUsername.getText().toString();
        final String password = doctorLoginPassword.getText().toString();

        // create a login client
        final UserServiceAPI userSvc = UserService.init(SERVER, username, password);
        SvcStore.setUserService(userSvc);

        CallableTask.invoke(new Callable<UserMedical>() {
            @Override
            public UserMedical call() throws Exception {
                return userSvc.findByUsername(username);
            }
        }, new TaskCallback<UserMedical>() {
            @Override
            public void success(UserMedical result) {

                UserMedical resultIn = result;

                if (resultIn.getRole().contentEquals("DOCTOR")) {
                    Log.d(ACTIVITY_NAME, " going down the Doctor path");

                    DoctorServiceAPI doctorSvc = DoctorService.init(SERVER, username, password);
                    PatientServiceAPI patientSvc = PatientService.init(SERVER, username, password);
                    SvcStore.setDoctorService(doctorSvc);
                    SvcStore.setPatientService(patientSvc);

                    startDoctorPath(resultIn);
                } else {
                    Log.d(ACTIVITY_NAME, " going down the Patient path");

                    DoctorServiceAPI doctorSvc = DoctorService.init(SERVER, username, password);
                    PatientServiceAPI patientSvc = PatientService.init(SERVER, username, password);
                    SvcStore.setDoctorService(doctorSvc);
                    SvcStore.setPatientService(patientSvc);

                    startPatientPath(resultIn);
                }
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error logging in via OAuth.", e);

                Toast.makeText(
                        LoginActivity.this,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }


    public void startDoctorPath(UserMedical user) {
        Log.d(ACTIVITY_NAME, " Got the users");
        Log.d(ACTIVITY_NAME, user.getUsername());
        Log.d(ACTIVITY_NAME, user.getRole());
        Log.d(ACTIVITY_NAME, String.valueOf(user.getRoleId()));

        // use doctor role id to find the doctor from the network database
        //---------------------------------------------------------------

        final UserMedical usr = user;

        final DoctorServiceAPI doctorSvc = SvcStore.getDoctorService();

        CallableTask.invoke(new Callable<Doctor>() {
            @Override
            public Doctor call() throws Exception {
                return doctorSvc.getDoctorByUsername(usr.getUsername());
            }
        }, new TaskCallback<Doctor>() {

            @Override
            public void success(Doctor result) {

                Doctor resultIn = result;

                Intent doctorIntent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                doctorIntent.putExtra("DOCTOR", resultIn);

                startActivity(doctorIntent);
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error logging in via OAuth.", e);

                Toast.makeText(
                        LoginActivity.this,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);

    }

    public void startPatientPath(UserMedical user) {

        final UserMedical usr = user;

        final PatientServiceAPI patientSvc = SvcStore.getPatientService();

        CallableTask.invoke(new Callable<Patient>() {
            @Override
            public Patient call() throws Exception {
                return patientSvc.getPatientByUsername(usr.getUsername());
            }
        }, new TaskCallback<Patient>() {

            @Override
            public void success(Patient result) {

                Patient resultIn = result;
                //Doctor doctor = result.getDoctor();
                Log.d(ACTIVITY_NAME, "Retrieved patient: " + resultIn.toString());
                //Log.d(ACTIVITY_NAME, "Retrieved doctor: " + doctor.toString());

                Intent patientIntent = new Intent(LoginActivity.this, PatientProfileActivity.class);
                patientIntent.putExtra("PATIENT", resultIn);
                Log.d(ACTIVITY_NAME, " about to start the patient intent");
                startActivity(patientIntent);
            }

            @Override
            public void error(Exception e) {
                Log.e(LoginActivity.class.getName(), "Error logging in via OAuth.", e);

                Toast.makeText(
                        LoginActivity.this,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

}
