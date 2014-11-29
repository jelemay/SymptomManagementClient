package com.coursera.symptommanagement.adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Patient;

import java.util.ArrayList;

/**
 * Created by victorialemay on 11/7/14.
 */
public class PatientListAdapter extends ArrayAdapter<Patient> {

    private static final String ADAPTER_NAME = "Patient List Adapter: ";

    private LayoutInflater inflater;

    // View lookup cache
    private static class ViewHolder {
        TextView lastName;
        TextView firstName;
        TextView id;
    }

    public PatientListAdapter(Context context, ArrayList<Patient> patients) {
        super(context, R.layout.item_patient, patients);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Patient patient = getItem(position);

        Log.d(ADAPTER_NAME, "Found patient " + patient.getFirstName() + " " + patient.getLastName());

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;  // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();
            //inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_patient, null);
            viewHolder.lastName = (TextView) convertView.findViewById(R.id.patientList_lastName);
            viewHolder.firstName = (TextView) convertView.findViewById(R.id.patientList_firstName);
            viewHolder.id = (TextView) convertView.findViewById(R.id.patientList_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.lastName.setText(patient.getLastName());
        viewHolder.firstName.setText(patient.getFirstName());
        viewHolder.id.setText(String.valueOf(patient.getId()));

        return convertView;
    }


}
