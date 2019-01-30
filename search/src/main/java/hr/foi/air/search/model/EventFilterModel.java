package hr.foi.air.search.model;

public class EventFilterModel {
    private int userId;
    private int sportId;
    private int cityId;
    private boolean owner;
    private boolean full;
    private Boolean participant;
    private Boolean open;

    public EventFilterModel() {
    }

    public EventFilterModel(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public Boolean getParticipant() {
        return participant;
    }

    public void setParticipant(Boolean participant) {
        this.participant = participant;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
