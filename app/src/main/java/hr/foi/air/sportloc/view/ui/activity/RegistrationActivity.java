package hr.foi.air.sportloc.view.ui.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.etv_name)
    EditText etvName;

    @BindView(R.id.etv_surname)
    EditText etvSurname;

    @BindView(R.id.etv_username)
    EditText etvUsername;

    @BindView(R.id.etv_email)
    EditText etvEmail;

    @BindView(R.id.etv_password)
    EditText etvPassword;

    @BindView(R.id.spn_gender)
    Spinner spnGender;

    @BindView(R.id.tv_date)
    TextView tvDate;


    private static final String TAG = "RegistrationActivity";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                tvDate.setText(date);
            }
        };
    }

    @OnClick(R.id.tv_date)
    public void dateClick()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                RegistrationActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
