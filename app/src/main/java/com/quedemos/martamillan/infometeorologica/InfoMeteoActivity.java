package com.quedemos.martamillan.infometeorologica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;

/**
 * Created by MartaMillan on 10/7/16.
 */
public class InfoMeteoActivity extends AppCompatActivity{

    private PredicDiaAdapter mAdapter; //nos permite acceso a nuestro objeto StackSite
    private ListView predicDiaList;
    private TextView titulo;
    String codigoCiudad;
    String ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        codigoCiudad = getIntent().getStringExtra("code");
        ciudad = getIntent().getStringExtra("city");

        titulo = (TextView) findViewById(R.id.ciudad);
        predicDiaList = (ListView)findViewById(R.id.dayList);

        //Set the click listener to launch the browser when a row is clicked.
        predicDiaList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
               //----- Si hay URL
               /* String url = mAdapter.getItem(pos).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/
            }
        });

        /*
		 * If network is available download the xml from the Internet.
		 * If not then try to use the local file from last time.
		 */
        if(isNetworkAvailable()){
            Log.i("StackSites", "starting download Task");
            PredicDownloadTask download = new PredicDownloadTask();
            download.execute();
        }else{
            mAdapter = new PredicDiaAdapter(getApplicationContext(), -1, PredicDiaXmlPullParser.getPredicDiasFromFile(InfoMeteoActivity.this));
            predicDiaList.setAdapter(mAdapter);
            titulo.setText(ciudad);
        }
    }

    //Helper method to determine if Internet connection is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
     * AsyncTask that will download the xml file for us and store it locally.
     * After the download is done we'll parse the local file.
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
            //setup our Adapter and set it to the ListView.
            mAdapter = new PredicDiaAdapter(InfoMeteoActivity.this, -1, PredicDiaXmlPullParser.getPredicDiasFromFile(InfoMeteoActivity.this));
            predicDiaList.setAdapter(mAdapter);
            Log.e("StackSites", "adapter size = "+ mAdapter.getCount());

            titulo.setText(ciudad);
        }
    }
}
