package hr.foi.air.sportloc.view.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.ActivityLoginBinding;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.DataInputValidator;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.viewmodel.LoginInfoViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(LoginInfoViewModel.class);
    }

    private void showMessage(String text, int color) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.getView().getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        toast.show();
    }

    private void observeViewModel(LoginInfoViewModel viewModel) {
        viewModel.getLoginInfoObservable().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer loginInfo) {
                if (loginInfo != null) {
                    if (loginInfo != 0) {
                        showMessage(getResources().getString(R.string.login_success), Color.GREEN);
                    }
                    else {
                        showMessage(getResources().getString(R.string.login_fail), Color.RED);
                    }
                }
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void submitLoginForm() {
        boolean success = DataInputValidator.validateInput(binding.etUsername,
                getResources().getString(R.string.login_username_empty_error),
                Constants.USERNAME_MIN_LENGTH,
                getResources().getString(R.string.login_username_length_error, Constants.USERNAME_MIN_LENGTH));

        if (!DataInputValidator.validateInput(binding.etPassword,
                getResources().getString(R.string.login_password_empty_error),
                Constants.PASSWORD_MIN_LENGTH,
                getResources().getString(R.string.login_password_length_error, Constants.PASSWORD_MIN_LENGTH))) {
            success = false;
        }
        if (success) {
            viewModel.login(binding.etUsername.getText().toString(), binding.etPassword.getText().toString());
            observeViewModel(viewModel);
        }
    }

    @OnClick(R.id.tv_forgotten_password)
    public void openForgottenPasswordActivity() {
        IntentManager.startActivity(getApplicationContext(), ForgottenPasswordActivity.class);
    }
}
