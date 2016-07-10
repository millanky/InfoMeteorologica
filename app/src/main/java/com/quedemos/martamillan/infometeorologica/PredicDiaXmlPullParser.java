package com.quedemos.martamillan.infometeorologica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class PredicDiaXmlPullParser {

    //LOs tags que aparecen en el XML
    //static final String KEY_DIA = "origen";
    //static final String KEY_TEMP= "nombre";
    //static final String KEY_SENS = "sens_termica";
    //static final String KEY_HUM = "provincia";


    public static List<PredicDia> getPredicDiasFromFile(Context ctx) {

        // List of StackSites that we will return
        List<PredicDia> predicDias;
        predicDias = new ArrayList<PredicDia>();

        // temp holder for current StackSite while parsing
        PredicDia curPredicDia = null;
        // temp holder for current text value while parsing
        String curText = "";

        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            FileInputStream fis = ctx.openFileInput("PredicDias.xml");
           // BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            // point the parser to our file.
            xpp.setInput(fis, "ISO-8859-1");

            // get initial eventType
            int eventType = xpp.getEventType();

            boolean caseTemperatura = false;
            boolean caseHumedad = false;

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Get the current tag
                String tagname = xpp.getName(); //El tag <>

                // React to different event types appropriately
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("dia")) { //el comienzo, creamos un objeto PredicDia
                            curPredicDia = new PredicDia();
                            curPredicDia.setDia(xpp.getAttributeValue(null,"fecha"));
                        } else if (tagname.equalsIgnoreCase("temperatura")) {
                            caseTemperatura = true;
                        } else if (tagname.equalsIgnoreCase("humedad_relativa")) {
                            caseHumedad = true;
                        }
                        break;

                    case XmlPullParser.TEXT:
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("dia")) {
                            //si es </dia> ya hemos cogido todos los datos que queriamos, lo añadimos a la lista
                            predicDias.add(curPredicDia);
                        } else if (tagname.equalsIgnoreCase("maxima")) {
                            if (caseTemperatura) {
                                curPredicDia.setTemperaturaMax(curText);
                            } else if (caseHumedad) {
                                curPredicDia.setHumedadMax(curText);
                            }
                        } else if (tagname.equalsIgnoreCase("minima")) {
                            if (caseTemperatura) {
                                curPredicDia.setTemperaturaMin(curText);
                                caseTemperatura =false;
                            } else if (caseHumedad) {
                                curPredicDia.setHumedadMin(curText);
                                caseHumedad = false;
                            }
                        }
                        break;

                    default:
                        break;
                }
                //move on to next iteration
                eventType = xpp.next(); //next even type
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return predicDias;
    }
}


