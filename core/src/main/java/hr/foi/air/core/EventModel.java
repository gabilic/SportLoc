package hr.foi.air.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;

public class EventModel implements Parcelable {

    private int eventId;
    private int sportId;
    private int cityId;
    private int userId;
    private int capacity;
    private int current;
    private boolean open;
    private boolean expanded;
    private boolean isFull;
    private String username;
    private String name;
    private String start;
    private String end;
    private String address;
    private String description;
    private String sport;
    private String city;

    public EventModel() {
    }

    private EventModel(Parcel in) {
        eventId = in.readInt();
        capacity = in.readInt();
        current = in.readInt();
        sportId = in.readInt();
        cityId = in.readInt();
        userId = in.readInt();
        open = in.readByte() != 0;
        expanded = in.readByte() != 0;
        isFull = in.readByte() != 0;
        username = in.readString();
        name = in.readString();
        start = in.readString();
        end = in.readString();
        address = in.readString();
        description = in.readString();
        sport = in.readString();
        city = in.readString();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFull() {
        return capacity == current;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventId);
        dest.writeInt(capacity);
        dest.writeInt(current);
        dest.writeInt(sportId);
        dest.writeInt(cityId);
        dest.writeInt(userId);
        dest.writeByte((byte) (open ? 1 : 0));
        dest.writeByte((byte) (expanded ? 1 : 0));
        dest.writeByte((byte) (isFull ? 1 : 0));
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(sport);
        dest.writeString(city);
    }

    public boolean containsText(String query) {
        boolean result = false;

        for (Field f : getClass().getDeclaredFields()) {
            try {
                if (f.get(this) instanceof String && f.get(this) != null && ((String) f.get(this)).contains(query)) {
                    result = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
