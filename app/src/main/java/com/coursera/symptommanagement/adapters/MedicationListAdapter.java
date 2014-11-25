package com.coursera.symptommanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Medication;

import java.util.ArrayList;

/**
 * Created by victorialemay on 11/8/14.
 */
public class MedicationListAdapter extends ArrayAdapter<Medication> {

    private LayoutInflater inflater;


    public MedicationListAdapter(Context context, int txtViewResourceId,
                                 ArrayList<Medication> medications) {
        super(context, txtViewResourceId, medications);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View contentView, ViewGroup parent) {
        return getMedicationView(position, contentView, parent);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        return getMedicationView(position, contentView, parent);
    }

    public View getMedicationView(int position, View convertView, ViewGroup parent) {
        Medication medication = getItem(position);

        View medicationSpinner = inflater.inflate(R.layout.item_medication, parent, false);

        TextView tvName = (TextView) medicationSpinner.findViewById(R.id.medicationListName);
        tvName.setText(medication.getName());

        TextView tvId = (TextView) medicationSpinner.findViewById(R.id.medicationListId);
        tvId.setText(String.valueOf(medication.getId()));

        return medicationSpinner;
    }
}
