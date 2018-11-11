package hr.foi.air.sportloc.view.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.view.adapter.EventDetailsPageAdapter;
import hr.foi.air.sportloc.view.ui.fragment.EventDetailsFragment;
import hr.foi.air.sportloc.view.ui.fragment.EventMembersFragment;

public class EventDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        EventDetailsPageAdapter eventDetailsPageAdapter = new EventDetailsPageAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tab_layout_event_details);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager pager){
        EventDetailsPageAdapter adapter = new EventDetailsPageAdapter(getSupportFragmentManager());

        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        eventDetailsFragment.setArguments(getIntent().getExtras());
        adapter.addFragment(eventDetailsFragment, getResources().getString(R.string.title_activity_event_details));
        adapter.addFragment(new EventMembersFragment(), getResources().getString(R.string.title_activity_event_members));

        pager.setAdapter(adapter);

    }

}
