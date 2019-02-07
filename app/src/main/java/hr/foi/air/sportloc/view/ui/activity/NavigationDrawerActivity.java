package hr.foi.air.sportloc.view.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import butterknife.ButterKnife;
import hr.foi.air.core.EventSearch;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.ActivityEventListBinding;
import hr.foi.air.sportloc.databinding.NavHeaderBinding;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.serviceUtil.DataUtil;
import hr.foi.air.sportloc.view.ui.fragment.EventListFragment;
import hr.foi.air.sportloc.view.ui.fragment.SettingsFragment;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.view.util.InternalStorageManager;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.PicklistViewModel;

public class NavigationDrawerActivity extends AppCompatActivity {
    private ActivityEventListBinding activityEventListBinding;
    private NavHeaderBinding navHeaderBinding;

    boolean dataArrived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEventListBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_list);
        navHeaderBinding = NavHeaderBinding.bind(activityEventListBinding.nvDrawer.getHeaderView(0));
        ButterKnife.bind(this);
        setSupportActionBar(activityEventListBinding.tlbrGeneral);
        initializeEventListFragment(savedInstanceState);
        initializeActivity();
    }

    @Override
    public void onBackPressed() {
        if (activityEventListBinding.dlDrawer.isDrawerOpen(GravityCompat.START)) {
            activityEventListBinding.dlDrawer.closeDrawers();
        } else {
            finishAffinity();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            activityEventListBinding.dlDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeEventListFragment(Bundle savedInstanceState) {
        activityEventListBinding.nvDrawer.setCheckedItem(R.id.nav_event_list);
        if (savedInstanceState == null) {
            EventListFragment newEventList = new EventListFragment();
            newEventList.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cl_fragment_holder, newEventList)
                    .commit();
        }
    }

    private <T extends Fragment> void startNewFragment(T fragment, Bundle args) {
        fragment.setArguments(args);
        startNewFragment(fragment);
    }

    private <T extends Fragment> void startNewFragment(T fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cl_fragment_holder, fragment)
                .commit();
    }

    private boolean handleNavigation(MenuItem menuItem) {
        menuItem.setChecked(true);
        activityEventListBinding.dlDrawer.closeDrawers();
        Bundle args = new Bundle();
        switch (menuItem.getItemId()) {
            case R.id.nav_event_list:
                args.putBoolean(Constants.OWNER, false);
                startNewFragment(new EventListFragment(), args);
                break;
            case R.id.nav_new_event:
                IntentManager.startActivity(getApplicationContext(), EventDetailsActivity.class, Constants.CREATE_NEW_EVENT, true);
                break;
            case R.id.nav_my_events:
                args.putBoolean(Constants.OWNER, true);
                startNewFragment(new EventListFragment(), args);
                break;
            case R.id.nav_profile:
                IntentManager.startActivity(getApplicationContext(), ProfileActivity.class, Constants.RELOADED, true);
                break;
            case R.id.nav_about:
                IntentManager.startActivity(getApplicationContext(), AboutActivity.class);
                break;
            case R.id.nav_logout:
                InternalStorageManager.clearObject(getApplicationContext());
                IntentManager.startActivity(getApplicationContext(), MainActivity.class);
                break;
            case R.id.nav_search_filter:
                openSearch();
                break;
            case R.id.nav_settings:
                startNewFragment(new SettingsFragment());
                break;
        }
        return true;
    }

    private void openSearch() {
        EventSearch search = DataUtil.getInstance().getEventSearch();
        search.setEvents(DataUtil.getInstance().getEvents());
        search.setDataArrivedHandler(result ->
                IntentManager.startActivity(getApplicationContext(), NavigationDrawerActivity.class, Constants.EVENTS, result));
        fetchData(search);

    }

    private void fetchData(EventSearch search) {
        PicklistViewModel viewModel = new PicklistViewModel();
        viewModel.getLocations();
        viewModel.getSports();
        viewModel.getSportsObservable().observe(this, result -> {
            search.setSports(result);
            DataUtil.getInstance().setSports(result);
            initSearch(search);
            dataArrived = true;
        });
        viewModel.getLocationObservable().observe(this, result -> {
            search.setLocations(result);
            DataUtil.getInstance().setLocations(result);
            initSearch(search);
            dataArrived = true;
        });
    }

    private void initSearch(EventSearch search) {
        if (dataArrived) {
            search.startSearch(getSupportFragmentManager(), R.id.cl_fragment_holder);
        }
    }

    private void initializeActivity() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navHeaderBinding.setUser(ActiveUserModel.getInstance().getActiveUser());
        activityEventListBinding.nvDrawer.setNavigationItemSelectedListener(this::handleNavigation);
        String message = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
        if (message != null && !message.isEmpty()) {
            MessageSender.sendMessage(getApplicationContext(), message);
        }
    }
}
