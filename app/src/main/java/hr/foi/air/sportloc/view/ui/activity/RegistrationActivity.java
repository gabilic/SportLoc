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
import hr.foi.air.sportloc.service.model.UserModel;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.DataInputValidator;
import hr.foi.air.sportloc.view.util.DateTimeHelper;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.et_surname)
    EditText etSurname;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.spn_gender)
    Spinner spnGender;

    @BindView(R.id.tv_date)
    TextView tvDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_date)
    public void dateClick() {
        new DateTimeHelper().showDateDialog(this, tvDate);
    }

    @OnClick(R.id.btn_send)
    public void registerClick() {
        UserModel user = new UserModel();
        user.setName(etName.getText().toString());
        user.setSurname(etSurname.getText().toString());
        user.setUsername(etUsername.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setGender(resolveGender());
        user.setDob(tvDate.getText().toString().equals(getResources().getString(R.string.registration_choose)) ? null : tvDate.getText().toString());
        observeRegistration(user);
    }

    private void observeRegistration(UserModel user) {
        RegistrationViewModel registrationViewModel = new RegistrationViewModel();
        registrationViewModel.register(user);
        registrationViewModel.getRegistrationObservable().observe(this, result -> {
            if (result != null) {
                if (result.containsKey(true)) {
                    MessageSender.sendMessage(this, getResources().getString(R.string.registration_success));
                    IntentManager.startActivity(getApplicationContext(), LoginActivity.class);
                } else {
                    MessageSender.sendError(this, result.get(false));
                }
            } else {
                MessageSender.sendError(this, getResources().getString(R.string.general_connection_error));
            }
        });
    }

    private boolean resolveGender() {
        if (spnGender.getSelectedItem().toString().equalsIgnoreCase(getResources().getString(R.string.registration_male))) {
            return true;
        }
        return false;
    }

}
