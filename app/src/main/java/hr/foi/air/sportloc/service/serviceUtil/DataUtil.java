package hr.foi.air.sportloc.service.serviceUtil;

import hr.foi.air.core.EventModel;
import hr.foi.air.core.EventSearch;
import hr.foi.air.filtersearch.FilterSearchImpl;
import hr.foi.air.core.LocationModel;
import hr.foi.air.core.SportModel;

public class DataUtil {

    private static DataUtil instance;

    private LocationModel[] locations;
    private SportModel[] sports;
    private EventSearch eventSearch;
    private EventModel[] events;

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

    public EventSearch getEventSearch() {
        return eventSearch != null ? eventSearch : new FilterSearchImpl();
    }

    public void setEventSearch(EventSearch eventSearch) {
        this.eventSearch = eventSearch;
    }

    public EventModel[] getEvents() {
        return events;
    }

    public void setEvents(EventModel[] events) {
        this.events = events;
    }
}
