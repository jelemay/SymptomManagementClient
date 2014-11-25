package com.coursera.symptommanagement.activities.patient;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.adapters.PainListAdapter;
import com.coursera.symptommanagement.models.Medication;
import com.coursera.symptommanagement.models.Pain;

import java.util.ArrayList;
import java.util.List;

public class PatientCheckInActivity extends Activity {

    private Spinner painSpinner;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_check_in);

        inflater = LayoutInflater.from(this);

        // spinner with all available pain designations
        painSpinner = (Spinner) findViewById(R.id.spinnerPain);
        ArrayList<Pain> painList = (ArrayList<Pain>) getPainList();

        painSpinner.setAdapter(new PainListAdapter(this, R.layout.item_pain, painList));

        // pain medication yes/no questions
        ArrayList<Medication> medList = (ArrayList<Medication>) getMedicationList();
        final TableLayout table = (TableLayout) findViewById(R.id.tableMedQuestions);

        for (Medication med : medList) {

            final View row = inflater.inflate(R.layout.item_medication_question, table, false);

            final TextView medName = (TextView) row.findViewById(R.id.txtMedName);
            medName.setText(med.getName() + "?");

            table.addView(row);
        }

    }

    public List<Pain> getPainList() {
        List<Pain> painList = new ArrayList<Pain>();
        painList.add(new Pain(1L, "Well-Controlled"));
        painList.add(new Pain(2L, "Moderate"));
        painList.add(new Pain(3L, "Severe"));
        return painList;
    }

    public List<Medication> getMedicationList() {
        List<Medication> medications = new ArrayList<Medication>();
        medications.add(new Medication(1L, "OxyContin"));
        medications.add(new Medication(2L, "Hydrocodone"));
        medications.add(new Medication(3L, "Percocet"));
        medications.add(new Medication(4L, "Vicodin"));
        medications.add(new Medication(5L, "Methadone"));
        return medications;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_check_in, menu);
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
