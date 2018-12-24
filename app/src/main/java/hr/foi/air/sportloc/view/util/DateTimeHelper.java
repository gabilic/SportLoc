package hr.foi.air.sportloc.view.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimeHelper {

    private DatePickerDialog.OnDateSetListener getOnDateSetListener(final Context context, final TextView textView, final boolean showTimePicker) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
//                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                if (showTimePicker) {
                    showTimePicker(context, textView, date);
                } else {
                    textView.setText(date);
                }
            }
        };
    }

    private void showTimePicker(final Context context, final TextView textView, final String date) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        textView.setText(date+" "+hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    public void showDateDialog(Context context, TextView textView) {
        createDatePicker(context, textView, false);
    }

    public void showDateTimeDialog(Context context, TextView textView) {
        createDatePicker(context, textView, true);
    }

    private void createDatePicker(Context context, TextView textView, boolean showTimePicker) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                getOnDateSetListener(context, textView, showTimePicker),
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
