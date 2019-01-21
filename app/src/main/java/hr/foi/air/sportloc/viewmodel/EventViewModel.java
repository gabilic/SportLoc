package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.EventFilterModel;
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.service.serviceUtil.ActionEnum;

public class EventViewModel extends ViewModel {
    private LiveData<Boolean> eventObservable;
    private LiveData<EventModel[]> eventsObservable;

    public void createEvent(EventModel event) {
        eventObservable = WebServiceCaller.getInstance().createEvent(event);
    }

    public void updateEvent(EventModel event) {
        eventObservable = WebServiceCaller.getInstance().updateEvent(event);
    }

    public void joinEvent(ParticipantModel participant, Boolean open) {
        participant.setAction(open ? ActionEnum.ENTER_EVENT : ActionEnum.SEND_REQUEST);
        eventObservable = WebServiceCaller.getInstance().resolveParticipant(participant);
    }

    public void leaveEvent(ParticipantModel participant) {
        participant.setAction(ActionEnum.REMOVE_USER);
        eventObservable = WebServiceCaller.getInstance().resolveParticipant(participant);
    }

    public void getEvents(EventFilterModel filter) {
        eventsObservable = WebServiceCaller.getInstance().getEvents(filter);
    }

    public LiveData<Boolean> getEventObservable() {
        return eventObservable;
    }

    public LiveData<EventModel[]> getEventsObservable() {
        return eventsObservable;
    }

}
