package hr.foi.air.sportloc.service.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EventModel implements Parcelable {
    private int currentCapacity;
    private int maxCapacity;
    private boolean openEvent;
    private String creatorUserName;
    private String title;
    private String startTime;
    private String startDate;
    private String endTime;
    private String endDate;
    private String description;
    private String address;
    private String sport;
    private String location;

    public EventModel() {
    }

    private EventModel(Parcel in) {
        currentCapacity = in.readInt();
        maxCapacity = in.readInt();
        openEvent = in.readByte() != 0;
        creatorUserName = in.readString();
        title = in.readString();
        startTime = in.readString();
        startDate = in.readString();
        endTime = in.readString();
        endDate = in.readString();
        description = in.readString();
        address = in.readString();
        sport = in.readString();
        location = in.readString();
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isOpenEvent() {
        return openEvent;
    }

    public void setOpenEvent(boolean openEvent) {
        this.openEvent = openEvent;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        dest.writeInt(currentCapacity);
        dest.writeInt(maxCapacity);
        dest.writeByte((byte) (openEvent ? 1 : 0));
        dest.writeString(creatorUserName);
        dest.writeString(title);
        dest.writeString(startTime);
        dest.writeString(startDate);
        dest.writeString(endTime);
        dest.writeString(endDate);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeString(sport);
        dest.writeString(location);
    }
}
