package hr.foi.air.sportloc.view.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import hr.foi.air.sportloc.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
