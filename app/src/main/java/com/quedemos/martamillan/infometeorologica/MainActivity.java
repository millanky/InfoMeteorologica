package com.quedemos.martamillan.infometeorologica;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.ListPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    Spinner eleccionCiudad;
    String[] codigos;
    String codigoSeleccionado;
    String ciudadSeleccionada;
    Button mostrarInfoMeteo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigos = getResources().getStringArray(R.array.codigo);
        mostrarInfoMeteo = (Button) findViewById(R.id.mostrar);
        eleccionCiudad = (Spinner) findViewById(R.id.ciudad);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ciudad, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eleccionCiudad.setAdapter(adapter);


        eleccionCiudad.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        ciudadSeleccionada = String.valueOf(parent.getItemAtPosition(position));
                        codigoSeleccionado = String.valueOf(codigos[position]);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        mostrarInfoMeteo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //TODO: MANDAR CODIGO A LA SIGUIENTE PAGINA y que ella llame a extraer datos xml

                Intent i = new Intent (MainActivity.this, InfoMeteoActivity.class);
                i.putExtra("code", codigoSeleccionado);
                i.putExtra("city", ciudadSeleccionada);
                startActivity(i);
            }
        });
    }





}
