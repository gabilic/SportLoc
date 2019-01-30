package hr.foi.air.search.model;

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
