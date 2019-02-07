package hr.foi.air.core;

import android.support.v4.app.FragmentManager;

public interface EventSearch {
    void setEvents(EventModel[] events);
    void setLocations(LocationModel[] locations);
    void setSports(SportModel[] sports);
    void startSearch(FragmentManager applicationContext, int replaceFragment);
    void setDataArrivedHandler(DataArrivedHandler dataArrivedHandler);
}
