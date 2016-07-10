package com.quedemos.martamillan.infometeorologica;

/**
 * Created by MartaMillan on 10/7/16.
 */
public class PredicDia {

    private String dia;
    private String temperaturaMax;
    private String temperaturaMin;
    private String humedadMax;
    private String humedadMin;

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

    @Override
    public String toString() {
        return "PredicDia [dia=" + dia + ", temperaturaMax=" + temperaturaMax + ", temperaturaMin="
                + temperaturaMin + ", humedadMax=" + humedadMax +", humedadMin=" + humedadMin + "]";
    }

}
