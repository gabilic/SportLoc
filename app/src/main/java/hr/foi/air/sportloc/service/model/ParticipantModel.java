package hr.foi.air.sportloc.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import hr.foi.air.sportloc.service.serviceUtil.ActionEnum;

public class ParticipantModel implements Parcelable {

    private int eventId;
    private int userId;
    private int statusId;
    private ActionEnum action;
    private String username;
    private String status;

    public ParticipantModel() {}

    public ParticipantModel(Parcel in){
        eventId = in.readInt();
        userId = in.readInt();
        statusId = in.readInt();
        action = ActionEnum.valueOf(in.readString());
        username = in.readString();
        status = in.readString();
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public static final Creator<ParticipantModel> CREATOR = new Creator<ParticipantModel>() {
        @Override
        public ParticipantModel createFromParcel(Parcel in) {
            return new ParticipantModel(in);
        }

        @Override
        public ParticipantModel[] newArray(int size) {
            return new ParticipantModel[size];
        }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventId);
        dest.writeInt(userId);
        dest.writeInt(statusId);
        dest.writeString(action != null ? action.name() : "");
        dest.writeString(username);
        dest.writeString(status);
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
