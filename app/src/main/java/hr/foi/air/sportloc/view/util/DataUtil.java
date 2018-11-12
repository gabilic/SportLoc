package hr.foi.air.sportloc.view.util;

import hr.foi.air.sportloc.service.model.LocationModel;
import hr.foi.air.sportloc.service.model.SportModel;

public class DataUtil {

    private static DataUtil instance;

    private LocationModel[] availableLocations;
    private SportModel[] availableSports;

    private DataUtil(){}

    public static synchronized DataUtil getInstance(){
        if(instance==null){
            instance = new DataUtil();
        }
        return instance;
    }

    //TODO replace with actual data
    private void loadAvailableLocations(){
        LocationModel location1 = new LocationModel(1,"Iza žige");
        LocationModel location2 = new LocationModel(2,"Varazdin");
        LocationModel location3 = new LocationModel(3,"Daruvar");
        availableLocations = new LocationModel[]{location1, location2, location3};
    }

    private void loadAvailableSports() {
        SportModel sport1 = new SportModel(1,"Boćanje");
        SportModel sport2 = new SportModel(2,"Ekstremni šah");
        SportModel sport3 = new SportModel(3,"Kamen škare papir");
        availableSports = new SportModel[]{sport1, sport2, sport3};
    }

    public LocationModel[] getAvailableLocations() {
        if(availableLocations == null){
            loadAvailableLocations();
        }
        return availableLocations;
    }

    public void setAvailableLocations(LocationModel[] availableLocations) {
        this.availableLocations = availableLocations;
    }

    public SportModel[] getAvailableSports() {
        if(availableSports == null){
            loadAvailableSports();
        }
        return availableSports;
    }

    public void setAvailableSports(SportModel[] availableSports) {
        this.availableSports = availableSports;
    }
}
