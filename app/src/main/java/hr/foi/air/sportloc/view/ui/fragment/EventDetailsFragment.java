package hr.foi.air.sportloc.view.ui.fragment;


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
import android.widget.ScrollView;
import android.widget.TextView;

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
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.service.model.ModelEnum;
import hr.foi.air.sportloc.view.adapter.LocationArrayAdapter;
import hr.foi.air.sportloc.view.adapter.SportArrayAdapter;
import hr.foi.air.sportloc.view.util.DataUtil;

public class EventDetailsFragment extends Fragment implements OnMapReadyCallback {

    private Unbinder unbinder;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sport_location_capacity)
    TextView tvSportLocationCapacity;
    @BindView(R.id.dvd_horizontal)
    View dvdHorizontal;
    @BindView(R.id.tv_creator)
    TextView lblCreator;
    @BindView(R.id.tv_creator_username)
    TextView tvCreatorUsername;
    @BindView(R.id.tv_lbl_address)
    TextView lblAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_lbl_start_time)
    TextView lblStartTime;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_lbl_end_time)
    TextView lblEndTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_lbl_description)
    TextView lblDescription;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_event_options)
    Button btnEventOptions;
    @BindView(R.id.tv_lbl_map)
    TextView lblMap;
    @BindView(R.id.sv_event_details)
    ScrollView scrollView;

    private EventModel event;
    private boolean editMode = false;

    private enum ButtonState {
        JOIN, LEAVE, EDIT
    }

    private ButtonState currentState = ButtonState.JOIN;

    public EventDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewDataBinding binding;
        Object target = this;
        if (!editMode) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false);
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details_edit, container, false);
            target = new EventDetailsEditFragment(getActivity());
        }
        View view = binding.getRoot();
        unbinder = ButterKnife.bind(target, view);

        loadInitialData(binding, getArguments());

        return view;
    }

    private void loadInitialData(ViewDataBinding binding, Bundle bundle) {
        if (bundle != null) {
            event = bundle.getParcelable(ModelEnum.EventModel.name());
            if (binding instanceof FragmentEventDetailsBinding) {
                ((FragmentEventDetailsBinding) binding).setEvent(event);
            } else if (binding instanceof FragmentEventDetailsEditBinding) {
                FragmentEventDetailsEditBinding eventBinding = ((FragmentEventDetailsEditBinding) binding);
                eventBinding.setEvent(event);
                eventBinding.setLocationAdapter(
                        new LocationArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_item,
                        DataUtil.getInstance().getAvailableLocations()));
                eventBinding.setSportAdapter(
                        new SportArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_item,
                        DataUtil.getInstance().getAvailableSports()));
            }
            if (!editMode) {
                resolveEventButton();
            }
        }
    }

    private void resolveEventButton() {
        //TODO check if creator and check if application is already sent
        boolean isCreator = true;
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
        LatLng eventLocation = getLocationFromAddress(event.getAddress());
        if (eventLocation != null) {
            map.addMarker(new MarkerOptions().position(eventLocation).title(event.getTitle()));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15.0f));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_event_options)
    public void eventButtonClick() {
        switch (currentState) {
            case JOIN:
                currentState = ButtonState.LEAVE;
                btnEventOptions.setText(R.string.event_details_leave);
                break;
            case LEAVE:
                currentState = ButtonState.JOIN;
                btnEventOptions.setText(R.string.event_details_join);
                break;
            case EDIT:
                switchToEditMode();
//                Toast.makeText(getActivity(), currentState.name(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    private void switchToEditMode() {

        editMode = true;
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
}
