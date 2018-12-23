package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.LocationModel;
import hr.foi.air.sportloc.service.model.SportModel;

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
