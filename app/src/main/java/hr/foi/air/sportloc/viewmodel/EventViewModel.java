package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.EventModel;

public class EventViewModel {
    private LiveData<Boolean> eventObservable;

    public void createEvent(EventModel event) {
        eventObservable = WebServiceCaller.getInstance().createEvent(event);
    }

    public LiveData<Boolean> getEventObservable() {
        return eventObservable;
    }

}
