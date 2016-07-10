package com.quedemos.martamillan.infometeorologica;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MartaMillan on 10/7/16.
 */
public class PredicDiaAdapter extends ArrayAdapter<PredicDia> {


    public PredicDiaAdapter(Context ctx, int textViewResourceId, List<PredicDia> sites) {
        super(ctx, textViewResourceId, sites);


    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        RelativeLayout row = (RelativeLayout) convertView;
        LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = (RelativeLayout)inflater.inflate(R.layout.row_day, null);
        TextView day = (TextView)row.findViewById(R.id.diaTxt);
        TextView tempMax = (TextView)row.findViewById(R.id.tempMax);
        TextView tempMin = (TextView)row.findViewById(R.id.tempMin);
        TextView humMax = (TextView)row.findViewById(R.id.humMax);
        TextView humMin = (TextView)row.findViewById(R.id.humMin);

        day.setText(getItem(pos).getDia());
        tempMax.setText(getItem(pos).getTemperaturaMax());
        tempMin.setText(getItem(pos).getTemperaturaMin());
        humMax.setText(getItem(pos).getHumedadMax());
        humMin.setText(getItem(pos).getHumedadMin());

        //Log.e("DIA",getItem(pos).getDia());
        //Log.e("HUMEDAD",getItem(pos).getHumedadMax());

        return row;
    }

}
