package com.quedemos.martamillan.infometeorologica;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;

/**
 * Created by MartaMillan on 10/7/16.
 */
public class InfoMeteoActivity extends AppCompatActivity{

    private PredicDiaAdapter mAdapter; //nos permite acceso a nuestro objeto PredicDia
    private ListView predicDiaList;
    private LinearLayout layout;
    String codigoCiudad;
    String ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        SharedPreferences prefs = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        ciudad = prefs.getString("ciudad", "Madrid"); //si no hay ciudad guardad aún, se pone Madrid por defecto
        codigoCiudad = prefs.getString("codCiudad", "28079");

        getSupportActionBar().setTitle(ciudad);

        predicDiaList = (ListView)findViewById(R.id.dayList);

        layout = (LinearLayout)findViewById(R.id.container);

        if (ciudad.equalsIgnoreCase("barcelona")) {
            layout.setBackground(getResources().getDrawable(R.drawable.barcelona));
        } else if (ciudad.equalsIgnoreCase("madrid")) {
            layout.setBackground(getResources().getDrawable(R.drawable.madrid));
        } else if (ciudad.equalsIgnoreCase("alicante")) {
            layout.setBackground(getResources().getDrawable(R.drawable.alicante));
        } else if (ciudad.equalsIgnoreCase("valladolid")) {
            layout.setBackground(getResources().getDrawable(R.drawable.valladolid));
        } else {
            layout.setBackground(getResources().getDrawable(R.drawable.san_sebastian));
        }

        /*
		 SI NO HAY CONEXION A INTERNET ACCEDEMOS A LO YA GUARDADO
		 */
        if(isNetworkAvailable()){
            Log.i("StackSites", "starting download Task");
            PredicDownloadTask download = new PredicDownloadTask();
            download.execute();
        }else{
            mAdapter = new PredicDiaAdapter(getApplicationContext(), -1, PredicDiaXmlPullParser.getPredicDiasFromFile(InfoMeteoActivity.this));
            predicDiaList.setAdapter(mAdapter);
            getSupportActionBar().setTitle(ciudad);
        }
    }

    //DETERMINAR SI HAY CONEXION
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
     * ASYNCTASK PARA DESCARGAR EL XML.
     */
    private class PredicDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            //Download the file and SAVE
            try {
                Downloader.DownloadFromUrl("http://www.aemet.es/xml/municipios/localidad_"+codigoCiudad+".xml", openFileOutput("PredicDias.xml", Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            mAdapter = new PredicDiaAdapter(InfoMeteoActivity.this, -1, PredicDiaXmlPullParser.getPredicDiasFromFile(InfoMeteoActivity.this));
            predicDiaList.setAdapter(mAdapter);
            getSupportActionBar().setTitle(ciudad);
        }
    }

    //------------------ MENU ---------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.elegir_ciudad, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences prefs = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        int id = item.getItemId();

        if (id == R.id.alicante) {
            edit.putString("ciudad", "Alicante");
            edit.putString("codCiudad","03014");
        } else if (id == R.id.valladolid) {
            edit.putString("ciudad", "Valladolid");
            edit.putString("codCiudad","47186");
        } else if (id == R.id.barcelona) {
            edit.putString("ciudad", "Barcelona");
            edit.putString("codCiudad","08019");
        } else if (id == R.id.madrid) {
            edit.putString("ciudad", "Madrid");
            edit.putString("codCiudad","28079");
        } else if (id == R.id.san_sebastian){
            edit.putString("ciudad", "San Sebastián");
            edit.putString("codCiudad","20069");
        }

        edit.commit();

        finish();
        startActivity(getIntent());

        return super.onOptionsItemSelected(item);
    }
}
