package hr.foi.air.sportloc.view.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hr.foi.air.sportloc.R;

public class ForgottenPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
