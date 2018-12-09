package hr.foi.air.sportloc.service.serviceUtil;

import hr.foi.air.sportloc.service.model.LocationModel;
import hr.foi.air.sportloc.service.model.SportModel;

public class DataUtil {

    private static DataUtil instance;

    private LocationModel[] locations;
    private SportModel[] sports;

    private DataUtil() {
    }

    public static synchronized DataUtil getInstance() {
        if (instance == null) {
            instance = new DataUtil();
        }
        return instance;
    }

    public LocationModel[] getLocations() {
        return locations;
    }

    public void setLocations(LocationModel[] locations) {
        this.locations = locations;
    }

    public SportModel[] getSports() {
        return sports;
    }

    public void setSports(SportModel[] sports) {
        this.sports = sports;
    }
}
