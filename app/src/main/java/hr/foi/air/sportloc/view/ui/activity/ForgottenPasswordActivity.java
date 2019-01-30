package hr.foi.air.sportloc.view.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.ActivityForgottenPasswordBinding;
import hr.foi.air.sportloc.view.util.DataInputValidator;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.ForgottenPasswordViewModel;

public class ForgottenPasswordActivity extends AppCompatActivity {
    private ActivityForgottenPasswordBinding binding;
    private ForgottenPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotten_password);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(ForgottenPasswordViewModel.class);
    }

    @OnClick(R.id.btn_send)
    public void resetPassword() {
        if (DataInputValidator.validateEmptyField(binding.etEmail, getResources().getString(R.string.forgotten_password_empty_error))) {
            viewModel.resetPassword(binding.etEmail.getText().toString());
            observeViewModel(viewModel);
        }
    }

    private void observeViewModel(ForgottenPasswordViewModel viewModel) {
        viewModel.getResetPasswordInfoObservable().observe(this, resetPasswordInfo -> {
            if (resetPasswordInfo != null) {
                if (resetPasswordInfo) {
                    MessageSender.sendMessage(getApplicationContext(), getResources().getString(R.string.forgotten_password_success));
                } else {
                    MessageSender.sendError(getApplicationContext(), getResources().getString(R.string.forgotten_password_fail));
                }
            } else {
                MessageSender.sendError(getApplicationContext(), getResources().getString(R.string.general_connection_error));
            }
        });
    }
}
