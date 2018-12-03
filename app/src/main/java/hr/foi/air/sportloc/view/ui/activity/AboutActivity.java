package hr.foi.air.sportloc.view.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_contact_us)
    public void SendEmail() {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"sportloc@heroku.com"});
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Izaberite aplikaciju za slanje e-maila:"));
    }
}
