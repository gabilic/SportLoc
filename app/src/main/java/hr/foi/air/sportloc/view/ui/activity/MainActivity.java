package hr.foi.air.sportloc.view.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.EventModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @OnClick(R.id.btn_account)
    public void openActivity(){
//     Intent intent = new Intent(MainActivity.this, EventDetailsActivity.class);
//
//     EventModel event = new EventModel();
//     event.setCreatorUserName("KRUMPIR");
//     event.setSport("SPORT");
//     event.setTitle("NASLOV");
//     event.setLocation("LOKACIJA");
//     event.setAddress("Pavlinska 2, Varaždin");
//     event.setOpenEvent(true);
//     intent.putExtra(ModelEnum.EventModel.name(), event);
//
//     startActivity(intent);
    }
}
