package com.quedemos.martamillan.infometeorologica;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

/*
 * Helper class for downloading a file.
 */

public class Downloader {

    private static String myTag = "TiempoDia";

    static final int POST_PROGRESS = 1;

    public static void DownloadFromUrl(String URL, FileOutputStream fos) {  //this is the downloader method
        try {

            URL url = new URL(URL); //URL of the file

            //keep the start time so we can display how long it took to the Log.
            long startTime = System.currentTimeMillis();
            Log.d(myTag, "download begining");

			/* Open a connection to that URL. */
            URLConnection ucon = url.openConnection();

            // this will be useful so that you can show a tipical 0-100% progress bar
            //int lenghtOfFile = ucon.getContentLength();

            Log.i(myTag, "Opened Connection");

            /************************************************
             * Define InputStreams to read from the URLConnection.
             ************************************************/
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            Log.i(myTag, "Got InputStream and BufferedInputStream");

            /************************************************
             * Define OutputStreams to write to our file.
             ************************************************/

            BufferedOutputStream bos = new BufferedOutputStream(fos);
            Log.i(myTag, "Got FileOutputStream and BufferedOutputStream");

            /************************************************
             * Start reading the and writing our file.
             ************************************************/
            byte data[] = new byte[1024];

            int count;

            while ((count = bis.read(data)) != -1) {

                bos.write(data, 0, count);
            }

            bos.flush();
            bos.close();

            Log.d(myTag, "download ready in "
                    + ((System.currentTimeMillis() - startTime))
                    + " milisec");
        } catch (IOException e) {
            Log.d(myTag, "Error: " + e);
        }
    }
}