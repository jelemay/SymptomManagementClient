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
import com.coursera.symptommanagement.task.UserCreateTest;

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

    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        //   Log.d(ACTIVITY_NAME, "The onStart() event");
    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();
        //  Log.d(ACTIVITY_NAME, "The onResume() event");
    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        //   Log.d(ACTIVITY_NAME, "The onPause() event");
    }

    /**
     * Called when the activity is no longer visible.
     */
    @Override
    protected void onStop() {
        super.onStop();
        //  Log.d(ACTIVITY_NAME, "The onStop() event");
    }

    /**
     * Called just before the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Log.d(ACTIVITY_NAME, "The onDestroy() event");
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
        final String USERNAME = "alemay";
        final String PASSWORD = "password";
        final String CLIENT_ID = "mobile";
        // This is the emulator's version of localhost
        //final String SERVER = "https://10.0.2.2:8443";
        final String SERVER = "https://192.168.1.132:8443";

        // softcode the username and password
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
                // OAuth 2.0 grant was successful and we
                // can talk to the server
                UserMedical resultIn = result;

                if (resultIn.getRole().contentEquals("DOCTOR")) {
                    System.out.println(" going down the Doctor path");

                    DoctorServiceAPI doctorSvc = DoctorService.init(SERVER, username, password);
                    SvcStore.setDoctorService(doctorSvc);

                    startDoctorPath(resultIn);
                } else {
                    System.out.println(" going down the Patient path");
                    PatientServiceAPI patientSvc = PatientService.init(SERVER, username, password);
                    SvcStore.setPatientService(patientSvc);

                    startPatientPath(resultIn);
                }

            }

            @Override
            public void error(Exception e) {
                Log.e(LoginActivity.class.getName(), "Error logging in via OAuth.", e);

                Toast.makeText(
                        LoginActivity.this,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void startDoctorPath(UserMedical user) {
        System.out.println(" Got the users");
        System.out.println(user.getUsername());
        System.out.println(user.getRole());
        System.out.println(user.getRoleId());

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
                // OAuth 2.0 grant was successful and we
                // can talk to the server, open up the video listing
                Doctor resultIn = result;

                Intent doctorIntent = new Intent(LoginActivity.this, DoctorDashboardActivity.class);
                doctorIntent.putExtra("Doctor", resultIn);

                //Remove below  - test of createUser
                //UserCreateTest.create();

                startActivity(doctorIntent);


            }

            @Override
            public void error(Exception e) {
                Log.e(LoginActivity.class.getName(), "Error logging in via OAuth.", e);

                Toast.makeText(
                        LoginActivity.this,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        });

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
                // OAuth 2.0 grant was successful and we
                // can talk to the server, open up the video listing
                Patient resultIn = result;

                Intent patientIntent = new Intent(LoginActivity.this, PatientProfileActivity.class);
                patientIntent.putExtra("PATIENT", resultIn);
                System.out.println(" about to start the patient intent");
                System.out.println(" about to start the patient intent");
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
        });







        // get patient from user info
        // Patient patient = new Patient("1", "David", "Bowie", "145", getMedicationList());

        // Doctor doctor = new Doctor("Freddy", "Mercury");
        //patient.setDoctor(doctor);

        // Intent patientIntent = new Intent(this, PatientProfileActivity.class);
        // patientIntent.putExtra("PATIENT", patient);
        //  startActivity(patientIntent);
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
