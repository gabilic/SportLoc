package hr.foi.air.sportloc.view.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.ActivityEventListBinding;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.EventFilterModel;
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.view.adapter.EventListAdapter;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.EventViewModel;

public class EventListActivity extends AppCompatActivity {
    private ActivityEventListBinding binding;
    private EventViewModel viewModel;
    private EventListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_list);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        initializeActivity();
    }

    @OnClick(R.id.fab_create_event)
    public void openEventDetailsActivity() {
        IntentManager.startActivity(getApplicationContext(), EventDetailsActivity.class, Constants.CREATE_NEW_EVENT, true);
    }

    private void initializeActivity() {
        String message = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
        if (message != null && !message.isEmpty()) {
            MessageSender.sendMessage(getApplicationContext(), message);
        }
        loadEvents();
    }

    private EventFilterModel createFilter() {
        EventFilterModel filter = new EventFilterModel();
        filter.setUserId(ActiveUserModel.getInstance().getActiveUser().getUserId());
        filter.setFull(true);
        return filter;
    }

    private void loadEvents() {
        viewModel.getEvents(createFilter());
        observeViewModel(viewModel);
    }

    private void manageClickedEvent(int position) {
        if (EventListAdapter.getLastExpandedPosition() >= 0 && EventListAdapter.getLastExpandedPosition() != position) {
            adapter.getEventList().get(EventListAdapter.getLastExpandedPosition()).setExpanded(false);
            adapter.notifyDataSetChanged();
        }
        EventListAdapter.setLastExpandedPosition(position);
        adapter.getEventList().get(position).setExpanded(!adapter.getEventList().get(position).isExpanded());
        adapter.notifyDataSetChanged();
    }

    private void openEventDetails(EventModel event) {
        IntentManager.startActivity(getApplicationContext(), EventDetailsActivity.class, ModelEnum.EventModel.name(), event);
    }

    private void setupAdapter(EventModel[] events) {
        adapter = new EventListAdapter(new ArrayList<>(Arrays.asList(events)), this::manageClickedEvent, this::openEventDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        Drawable divider = ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null);
        if (divider != null) {
            decoration.setDrawable(divider);
        }
        binding.rvEventList.setLayoutManager(layoutManager);
        binding.rvEventList.addItemDecoration(decoration);
        binding.rvEventList.setAdapter(adapter);
    }

    private void observeViewModel(EventViewModel viewModel) {
        viewModel.getEventsObservable().observe(this, events -> {
            if (events != null) {
                setupAdapter(events);
            } else {
                MessageSender.sendError(getApplicationContext(), getResources().getString(R.string.general_connection_error));
            }
        });
    }
}
