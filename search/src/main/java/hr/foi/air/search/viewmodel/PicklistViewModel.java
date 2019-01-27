package hr.foi.air.search.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.search.model.LocationModel;
import hr.foi.air.search.model.SportModel;
import hr.foi.air.search.model.WebServiceCaller;

public class PicklistViewModel extends ViewModel {

    private LiveData<SportModel[]> sportsObservable;
    private LiveData<LocationModel[]> locationObservable;

    public void getSports() {
        sportsObservable = WebServiceCaller.getInstance().getSports();
    }

    public LiveData<SportModel[]> getSportsObservable() {
        return sportsObservable;
    }

    public void getLocations() {
        locationObservable = WebServiceCaller.getInstance().getLocations();
    }

    public LiveData<LocationModel[]> getLocationObservable() {
        return locationObservable;
    }
}
