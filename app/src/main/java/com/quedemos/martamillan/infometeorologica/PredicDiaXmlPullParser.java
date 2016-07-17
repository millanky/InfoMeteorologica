package com.quedemos.martamillan.infometeorologica;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

public class PredicDiaXmlPullParser {


    public static List<PredicDia> getPredicDiasFromFile(Context ctx) {

        // Lista de días que va a ser devuelta
        List<PredicDia> predicDias;
        predicDias = new ArrayList<PredicDia>();

        // variables temporales
        PredicDia curPredicDia = null;
        String curText = "";
        String curEstadoCielo = "";
        ArrayList<String> currTemperaturasDia = new ArrayList<String>();
        ArrayList<String> currPrecipitacionesDia = new ArrayList<String>();
        String curPeriodo = "";
        int numDatoTemperatura = 0;

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            FileInputStream fis = ctx.openFileInput("PredicDias.xml");
            xpp.setInput(fis, "ISO-8859-1");

            int eventType = xpp.getEventType();

            boolean caseTemperatura = false;
            boolean caseHumedad = false;
            boolean casePrecip = false;
            boolean casePrecipGeneral = false;
            boolean caseEstadoCielo = false;


            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagname = xpp.getName(); //El tag <>


                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("dia")) { //el comienzo, creamos un objeto PredicDia
                            curPredicDia = new PredicDia();
                            curPredicDia.setDia(xpp.getAttributeValue(null,"fecha"));
                            casePrecipGeneral = true;
                            caseEstadoCielo = true;
                        } else if (tagname.equalsIgnoreCase("temperatura")) {
                            caseTemperatura = true;
                            currTemperaturasDia = new ArrayList<String>();
                        } else if (tagname.equalsIgnoreCase("humedad_relativa")) {
                            caseHumedad = true;
                        } else if (tagname.equalsIgnoreCase("prob_precipitacion")) {
                            curPeriodo = xpp.getAttributeValue(null,"periodo");
                            if (curPeriodo == null){
                                curPeriodo = "";
                            }
                            casePrecip = true;
                        } else if (tagname.equalsIgnoreCase("estado_cielo")) {
                            curEstadoCielo = xpp.getAttributeValue(null,"descripcion");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        //recogemos el texto
                        curText = xpp.getText();

                        if (curPeriodo.equalsIgnoreCase("00-06") && casePrecip){
                            if (!android.text.TextUtils.isDigitsOnly(curText)) {
                                currPrecipitacionesDia.add("0");
                            } else {
                                currPrecipitacionesDia.add(curText);
                            }
                            casePrecip=false;

                        } else if (curPeriodo.equalsIgnoreCase("06-12") && casePrecip){
                            if (!android.text.TextUtils.isDigitsOnly(curText)) {
                                currPrecipitacionesDia.add("0");
                            }else {
                                currPrecipitacionesDia.add(curText);
                            }
                            casePrecip=false;

                        } else if (curPeriodo.equalsIgnoreCase("12-18") && casePrecip) {
                            if (!android.text.TextUtils.isDigitsOnly(curText)) {
                                currPrecipitacionesDia.add("0");
                            }
                            else {
                                currPrecipitacionesDia.add(curText);
                            }
                            casePrecip=false;

                        } else if (curPeriodo.equalsIgnoreCase("18-24") && casePrecip){
                            if (!android.text.TextUtils.isDigitsOnly(curText)) {
                                currPrecipitacionesDia.add("0");
                            }
                            else {
                                currPrecipitacionesDia.add(curText);
                            }
                            curPredicDia.setPrecipitacionesDia(currPrecipitacionesDia);
                            casePrecip=false;
                            curPeriodo="";
                        }

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
                        } else if (tagname.equalsIgnoreCase("dato")) {
                            if (caseTemperatura) {
                                if (android.text.TextUtils.isDigitsOnly(curText)) {
                                    currTemperaturasDia.add(curText);
                                } else {currTemperaturasDia.add("-");}
                                numDatoTemperatura++;
                                if (numDatoTemperatura == 4 || numDatoTemperatura == 8) { //solo los dos primeros tienen datos de temperatura por horas
                                    curPredicDia.setTemperaturasDia(currTemperaturasDia);
                                    caseTemperatura = false;
                                }
                            }
                        } else if (tagname.equalsIgnoreCase("minima")) {
                            if (caseTemperatura) {
                                curPredicDia.setTemperaturaMin(curText);
                                if (numDatoTemperatura == 8) {
                                    caseTemperatura = false;
                                }
                            } else if (caseHumedad) {
                                curPredicDia.setHumedadMin(curText);
                                caseHumedad = false;
                            }
                        } else if (tagname.equalsIgnoreCase("prob_precipitacion") && casePrecipGeneral) { //primera probabilidad de precipitacion equivale a 00-24 (la general)
                            if(android.text.TextUtils.isDigitsOnly(curText)) {
                                curPredicDia.setProbPrecipitacion(curText);
                                casePrecipGeneral = false;
                            }
                        } else if (tagname.equalsIgnoreCase("estado_cielo") && caseEstadoCielo) { //primer estado cielo equivale a 00-24 (la general)
                            if(android.text.TextUtils.isDigitsOnly(curText)){
                                curPredicDia.setEstadoCieloImg(curText);
                                curPredicDia.setEstadoCielo(curEstadoCielo);
                                caseEstadoCielo = false;
                            }
                        }
                        break;

                    default:
                        break;
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return predicDias;
    }
}


