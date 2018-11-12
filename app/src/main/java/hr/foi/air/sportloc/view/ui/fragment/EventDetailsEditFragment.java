package hr.foi.air.sportloc.view.ui.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;

public class EventDetailsEditFragment  {


    @BindView(R.id.etv_start_date)
    TextView etvStartDate;

    private Context context;

    public EventDetailsEditFragment(Context context) {
        this.context = context;
    }


    @OnClick(R.id.etv_start_date)
    public void editStartDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(context, getOnDateSetListener(),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener getOnDateSetListener() {
        return new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        };

    }


}
