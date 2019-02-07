package hr.foi.air.sportloc.view.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.FragmentEventListBinding;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.sportloc.service.model.EventFilterModel;
import hr.foi.air.core.EventModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.serviceUtil.DataUtil;
import hr.foi.air.sportloc.view.adapter.EventListAdapter;
import hr.foi.air.sportloc.view.ui.activity.EventDetailsActivity;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.IntentManager;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.EventViewModel;

public class EventListFragment extends Fragment {
    private FragmentEventListBinding binding;
    private EventViewModel viewModel;
    private EventListAdapter adapter;
    private FragmentActivity activity;

    public EventListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false);
        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        loadEvents();
    }

    @OnClick(R.id.fab_create_event)
    public void createNewEvent() {
        IntentManager.startActivity(activity.getApplicationContext(), EventDetailsActivity.class, Constants.CREATE_NEW_EVENT, true);
    }

    private EventFilterModel createFilter() {
        EventFilterModel filter = new EventFilterModel();
        filter.setUserId(ActiveUserModel.getInstance().getActiveUser().getUserId());
        if (getArguments() != null) {
            filter.setOwner(getArguments().getBoolean(Constants.OWNER));
        }
        filter.setFull(true);
        return filter;
    }

    private void loadEvents() {
        if (getArguments() != null && getArguments().getParcelableArray(Constants.EVENTS) != null) {
            Parcelable[] array = getArguments().getParcelableArray(Constants.EVENTS);
            //For some reason humane conversion of Parcelable array does not work...
            setupAdapter(Arrays.copyOf(array, array.length, EventModel[].class));
        } else {
            viewModel.getEvents(createFilter());
            observeViewModel(viewModel);
        }
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
        IntentManager.startActivity(activity.getApplicationContext(), EventDetailsActivity.class, ModelEnum.EventModel.name(), event);
    }

    private void setupAdapter(EventModel[] events) {
        adapter = new EventListAdapter(new ArrayList<>(Arrays.asList(events)), this::manageClickedEvent, this::openEventDetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext());
        DividerItemDecoration decoration = new DividerItemDecoration(activity.getApplicationContext(), LinearLayoutManager.VERTICAL);
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
                DataUtil.getInstance().setEvents(events);
            } else {
                MessageSender.sendError(activity.getApplicationContext(), getResources().getString(R.string.general_connection_error));
            }
        });
    }
}
