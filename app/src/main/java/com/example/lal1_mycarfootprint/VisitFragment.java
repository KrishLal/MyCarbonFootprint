/*
Code Written by: Krish Lal
SN: 1721688
 */


package com.example.lal1_mycarfootprint;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.lal1_mycarfootprint.CarVisit;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class VisitFragment extends DialogFragment {
    interface AddVisitListener {
        void AddVisit(CarVisit carVisit);
        void EditVisit(CarVisit carVisit);
        void DeleteVisit(CarVisit carVisit);
    }
    private int index;
    private CarVisit carVisit;
    private AddVisitListener listener;
    private ArrayList<CarVisit> CarVisits;

    public VisitFragment(CarVisit carVisit,int position, ArrayList<CarVisit> CarVisits) {
        this.carVisit = carVisit;
        this.index = position;
        this.CarVisits = CarVisits;
    }

    public VisitFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AddVisitListener){
            listener = (AddVisitListener) context;

        }
        else{
            throw new RuntimeException(context+"must implement AddVisitListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.add_edit_visit_fragment, null);
        TextView dateDisplay = view.findViewById(R.id.date_text_view);
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        String stringDate = DateFor.format(date);
        dateDisplay.setText(stringDate);


        EditText editGasStation = view.findViewById(R.id.gas_station_name_edit_text);
        EditText ltrAmount = view.findViewById(R.id.amount_litres_edit_text);
        EditText priceLitre = view.findViewById(R.id.price_per_liter_edit_text);

        //The article of "Change date string format in android" was used to help with the date picker
        //Author:Krishna Suthar, License: cc-wiki, URL:https://stackoverflow.com/questions/10426492/change-date-string-format-in-android
        Button dateButton = view.findViewById(R.id.date_button);
        final Calendar myCalendar = Calendar.getInstance();
        final int year = myCalendar.get(Calendar.YEAR);
        final int month = myCalendar.get(Calendar.MONTH);
        final int day = myCalendar.get(Calendar.DAY_OF_MONTH);

        //Learned datepicker using another geeksforgeeks article
        //Author:@chaitanyamunje , License: cc-wiki, URL:https://www.geeksforgeeks.org/datepicker-in-android/, Date: 17 Jul, 2022
        dateButton.setOnClickListener(view1 -> {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (datePicker, year1, month1, day1) -> {
                myCalendar.set(Calendar.YEAR, year1);
                myCalendar.set(Calendar.MONTH, month1);
                myCalendar.set(Calendar.DAY_OF_MONTH, day1);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                dateDisplay.setText(sdf.format(myCalendar.getTime()));
            }, year, month, day);
            dialog.show();
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (carVisit == null){
            return builder
                    .setView(view)
                    .setTitle("Add a Visit")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialog, which) -> {
                        //used stackoverflow to help with getting the selected radio button: "How to check which radio button of a radio group is selected? [ANDROID]"
                        //User: Aftab Alam, License:cc-wiki,URL:https://stackoverflow.com/questions/42502055/how-to-check-which-radio-button-of-a-radio-group-is-selected-android, Date: Nov 22, 2019
                        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
                        int radioButtonID = radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(radioButtonID);
                        int idx = radioGroup.indexOfChild(radioButton);
                        RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
                        String selectedText = r.getText().toString();
                        String dateText = dateDisplay.getText().toString();
                        String gasStation = editGasStation.getText().toString();
                        if(gasStation.equals("")){
                            gasStation = "Unknown";
                        }
                        else if(gasStation.length() > 30){ //added limit to gas station name so it doesn't exceed 30 characters
                            gasStation = gasStation.substring(0,30);
                        }
                        String amtLtr = ltrAmount.getText().toString();
                        if (amtLtr.equals("")){
                            amtLtr = "0";
                        }
                        String priceLtr = priceLitre.getText().toString();
                        if (priceLtr.equals("")){
                            priceLtr = "0";
                        }
                        CarVisit carVisit3 = new CarVisit(gasStation, dateText, selectedText, amtLtr, priceLtr);
                        listener.AddVisit(carVisit3);
                    })
                    .create();
        }
        //referenced Lab 3 that I wrote myself for this code
        else{
            editGasStation.setText(carVisit.getGasStation());
            ltrAmount.setText(carVisit.getLitres());
            priceLitre.setText(carVisit.getPriceLitre());
            dateDisplay.setText(carVisit.getDateVisit());
            return builder
                    .setView(view)
                    .setTitle("Edit a Visit")
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Delete Visit", (dialog, which) -> {
                        CarVisits.remove(index);
                        listener.DeleteVisit(carVisit);
                    })
                    .setPositiveButton("Edit", (dialog, which) -> {
                        //used stackoverflow to help with getting the selected radio button: "How to check which radio button of a radio group is selected? [ANDROID]"
                        //User: Aftab Alam, License:cc-wiki,URL:https://stackoverflow.com/questions/42502055/how-to-check-which-radio-button-of-a-radio-group-is-selected-android, Date: Nov 22, 2019
                        RadioGroup radioGroup = view.findViewById(R.id.radio_group);
                        int radioButtonID = radioGroup.getCheckedRadioButtonId();
                        View radioButton = radioGroup.findViewById(radioButtonID);
                        int idx = radioGroup.indexOfChild(radioButton);
                        RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
                        String selectedText = r.getText().toString();
                        String dateText = dateDisplay.getText().toString();
                        String gasStation = editGasStation.getText().toString();
                        if(gasStation.equals("")){
                            gasStation = "Unknown";
                        }
                        else if(gasStation.length() > 30){ //added limit to gas station name so it doesn't exceed 30 characters
                            gasStation = gasStation.substring(0,30);
                        }
                        String amtLtr = ltrAmount.getText().toString();
                        if (amtLtr.equals("")){
                            amtLtr = "0";
                        }
                        String priceLtr = priceLitre.getText().toString();
                        if (priceLtr.equals("")){
                            priceLtr = "0";
                        }
                        carVisit.setGasStation(gasStation);
                        carVisit.setDateVisit(dateText);
                        carVisit.setFuelType(selectedText);
                        carVisit.setLitres(amtLtr);
                        carVisit.setPriceLitre(priceLtr);
                        listener.EditVisit(carVisit);
                    })
                    .create();
        }
    }
}



