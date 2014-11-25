package com.coursera.symptommanagement.activities.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.adapters.PatientListAdapter;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorPatientListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_doctor_patient_list);

        // get data
        ArrayList<Patient> patientList = (ArrayList<Patient>) getPatientList();
        final HashMap<String,Patient> patientMap =
                (HashMap<String,Patient>) createPatientMap(patientList);

        // create the adapter to convert the array to views
        PatientListAdapter adapter = new PatientListAdapter(this, patientList);

        // attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.patientListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.patientList_id);
                String patientId = tv.getText().toString();
                Patient patient = patientMap.get(patientId);

                Intent intent = new Intent(getApplicationContext(), DoctorPatientProfileActivity.class);
                intent.putExtra("PATIENT", patient);
                startActivity(intent);
            }
        });

    }


    public List<Patient> getPatientList() {
        List<Patient> patients = new ArrayList<Patient>();
        patients.add(new Patient("1", "David", "Bowie", "145", getMedicationList()));
        patients.add(new Patient("2", "Freddy", "Mercury", "234", getMedicationList()));
        patients.add(new Patient("3", "Karen", "O", "456", getMedicationList()));
        return patients;
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

    public Map<String,Patient> createPatientMap(List<Patient> patientList) {
        Map<String,Patient> patientMap = new HashMap<String, Patient>();
        for (Patient p : patientList) {
            String id = p.getId();
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
