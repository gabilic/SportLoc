package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.util.IntentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_account)
    public void openLoginActivity() {
        IntentManager.startActivity(getApplicationContext(), LoginActivity.class);
    }

}
