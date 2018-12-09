package hr.foi.air.sportloc.view.ui.fragment;

import android.content.Context;
import android.databinding.InverseMethod;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import hr.foi.air.sportloc.R;
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.view.util.DateTimeHelper;

public class EventDetailsEditFragment {

    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.et_title)
    EditText etTitle;
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


    private static Context context;
    private EventDetailsFragment rootFragment;
    private EventModel newEvent;
    private boolean isNewEvent = false;

    public EventDetailsEditFragment(EventDetailsFragment rootFragment) {
        this.context = rootFragment.getActivity();
        this.rootFragment = rootFragment;
        newEvent = new EventModel();

    }

    @OnClick(R.id.btn_cancel_edit)
    public void cancelEdit() {
        rootFragment.switchMode(true);
    }

    @OnClick(R.id.btn_save_event)
    public void saveEvent() {
        //TODO check if data is valid
        if (isNewEvent) {
            //TODO save event...
        } else {
            rootFragment.setEvent(newEvent);
            rootFragment.switchMode(false);
        }
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
        newEvent.setEndDate(tvEndDate.getText().toString());
    }

    @OnTextChanged(R.id.tv_start_date)
    public void setStartDate() {
        newEvent.setStartDate(tvStartDate.getText().toString());
    }

    @OnTextChanged(R.id.et_title)
    public void setTitle() {
        newEvent.setTitle(etTitle.getText().toString());
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
        newEvent.setMaxCapacity(capacity);
    }

    @OnItemSelected(R.id.spn_sport)
    public void setSport() {
        newEvent.setSport(spinnerSport.getSelectedItem().toString());
    }

    @OnItemSelected(R.id.spn_location)
    public void setLocation() {
        newEvent.setLocation(spinnerLocation.getSelectedItem().toString());
    }


    @OnClick(R.id.sw_is_open)
    public void changeEventStatus() {
        newEvent.setOpenEvent(!newEvent.isOpenEvent());
    }

    @InverseMethod("resolveStartText")
    public static String resolveStartText(String startDate) {
        return isValidString(startDate) ? startDate :
                context.getResources().getString(R.string.event_details_select_start);
    }

    @InverseMethod("resolveEndText")
    public static String resolveEndText(String endDate) {
        return isValidString(endDate) ? endDate :
                context.getResources().getString(R.string.event_details_select_end);
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

}
