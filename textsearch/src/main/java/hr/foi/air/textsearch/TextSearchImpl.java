package hr.foi.air.textsearch;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;

import hr.foi.air.core.DataArrivedHandler;
import hr.foi.air.core.EventModel;
import hr.foi.air.core.EventSearch;
import hr.foi.air.core.LocationModel;
import hr.foi.air.core.SportModel;

public class TextSearchImpl implements EventSearch {

    public final static String EVENTS= "events";
    private EventModel[] events = new EventModel[0];
    private static DataArrivedHandler dataArrivedHandler;

    @Override
    public void setEvents(EventModel[] events) {
        this.events = events;
    }

    @Override
    public void setLocations(LocationModel[] locations){
    }

    @Override
    public void setSports(SportModel[] sports) {
    }

    @Override
    public void setDataArrivedHandler(DataArrivedHandler dataArrivedHandler) {
        this.dataArrivedHandler = dataArrivedHandler;
    }

    @Override
    public void startSearch(FragmentManager fragmentManager, int replaceFragment) {
        TextSearchFragment fragment = new TextSearchFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArray(EVENTS, events);

        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(replaceFragment, fragment)
                .commit();
    }

    public static void returnResult(EventModel[] resultArray){
        dataArrivedHandler.onDataArrived(resultArray);
    }
}
