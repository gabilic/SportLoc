package hr.foi.air.search;

import hr.foi.air.search.model.EventModel;

public interface DataArrivedHandler {
    void onDataArrived(EventModel[] eventsList);
}
