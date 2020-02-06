package id.ac.binus.recruito;

//
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar CalendarObj = Calendar.getInstance();
        int hour = CalendarObj.get(Calendar.HOUR_OF_DAY);
        int minute = CalendarObj.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.DialogTheme, (TimePickerDialog.OnTimeSetListener) getContext(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}