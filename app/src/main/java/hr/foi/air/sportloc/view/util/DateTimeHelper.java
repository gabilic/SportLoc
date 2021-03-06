package hr.foi.air.sportloc.view.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateTimeHelper {

    private DatePickerDialog.OnDateSetListener getOnDateSetListener(final Context context, final TextView textView, final boolean showTimePicker) {
        return (datePicker, year, month, day) -> {
            month = month + 1;
            String date = day + "." + month + "." + year + ".";
            if (showTimePicker) {
                showTimePicker(context, textView, date);
            } else {
                textView.setText(date);
            }
        };
    }

    private void showTimePicker(final Context context, final TextView textView, final String date) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minute) ->
                        textView.setText(date + " " + hourOfDay + ":" + minute),
                hour, min, false);
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
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public static String calculateAge(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date dateFromPC = Calendar.getInstance().getTime();
        Date DateFromUserNewFormat = formatter.parse(date, new ParsePosition(0));
        SimpleDateFormat formatterForYears = new SimpleDateFormat("yyyy");

        String yearFromPc = formatterForYears.format(dateFromPC);
        String yearFromUser = formatterForYears.format(DateFromUserNewFormat);
        int differenceInYears = Integer.parseInt(yearFromPc) - Integer.parseInt(yearFromUser);
        return Integer.toString(differenceInYears);
    }
}
