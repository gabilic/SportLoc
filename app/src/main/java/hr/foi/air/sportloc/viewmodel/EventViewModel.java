package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.service.serviceUtil.ActionEnum;

public class EventViewModel {
    private LiveData<Boolean> eventObservable;

    public void createEvent(EventModel event) {
        eventObservable = WebServiceCaller.getInstance().createEvent(event);
    }

    public void updateEvent(EventModel event) {
        eventObservable = WebServiceCaller.getInstance().updateEvent(event);
    }
    public void joinEvent(ParticipantModel participant) {
        participant.setAction(ActionEnum.SEND_REQUEST);
        eventObservable = WebServiceCaller.getInstance().resolveParticipant(participant);
    }
    public void leaveEvent(ParticipantModel participant) {
        participant.setAction(ActionEnum.REMOVE_USER);
        eventObservable = WebServiceCaller.getInstance().resolveParticipant(participant);
    }

    public LiveData<Boolean> getEventObservable() {
        return eventObservable;
    }

}
