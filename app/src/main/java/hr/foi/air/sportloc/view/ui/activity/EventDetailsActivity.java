package hr.foi.air.sportloc.view.ui.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.foi.air.core.EventModel;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.view.adapter.EventDetailsPageAdapter;
import hr.foi.air.sportloc.view.ui.fragment.eventdetails.EventDetailsFragment;
import hr.foi.air.sportloc.view.ui.fragment.EventMembersFragment;
import hr.foi.air.sportloc.viewmodel.EventParticipantViewModel;

import static hr.foi.air.sportloc.view.util.Constants.EVENT_PARTICIPANTS;

public class EventDetailsActivity extends AppCompatActivity {

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tab_layout_event_details)
    TabLayout tabLayout;

    private EventDetailsPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);
        getEventMembers();
    }

    private void getEventMembers(){
        EventParticipantViewModel eventParticipantViewModel = new EventParticipantViewModel();
        eventParticipantViewModel.getParticipants(((EventModel)getIntent().getExtras().getParcelable(ModelEnum.EventModel.name())).getEventId());
        eventParticipantViewModel.getParticipantsObservable().observe(this, result -> {
            ArrayList<ParticipantModel> participants = new ArrayList<>();
            participants.addAll(result);
            Bundle bundle = getIntent().getExtras();
            bundle.putParcelableArrayList(EVENT_PARTICIPANTS, participants);
            getIntent().putExtras(bundle);
            setupTabLayout();
        });
    }

    private void setupTabLayout() {
        EventDetailsPageAdapter adapter = new EventDetailsPageAdapter(getSupportFragmentManager());
        EventMembersFragment eventMembersFragment = new EventMembersFragment();
        eventMembersFragment.setArguments(getIntent().getExtras());
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        eventDetailsFragment.setArguments(getIntent().getExtras());

        adapter.addFragment(eventDetailsFragment, getResources().getString(R.string.title_activity_event_details));
        adapter.addFragment(eventMembersFragment, getResources().getString(R.string.title_activity_event_members));
        adapter = new EventDetailsPageAdapter(getSupportFragmentManager());

//        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
//        eventDetailsFragment.setArguments(getIntent().getExtras());
//        adapter.addFragment(eventDetailsFragment, getResources().getString(R.string.title_activity_event_details));
        //adapter.addFragment(new EventMembersFragment(), getResources().getString(R.string.title_activity_event_members));

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    public void addMembersAdapter(){
       // EventDetailsPageAdapter adapter = new EventDetailsPageAdapter(childFragmentManager);
        adapter.addFragment(new EventMembersFragment(), getResources().getString(R.string.title_activity_event_members));
        adapter.notifyDataSetChanged();
        //viewPager.setAdapter(adapter);
    }
}
