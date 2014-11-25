package com.coursera.symptommanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Pain;

import java.util.ArrayList;

/**
 * Created by victorialemay on 11/23/14.
 */
public class PainListAdapter extends ArrayAdapter<Pain> {

    private LayoutInflater inflater;

    public PainListAdapter(Context context, int txtViewResourceId,
                           ArrayList<Pain> pains) {
        super(context, txtViewResourceId, pains);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View contentView, ViewGroup parent) {
        return getPainView(position, contentView, parent);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        return getPainView(position, contentView, parent);
    }

    public View getPainView(int position, View convertView, ViewGroup parent) {
        Pain pain = getItem(position);

        View painSpinner = inflater.inflate(R.layout.item_pain, parent, false);

        TextView tvName = (TextView) painSpinner.findViewById(R.id.painListName);
        tvName.setText(pain.getDescription());

        TextView tvId = (TextView) painSpinner.findViewById(R.id.painListId);
        tvId.setText(String.valueOf(pain.getId()));

        return painSpinner;
    }
}
