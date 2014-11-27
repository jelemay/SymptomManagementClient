package com.coursera.symptommanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coursera.symptommanagement.R;
import com.coursera.symptommanagement.models.Appetite;

import java.util.ArrayList;

/**
 * Created by victorialemay on 11/25/14.
 */
public class AppetiteListAdapter extends ArrayAdapter<Appetite> {

    private LayoutInflater inflater;

    public AppetiteListAdapter(Context context, int txtViewResourceId,
                               ArrayList<Appetite> appetites) {
        super(context, txtViewResourceId, appetites);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View contentView, ViewGroup parent) {
        return getAppetiteView(position, contentView, parent);
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        return getAppetiteView(position, contentView, parent);
    }

    public View getAppetiteView(int position, View convertView, ViewGroup parent) {
        Appetite appetite = getItem(position);

        View appetiteRow = inflater.inflate(R.layout.item_appetite, parent, false);

        TextView tvName = (TextView) appetiteRow.findViewById(R.id.appetiteListName);
        tvName.setText(appetite.getDescription());

        TextView tvId = (TextView) appetiteRow.findViewById(R.id.appetiteListId);
        tvId.setText(String.valueOf(appetite.getId()));

        return appetiteRow;
    }

}
