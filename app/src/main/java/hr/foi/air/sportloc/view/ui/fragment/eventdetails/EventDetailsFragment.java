package hr.foi.air.sportloc.view.ui.fragment.eventdetails;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.databinding.FragmentEventDetailsBinding;
import hr.foi.air.sportloc.databinding.FragmentEventDetailsEditBinding;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.core.EventModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.view.adapter.LocationArrayAdapter;
import hr.foi.air.sportloc.view.adapter.SportArrayAdapter;
import hr.foi.air.sportloc.view.util.Constants;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.EventViewModel;
import hr.foi.air.sportloc.viewmodel.PicklistViewModel;

public class EventDetailsFragment extends Fragment implements OnMapReadyCallback {

    private Unbinder unbinder;

    @BindView(R.id.btn_event_options)
    Button btnEventOptions;

    private EventModel event;
    private boolean editMode = false;
    private boolean newEvent = false;

    private ButtonState currentState = ButtonState.JOIN;
    private Object target;

    private enum ButtonState {
        JOIN, LEAVE, EDIT
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() == null) {
            return null;
        }
        ViewDataBinding binding;

        target = this;
        if (!editMode && !isNewEvent()) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false);
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details_edit, container, false);
            target = new EventDetailsEditFragment(this);
        }

        View view = binding.getRoot();
        unbinder = ButterKnife.bind(target, view);
        loadInitialData(binding, getArguments());

        return view;
    }

    private boolean isNewEvent() {
        if (getArguments() != null) {
            newEvent = getArguments().getBoolean(Constants.CREATE_NEW_EVENT);
            editMode = newEvent;
        }
        return newEvent;
    }

    private void loadInitialData(ViewDataBinding binding, Bundle bundle) {
        event = bundle.getParcelable(ModelEnum.EventModel.name());

        if (binding instanceof FragmentEventDetailsBinding) {
            ((FragmentEventDetailsBinding) binding).setEvent(event);
        } else if (binding instanceof FragmentEventDetailsEditBinding) {
            resolveEventEditBinding((FragmentEventDetailsEditBinding) binding);
            ((EventDetailsEditFragment) target).setNewEvent(event);
        }
        if (!editMode) {
            resolveEventButton();
        }
    }

    private void resolveEventEditBinding(FragmentEventDetailsEditBinding binding) {
        binding.setEvent(event);
        PicklistViewModel viewModel = ViewModelProviders.of(getActivity()).get(PicklistViewModel.class);
        loadLocations(binding, viewModel);
        loadSports(binding, viewModel);
    }

    private void loadLocations(final FragmentEventDetailsEditBinding binding, PicklistViewModel viewModel) {
        viewModel.getLocations();
        viewModel.getLocationObservable().observe(this, result -> {
            if (result != null) {
                binding.setLocationAdapter(
                        new LocationArrayAdapter(getActivity(),
                                android.R.layout.simple_spinner_item,
                                result));
            } else {
                MessageSender.sendError(getActivity(), getResources().getString(R.string.general_connection_error));
            }
        });
    }

    private void loadSports(final FragmentEventDetailsEditBinding binding, PicklistViewModel viewModel) {
        viewModel.getSports();
        viewModel.getSportsObservable().observe(this, result -> {
            if (result != null) {
                binding.setSportAdapter(
                        new SportArrayAdapter(getActivity(),
                                android.R.layout.simple_spinner_item,
                                result));
            } else {
                MessageSender.sendError(getActivity(), getResources().getString(R.string.general_connection_error));
            }
        });

    }

    private void resolveEventButton() {
        //TODO check if creator and check if application is already sent
        boolean isCreator = ActiveUserModel.getInstance().getActiveUser().getUsername().equalsIgnoreCase(event.getUsername());
        boolean isMember = false;
        if (isCreator) {
            btnEventOptions.setText(R.string.btn_edit);
            currentState = ButtonState.EDIT;
        } else if (isMember) {
            btnEventOptions.setText(R.string.event_details_leave);
            currentState = ButtonState.LEAVE;
        }
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(Objects.requireNonNull(getActivity()).getApplicationContext());
        LatLng result = null;

        try {
            List<Address> address = coder.getFromLocationName(strAddress, 1);
            if (address != null && !address.isEmpty()) {
                Address location = address.get(0);
                result = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (event.getAddress() != null && !event.getAddress().trim().isEmpty()) {
            LatLng eventLocation = getLocationFromAddress(event.getAddress());
            if (eventLocation != null) {
                map.addMarker(new MarkerOptions().position(eventLocation).title(event.getName()));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15.0f));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.btn_event_options)
    public void eventButtonClick() {
        switch (currentState) {
            case JOIN:
                joinEvent();
                break;
            case LEAVE:
                leaveEvent();
                break;
            case EDIT:
                switchMode();
                break;
            default:
                break;
        }
    }

    private void leaveEvent() {
        EventViewModel vm = new EventViewModel();
        vm.leaveEvent(getParticipant());
        vm.getEventObservable().observe(this, result -> {
            if (result) {
                MessageSender.sendMessage(getActivity(), getResources().getString(R.string.event_details_leave_event));
            } else {
                MessageSender.sendError(getActivity(), getResources().getString(R.string.general_connection_error));
            }
        });

        currentState = ButtonState.JOIN;
        btnEventOptions.setText(R.string.event_details_join);
    }

    private ParticipantModel getParticipant() {
        ParticipantModel participant = new ParticipantModel();
        participant.setEventId(event.getEventId());
        participant.setUserId(ActiveUserModel.getInstance().getActiveUser().getUserId());
        return participant;
    }

    private void joinEvent() {
        EventViewModel vm = new EventViewModel();
        vm.joinEvent(getParticipant(), event.isOpen());
        vm.getEventObservable().observe(this, result -> {
            if (result) {
                MessageSender.sendMessage(getActivity(), getResources().getString(R.string.event_details_request_sent));
            } else {
                MessageSender.sendError(getActivity(), getResources().getString(R.string.general_connection_error));
            }
        });
        currentState = ButtonState.LEAVE;
        btnEventOptions.setText(R.string.event_details_leave);
    }

    public void switchMode(boolean cancel, boolean save) {
        if (cancel && newEvent) {
            getFragmentManager().popBackStack();
            getActivity().finish();
        } else {
            if(save) {
                getArguments().clear();
                getArguments().putParcelable(ModelEnum.EventModel.name(),event);
            }
            editMode = !editMode;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public void switchMode() {
        switchMode(false, false);
    }

    public void switchMode(boolean cancel) {
        switchMode(cancel, false);
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }
}
