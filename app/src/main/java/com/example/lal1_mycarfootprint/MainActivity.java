/*
Code Written by: Krish Lal
SN: 1721688
 */


package com.example.lal1_mycarfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VisitFragment.AddVisitListener{

    ArrayList<CarVisit> carVisits;
    VisitArrayAdapter visitAdapter;
    ListView visitList;

    //loops through every item in the list and adds up the total carbon footprint
    public void getTotalFootprint(ArrayList<CarVisit> carList){
        double totalFootprint = 0;
        for(int i = 0; i < carList.size(); i++){
            totalFootprint += carList.get(i).getCarbonFootprint();
        }
        TextView totalFootprintView = findViewById(R.id.car_footprint);
        totalFootprintView.setText(String.format("Total Footprint: %d kg C02",Math.round(totalFootprint)));
    }

    //loops through every item in the list and adds up the total fuel cost

    //Learned how to use Double.parseDouble() through GeekforGeeks article
    //Author: @gopaldave, License: cc-wiki, URL:https://www.geeksforgeeks.org/double-parsedouble-method-in-java-with-examples/
    public void totalFuelCost(ArrayList<CarVisit> carList){
        double totalFuel = 0.0;
        for(int i = 0; i < carList.size(); i++){
            totalFuel += Double.parseDouble(carList.get(i).getPriceLitre())*Double.parseDouble(carList.get(i).getLitres());
        }
        TextView totalFuelView = findViewById(R.id.total_fuel);
        totalFuelView.setText(String.format("Total Fuel Cost: %.2f $",totalFuel));
    }

    //overriding the methods from the interface
    @Override
    public void DeleteVisit(CarVisit carVisit) {
        totalFuelCost(carVisits);
        getTotalFootprint(carVisits);
        visitAdapter.notifyDataSetChanged();
    }

    @Override
    public void EditVisit(CarVisit carVisit) {
        totalFuelCost(carVisits);
        getTotalFootprint(carVisits);
        visitAdapter.notifyDataSetChanged();
    }

    @Override
    public void AddVisit(CarVisit carVisit){
        System.out.println(carVisit.getGasStation());
        //visitAdapter.add(carVisit);
        carVisits.add(carVisit);
        getTotalFootprint(carVisits);
        totalFuelCost(carVisits);
        visitAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carVisits = new ArrayList<CarVisit>();
        visitList = findViewById(R.id.visit_list);

        visitAdapter = new VisitArrayAdapter(this, carVisits);
        visitList.setAdapter(visitAdapter);

        Button addVisitButton = findViewById(R.id.add_trip);
        addVisitButton.setOnClickListener(v -> {
            new VisitFragment().show(getSupportFragmentManager(), "Add Visit");

        });
        visitList.setOnItemClickListener((parent,view,position,id)->{
            new VisitFragment(carVisits.get(position),position,carVisits).show(getSupportFragmentManager(), "Edit/Delete Visit");
        });


    }


}