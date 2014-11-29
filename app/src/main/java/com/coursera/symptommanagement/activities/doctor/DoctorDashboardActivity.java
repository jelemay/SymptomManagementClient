package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.fragments.DoctorDashboardFragment;
import com.coursera.symptommanagement.fragments.DoctorDashboardFragment.OnFragmentInteractionListener;
import com.coursera.symptommanagement.models.Doctor;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DoctorDashboardActivity extends Activity implements OnFragmentInteractionListener {

    public static final String ACTIVITY_NAME = "Doctor Dashboard Activity: ";

    private DoctorDashboardFragment doctorInfoFragment;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);

        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("DOCTOR");
        doctorInfoFragment = DoctorDashboardFragment.newInstance(doctor);

        String doctorName = doctor.getLastName() + ", " + doctor.getFirstName();
        TextView txtDoctorName = (TextView) findViewById(R.id.txtDoctorName);
        txtDoctorName.setText(doctorName);

        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.doctor_info, doctorInfoFragment);

        fragmentTransaction.commit();

        Log.d(ACTIVITY_NAME, "Finished onCreate()");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_dashboard, menu);
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

    public void onPatientSearchClicked(View v) {

    }

    public void onPatientListClicked(View v) {
        Intent patientListIntent = new Intent(this, DoctorPatientListActivity.class);
        patientListIntent.putExtra("DOCTOR", doctor);
        startActivity(patientListIntent);
    }

    public void onPatientAddClicked(View v) {
        Intent patientAddIntent = new Intent(this, DoctorPatientAddActivity.class);
        patientAddIntent.putExtra("DOCTOR", doctor);
        startActivity(patientAddIntent);
    }
}
