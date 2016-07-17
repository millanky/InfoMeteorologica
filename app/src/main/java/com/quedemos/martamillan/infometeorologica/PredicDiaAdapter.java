package com.quedemos.martamillan.infometeorologica;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Iterator;
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

        Calendar c = Calendar.getInstance();

        if  (pos == 0) {

            RelativeLayout firstRow = (RelativeLayout) convertView;
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            firstRow = (RelativeLayout) inflater.inflate(R.layout.top_view, null);

            TextView tempActual = (TextView) firstRow.findViewById(R.id.tempActual);
            TextView tempMaxActual = (TextView) firstRow.findViewById(R.id.tempMaxActual);
            TextView tempMinActual = (TextView) firstRow.findViewById(R.id.tempMinActual);
            TextView temp1 = (TextView) firstRow.findViewById(R.id.temp1);
            TextView temp2 = (TextView) firstRow.findViewById(R.id.temp2);
            TextView temp3 = (TextView) firstRow.findViewById(R.id.temp3);
            TextView temp4 = (TextView) firstRow.findViewById(R.id.temp4);
            TextView precip1 = (TextView) firstRow.findViewById(R.id.precip1);
            TextView precip2 = (TextView) firstRow.findViewById(R.id.precip2);
            TextView precip3 = (TextView) firstRow.findViewById(R.id.precip3);
            TextView precip4 = (TextView) firstRow.findViewById(R.id.precip4);

            //CALCULO TEMPERATURA ACTUAL
            int hora = c.get(Calendar.HOUR_OF_DAY);
            if (hora <= 6) {
                tempActual.setText(getItem(pos).getTemperaturasDia().get(0)+"º");
            } else if (hora <= 12) {
                tempActual.setText(getItem(pos).getTemperaturasDia().get(1)+"º");
            } else if (hora <= 18) {
                tempActual.setText(getItem(pos).getTemperaturasDia().get(2)+"º");
            } else {
                tempActual.setText(getItem(pos).getTemperaturasDia().get(3)+"º");
            }

            tempMaxActual.setText("↑ " + getItem(pos).getTemperaturaMax()+"º");
            tempMinActual.setText("↓ " + getItem(pos).getTemperaturaMin()+"º");

            temp1.setText(getItem(pos).getTemperaturasDia().get(0)+"ºC");
            temp2.setText(getItem(pos).getTemperaturasDia().get(1)+"ºC");
            temp3.setText(getItem(pos).getTemperaturasDia().get(2)+"ºC");
            temp4.setText(getItem(pos).getTemperaturasDia().get(3)+"ºC");
            precip1.setText(getItem(pos).getPrecipitacionesDia().get(0)+"%");
            precip2.setText(getItem(pos).getPrecipitacionesDia().get(1)+"%");
            precip3.setText(getItem(pos).getPrecipitacionesDia().get(2)+"%");
            precip4.setText(getItem(pos).getPrecipitacionesDia().get(3)+"%");

            return firstRow;

        } else {
            RelativeLayout row = (RelativeLayout) convertView;
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout) inflater.inflate(R.layout.row_day, null);

            TextView day = (TextView) row.findViewById(R.id.diaTxt);
            TextView tempMax = (TextView) row.findViewById(R.id.tempMax);
            TextView tempMin = (TextView) row.findViewById(R.id.tempMin);
            TextView humMax = (TextView) row.findViewById(R.id.humMax);
            TextView humMin = (TextView) row.findViewById(R.id.humMin);
            TextView precip = (TextView) row.findViewById(R.id.precip);

            int diaSemana = c.get(Calendar.DAY_OF_WEEK);
            diaSemana += pos;
            if (diaSemana > 7) {
                diaSemana -= 7;
            }

            switch (diaSemana) {
                case 1: day.setText("Dom");
                    break;
                case 2: day.setText("Lun");
                    break;
                case 3: day.setText("Mar");
                    break;
                case 4:  day.setText("Mie");
                    break;
                case 5:  day.setText("Jue");
                    break;
                case 6:  day.setText("Vie");
                    break;
                case 7:  day.setText("Sab");
                    break;
                default:
                    break;
            }

            tempMax.setText("↑"+getItem(pos).getTemperaturaMax()+"º");
            tempMin.setText("↓"+getItem(pos).getTemperaturaMin()+"º");
            humMax.setText("↑"+getItem(pos).getHumedadMax()+"%");
            humMin.setText("↓"+getItem(pos).getHumedadMin()+"%");
            precip.setText(getItem(pos).getProbPrecipitacion()+"%");

            return row;
        }
    }

}
