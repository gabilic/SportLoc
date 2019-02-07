package hr.foi.air.sportloc.view.ui.fragment.eventdetails;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.InverseMethod;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.ActiveUserModel;
import hr.foi.air.core.EventModel;
import hr.foi.air.core.LocationModel;
import hr.foi.air.core.SportModel;
import hr.foi.air.sportloc.view.util.DateTimeHelper;
import hr.foi.air.sportloc.view.util.MessageSender;
import hr.foi.air.sportloc.viewmodel.EventViewModel;

public class EventDetailsEditFragment implements LifecycleOwner {

    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_description)
    EditText etDescription;
    @BindView(R.id.et_capacity)
    EditText etCapacity;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.sw_is_open)
    Switch isOpen;
    @BindView(R.id.spn_location)
    Spinner spinnerLocation;
    @BindView(R.id.spn_sport)
    Spinner spinnerSport;


    private static Resources resources;
    private final Context context;
    private final EventDetailsFragment rootFragment;
    private final FragmentManager fragmentManager;
    private EventModel newEvent;
    private boolean isNewEvent = false;
    private final LifecycleRegistry lifecycleRegistry;

    public EventDetailsEditFragment(EventDetailsFragment rootFragment) {
        this.context = rootFragment.getActivity();
        this.fragmentManager = rootFragment.getFragmentManager();
        this.rootFragment = rootFragment;
        resources = rootFragment.getActivity().getResources();
        newEvent = new EventModel();
        lifecycleRegistry = new LifecycleRegistry(this);
        lifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @OnClick(R.id.btn_cancel_edit)
    public void cancelEdit() {
        rootFragment.switchMode(true);
    }

    @OnClick(R.id.btn_save_event)
    public void saveChanges() {
        //TODO check if data is valid
        if (isNewEvent) {
            createEvent();
        } else {
            updateEvent();
        }
    }

    private void updateEvent() {
        EventViewModel vm = new EventViewModel();
        vm.updateEvent(newEvent);
        vm.getEventObservable().observe(this, result -> {
            if (Boolean.TRUE.equals(result)) {
                lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
                switchToViewMode(false, true);
            } else {
                MessageSender.sendError(context, resources.getString(R.string.general_connection_error));
            }
        });
        switchToViewMode(true,false);
    }

    private void switchToViewMode(boolean cancel, boolean save) {
        rootFragment.setEvent(newEvent);
        rootFragment.switchMode(cancel, save);
    }

    private void createEvent() {
        newEvent.setUserId(ActiveUserModel.getInstance().getActiveUser().getUserId());
        newEvent.setUsername(ActiveUserModel.getInstance().getActiveUser().getUsername());
        lifecycleRegistry.markState(Lifecycle.State.STARTED);
        EventViewModel vm = new EventViewModel();
        vm.createEvent(newEvent);
        vm.getEventObservable().observe(this, result -> {
            if (Boolean.TRUE.equals(result)) {
                lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
                switchToViewMode(false, true);
            } else {
                MessageSender.sendError(context, resources.getString(R.string.general_connection_error));
            }
        });
    }

    @OnClick(R.id.tv_start_date)
    public void editStartDate() {
        new DateTimeHelper().showDateTimeDialog(context, tvStartDate);
    }

    @OnClick(R.id.tv_end_date)
    public void editEndDate() {
        new DateTimeHelper().showDateTimeDialog(context, tvEndDate);
    }

    @OnTextChanged(R.id.tv_end_date)
    public void setEndDate() {
        if (!resources.getString(R.string.event_details_select_end).equals(tvEndDate.getText().toString())) {
            newEvent.setEnd(tvEndDate.getText().toString());
        }
    }

    @OnTextChanged(R.id.tv_start_date)
    public void setStartDate() {
        if (!resources.getString(R.string.event_details_select_start).equals(tvStartDate.getText().toString())) {
            newEvent.setStart(tvStartDate.getText().toString());
        }
    }

    @OnTextChanged(R.id.et_name)
    public void setName() {
        newEvent.setName(etName.getText().toString());
    }

    @OnTextChanged(R.id.et_description)
    public void setDescription() {
        newEvent.setDescription(etDescription.getText().toString());
    }

    @OnTextChanged(R.id.et_address)
    public void setAddress() {
        newEvent.setAddress(etAddress.getText().toString());
    }

    @OnTextChanged(R.id.et_capacity)
    public void setCapacity() {
        int capacity = etCapacity.getText() != null && !etCapacity.getText().toString().isEmpty() ?
                Integer.valueOf(etCapacity.getText().toString()) : 0;
        newEvent.setCapacity(capacity);
    }

    @OnItemSelected(R.id.spn_sport)
    public void setSport() {
        SportModel sport = (SportModel)spinnerSport.getSelectedItem();
        newEvent.setSport(sport.getName());
        newEvent.setSportId(sport.getId());
    }

    @OnItemSelected(R.id.spn_location)
    public void setLocation() {
        LocationModel location = (LocationModel)spinnerLocation.getSelectedItem();
        newEvent.setCity(location.getName());
        newEvent.setCityId(location.getId());
    }


    @OnClick(R.id.sw_is_open)
    public void changeEventStatus() {
        newEvent.setOpen(!newEvent.isOpen());
    }

    @InverseMethod("resolveStartText")
    public static String resolveStartText(String startDate) {
        return isValidString(startDate) ? startDate :
                resources.getString(R.string.event_details_select_start);
    }

    @InverseMethod("resolveEndText")
    public static String resolveEndText(String endDate) {
        return isValidString(endDate) ? endDate :
                resources.getString(R.string.event_details_select_end);
    }

    private static boolean isValidString(String value) {
        return value != null && !value.isEmpty();
    }

    public void setNewEvent(EventModel newEvent) {
        if (newEvent == null) {
            isNewEvent = true;
        } else {
            this.newEvent = newEvent;
        }
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
