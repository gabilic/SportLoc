package hr.foi.air.filtersearch;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import hr.foi.air.core.DataArrivedHandler;
import hr.foi.air.core.EventModel;
import hr.foi.air.core.EventSearch;
import hr.foi.air.core.LocationModel;
import hr.foi.air.core.SportModel;

public class FilterSearchImpl implements EventSearch {

    public final static String DATA_MODEL = "dataModel";

    private DataModel dataModel = new DataModel();
    private static DataArrivedHandler dataArrivedHandler;

    @Override
    public void setEvents(EventModel[] events) {
        dataModel.setEvents(events);
    }

    @Override
    public void setLocations(LocationModel[] locations) {
        dataModel.setLocations(locations);
    }

    @Override
    public void setSports(SportModel[] sports) {
        dataModel.setSports(sports);
    }

    @Override
    public void setDataArrivedHandler(DataArrivedHandler dataArrivedHandler) {
        this.dataArrivedHandler = dataArrivedHandler;
    }

    @Override
    public void startSearch(FragmentManager fragmentManager, int replaceFragment) {
        SearchFragment fragment = new SearchFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(DATA_MODEL, dataModel);

        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(replaceFragment, fragment)
                .commit();
    }


    public static void returnResult(EventModel[] resultArray) {
        dataArrivedHandler.onDataArrived(resultArray);
    }
}
