package com.example.mleroux2017.freestuff.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private EditTimeDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EditTimeDialogListener){
            listener=((EditTimeDialogListener) context);
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
      sendTime(hourOfDay,minute);
      //remove la boite de dialogue
      this.dismiss();
    }

    //lance l'event du listener
    public void sendTime(int hourOfDay, int minute) {
        listener.onFinishEditTimeDialog(hourOfDay,minute);
    }

    public interface EditTimeDialogListener {
        void onFinishEditTimeDialog(int hour, int minute);
    }
}