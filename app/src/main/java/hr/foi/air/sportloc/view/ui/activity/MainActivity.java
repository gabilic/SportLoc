package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.UserModel;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.view.util.InternalStorageManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        retrieveUserData();
    }

    @OnClick(R.id.btn_account)
    public void openLoginActivity() {
        IntentManager.startActivity(getApplicationContext(), LoginActivity.class);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void retrieveUserData() {
        UserModel user = (UserModel) InternalStorageManager.readObject(getApplicationContext());
        if (user != null) {
            ActiveUserModel.getInstance().setActiveUser(user);
            IntentManager.startActivity(getApplicationContext(), NavigationDrawerActivity.class);
        }
    }

}
