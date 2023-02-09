package com.example.lal1_mycarfootprint;
/*
Code Written by: Krish Lal
SN: 1721688
 */

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CarVisit {
    private String gasStation;
    private String dateVisit;
    private String fuelType;
    private String amtoflitres;
    private String priceLitre;

    public CarVisit(String gasStation, String dateVisit, String fuelType, String amtoflitres, String priceLitre){
        this.gasStation = gasStation;
        this.dateVisit = dateVisit;
        this.fuelType = fuelType;
        this.amtoflitres = amtoflitres;
        this.priceLitre = priceLitre;
    }

    public String getGasStation(){
        return gasStation;
    }

    public String getDateVisit(){
        return dateVisit;
    }

    public String getFuelType(){
        return fuelType;
    }

    public String getLitres(){
        return amtoflitres;
    }

    public String getPriceLitre(){
        return priceLitre;
    }

    public double getCarbonFootprint(){

        double carbonFootprint = 0.0;
        if(this.fuelType.equals("Gasoline")){
            carbonFootprint = Double.parseDouble(amtoflitres) * 2.32f;
        }
        else{
            carbonFootprint = Double.parseDouble(amtoflitres) * 2.69f;
        }
        return carbonFootprint;
    }



    public void setGasStation(String gasStation){
        this.gasStation = gasStation;
    }

    public void setDateVisit(String dateVisit){
        this.dateVisit = dateVisit;
    }

    public void setFuelType(String fuelType){
        this.fuelType = fuelType;
    }

    public void setLitres(String amtoflitres){
        this.amtoflitres = amtoflitres;
    }

    public void setPriceLitre(String priceLitre){
        this.priceLitre = priceLitre;
    }

}
