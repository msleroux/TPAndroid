package com.example.mleroux2017.freestuff;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.DatePicker;


import java.util.Calendar;


public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private EditDateDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //on renvoie à l'activity les infos sélectionnées via l'interface
        listener.onFinishEditDateDialog(year,month,day);
        this.dismiss();
    }

    public interface EditDateDialogListener {
       void onFinishEditDateDialog(int year, int month, int day);
    }
}
