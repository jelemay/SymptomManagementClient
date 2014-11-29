package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.activities.LoginActivity;
import com.coursera.symptommanagement.adapters.PatientListAdapter;
import com.coursera.symptommanagement.models.Doctor;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;
import com.coursera.symptommanagement.services.DoctorServiceAPI;
import com.coursera.symptommanagement.services.PatientServiceAPI;
import com.coursera.symptommanagement.task.CallableTask;
import com.coursera.symptommanagement.task.SvcStore;
import com.coursera.symptommanagement.task.TaskCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class DoctorPatientListActivity extends Activity {

    private static final String ACTIVITY_NAME = "Doctor Patient List Activity: ";

    private Doctor doctor;
    private ListView listView;
    private List<Patient> patientList = new ArrayList<Patient>();
    private Map<Long, Patient> patientMap = new HashMap<Long,Patient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_list);

        Intent intent = getIntent();
        doctor = (Doctor) intent.getSerializableExtra("DOCTOR");

        Log.d(ACTIVITY_NAME, "Entered with doctor " + doctor.toString());

        getPatientList(doctor);

//        // create the adapter to convert the array to views
//        PatientListAdapter adapter = new PatientListAdapter(this, (ArrayList<Patient>)patientList);
//
//        // attach the adapter to a ListView
//        ListView listView = (ListView) findViewById(R.id.patientListView);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView tv = (TextView) view.findViewById(R.id.patientList_id);
//                String patientId = tv.getText().toString();
//                Patient patient = patientMap.get(Long.parseLong(patientId));
//
//                Intent intent = new Intent(getApplicationContext(), DoctorPatientProfileActivity.class);
//                intent.putExtra("PATIENT", patient);
//                startActivity(intent);
//            }
//        });

    }

    public void getPatientList(final Doctor doctor) {
        final DoctorServiceAPI doctorService = SvcStore.getDoctorService();

        CallableTask.invoke(new Callable<List<Patient>>() {
            @Override
            public List<Patient> call() throws Exception {
                return doctorService.getPatientList(doctor.getId());
            }
        }, new TaskCallback<List<Patient>>() {

            @Override
            public void success(List<Patient> patients) {
                patientList.addAll(patients);
                patientMap = createPatientMap(patients);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // create the adapter to convert the array to views
                        PatientListAdapter adapter = new PatientListAdapter(getBaseContext(),
                                (ArrayList<Patient>) patientList);

                        // attach the adapter to a ListView
                        listView = (ListView) findViewById(R.id.patientListView);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                TextView tv = (TextView) view.findViewById(R.id.patientList_id);
                                tv.setTextColor(Color.BLACK);
                                String patientId = tv.getText().toString();
                                Patient patient = patientMap.get(Long.parseLong(patientId));

                                Intent intent = new Intent(getApplicationContext(), DoctorPatientProfileActivity.class);
                                intent.putExtra("PATIENT", patient);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void error(Exception e) {
                Log.e(ACTIVITY_NAME, "Error pulling patient list from doctor.", e);
                Toast.makeText(
                        DoctorPatientListActivity.this,
                        "Could not pull patient list.",
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    public Map<Long,Patient> createPatientMap(List<Patient> patientList) {
        Map<Long,Patient> patientMap = new HashMap<Long, Patient>();
        for (Patient p : patientList) {
            Log.d(ACTIVITY_NAME, "Added patient " + p.getFirstName() + " " + p.getLastName() +
                        " to patient list.");
            p.setDoctor(doctor);
            Long id = p.getId();
            patientMap.put(id, p);
        }
        return patientMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_patient_list, menu);
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
