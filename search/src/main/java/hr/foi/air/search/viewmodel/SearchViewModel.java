package hr.foi.air.search.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.core.EventModel;
import hr.foi.air.search.model.EventFilterModel;
import hr.foi.air.search.model.WebServiceCaller;

public class SearchViewModel extends ViewModel {
    private LiveData<Boolean> eventObservable;
    private LiveData<EventModel[]> eventsObservable;

    public void getEvents(EventFilterModel filter) {
        eventsObservable = (WebServiceCaller.getInstance().getEvents(filter));
    }

    public LiveData<Boolean> getEventObservable() {
        return eventObservable;
    }

    public LiveData<EventModel[]> getEventsObservable() {
        return eventsObservable;
    }

}
