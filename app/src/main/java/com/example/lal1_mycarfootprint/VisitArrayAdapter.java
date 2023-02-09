/*
Code Written by: Krish Lal
SN: 1721688
 */

package com.example.lal1_mycarfootprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class VisitArrayAdapter extends ArrayAdapter<CarVisit> {
    public VisitArrayAdapter(Context context, List<CarVisit> cars) {
        super(context,0,cars);
    }


    //used android docs to read how to create a cardview
    //Author: Google, URL: https://developer.android.com/reference/androidx/cardview/widget/CardView, Date published: 2023-01-25

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,parent,false);
        }
        else{
            view = convertView;
        }

        //simply receiving the fields to be set as a list item
        CarVisit carVisit = getItem(position);
        TextView stationView = view.findViewById(R.id.station_name);
        TextView dateView = view.findViewById(R.id.date_visit);
        TextView fuelView = view.findViewById(R.id.fuel_type);
        TextView litresView = view.findViewById(R.id.amount_litres);
        TextView priceView = view.findViewById(R.id.price_litres);
        TextView carbonView = view.findViewById(R.id.carbon_footprint);
        TextView costView = view.findViewById(R.id.fuel_cost);
        stationView.setText(String.format("Gas Station: %s", carVisit.getGasStation()));
        dateView.setText(String.format("Date of Visit: %s", carVisit.getDateVisit()));
        fuelView.setText(String.format("Fuel Type: %s", carVisit.getFuelType()));
        litresView.setText(String.format("Amount of Litres: %s L", carVisit.getLitres()));
        priceView.setText(String.format("Price per Litre: %.2f $",Double.parseDouble(carVisit.getPriceLitre())));
        carbonView.setText(String.format("Carbon Footprint: %d kg CO2",Math.round(carVisit.getCarbonFootprint())));
        costView.setText(String.format("Fuel Cost: %.2f $",Double.parseDouble(carVisit.getLitres())*Double.parseDouble(carVisit.getPriceLitre())));

        return view;
    }
}
