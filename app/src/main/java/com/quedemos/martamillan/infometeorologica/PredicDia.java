package com.quedemos.martamillan.infometeorologica;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MartaMillan on 10/7/16.
 */
public class PredicDia {

    private String dia;
    private String temperaturaMax;
    private String temperaturaMin;
    private String humedadMax;
    private String humedadMin;
    private String probPrecipitacion;
    private ArrayList<String> temperaturasDia = new ArrayList<String>();
    private ArrayList<String> precipitacionesDia = new ArrayList<String>();


    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public String getTemperaturaMax() {
        return temperaturaMax;
    }
    public void setTemperaturaMax(String temp) {
        this.temperaturaMax = temp;
    }
    public String getTemperaturaMin() {
        return temperaturaMin;
    }
    public void setTemperaturaMin(String temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }
    public String getHumedadMax() {
        return humedadMax;
    }
    public void setHumedadMax(String hum) {
        this.humedadMax = hum;
    }
    public String getHumedadMin() {
        return humedadMin;
    }
    public void setHumedadMin(String humedadMin) {
        this.humedadMin = humedadMin;
    }

    public ArrayList<String> getTemperaturasDia() {
        return temperaturasDia;
    }

    public void setTemperaturasDia(ArrayList<String> temperaturasDia) {
        this.temperaturasDia = temperaturasDia;
    }

    public String getProbPrecipitacion() {
        return probPrecipitacion;
    }

    public void setProbPrecipitacion(String probPrecipitacion) {
        this.probPrecipitacion = probPrecipitacion;
    }

    public ArrayList<String> getPrecipitacionesDia() {
        return precipitacionesDia;
    }

    public void setPrecipitacionesDia(ArrayList<String> precipitacionesDia) {
        this.precipitacionesDia = precipitacionesDia;
    }
}
