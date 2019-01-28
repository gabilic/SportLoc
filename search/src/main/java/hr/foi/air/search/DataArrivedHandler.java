package hr.foi.air.search;

import java.io.Serializable;

import hr.foi.air.search.model.EventModel;

public interface DataArrivedHandler extends Serializable {
    void onDataArrived(EventModel[] eventsList);
}
