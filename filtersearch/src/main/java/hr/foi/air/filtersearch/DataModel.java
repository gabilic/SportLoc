package hr.foi.air.filtersearch;

import android.os.Parcel;
import android.os.Parcelable;

import hr.foi.air.core.EventModel;
import hr.foi.air.core.LocationModel;
import hr.foi.air.core.SportModel;

public class DataModel implements Parcelable {

    private SportModel[] sports;
    private LocationModel[] locations;
    private EventModel[] events;

    public DataModel() {

    }

    protected DataModel(Parcel in) {
        sports = (SportModel[]) in.readArray(SportModel.class.getClassLoader());
        locations = (LocationModel[]) in.readArray(LocationModel.class.getClassLoader());
        events = (EventModel[]) in.readArray(EventModel.class.getClassLoader());
    }

    public SportModel[] getSports() {
        return sports;
    }

    public void setSports(SportModel[] sports) {
        this.sports = sports;
    }

    public LocationModel[] getLocations() {
        return locations;
    }

    public void setLocations(LocationModel[] locations) {
        this.locations = locations;
    }

    public EventModel[] getEvents() {
        return events;
    }

    public void setEvents(EventModel[] events) {
        this.events = events;
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(sports);
        dest.writeArray(locations);
        dest.writeArray(events);
    }
}
