package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import hr.foi.air.sportloc.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
