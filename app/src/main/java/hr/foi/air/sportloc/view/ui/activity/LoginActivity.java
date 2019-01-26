package hr.foi.air.sportloc.view.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.ActivityLoginBinding;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.DataInputValidator;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    public static final String EXTRA_MESSAGE = "hr.foi.air.sportloc.LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
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

    @OnClick(R.id.tv_register)
    public void openRegistrationActivity() {
        IntentManager.startActivity(getApplicationContext(), RegistrationActivity.class);
    }

    private void observeViewModel(LoginViewModel viewModel) {
        viewModel.getLoginInfoObservable().observe(this, loginInfo -> {
            if (loginInfo != null) {
                if (loginInfo.getUserId() != 0) {
                    ActiveUserModel.getInstance().setActiveUser(loginInfo);
                    IntentManager.startActivity(getApplicationContext(), EventListActivity.class, EXTRA_MESSAGE, getResources().getString(R.string.login_success));
                } else {
                    MessageSender.sendError(getApplicationContext(), getResources().getString(R.string.login_fail));
                }
            } else {
                MessageSender.sendError(getApplicationContext(), getResources().getString(R.string.general_connection_error));
            }
        });
    }
}
