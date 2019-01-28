package hr.foi.air.search.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import hr.foi.air.search.DataArrivedHandler;

public class SearchFormModel implements Parcelable {

    private Integer activeUserId;

    private boolean showSportSelection;
    private boolean showCitySelection;
    private boolean showFullSelection;
    private boolean showParticipantSelection;
    private boolean showOpenSelection;

    public SearchFormModel(Integer activeUserId) {
        this.activeUserId = activeUserId;
    }

    public SearchFormModel(Parcel in) {
        activeUserId = in.readInt();
        showSportSelection = in.readByte() != 0;
        showCitySelection = in.readByte() != 0;
        showFullSelection = in.readByte() != 0;
        showParticipantSelection = in.readByte() != 0;
        showOpenSelection = in.readByte() != 0;
    }

    public Integer getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(Integer activeUserId) {
        this.activeUserId = activeUserId;
    }

    public boolean getShowSportSelection() {
        return showSportSelection;
    }

    public void setShowSportSelection(boolean showSportSelection) {
        this.showSportSelection = showSportSelection;
    }

    public boolean getShowCitySelection() {
        return showCitySelection;
    }

    public void setShowCitySelection(boolean showCitySelection) {
        this.showCitySelection = showCitySelection;
    }

    public boolean getShowFullSelection() {
        return showFullSelection;
    }

    public void setShowFullSelection(boolean showFullSelection) {
        this.showFullSelection = showFullSelection;
    }

    public boolean getShowParticipantSelection() {
        return showParticipantSelection;
    }

    public void setShowParticipantSelection(boolean showParticipantSelection) {
        this.showParticipantSelection = showParticipantSelection;
    }

    public boolean getShowOpenSelection() {
        return showOpenSelection;
    }

    public void setShowOpenSelection(boolean showOpenSelection) {
        this.showOpenSelection = showOpenSelection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchFormModel> CREATOR = new Creator<SearchFormModel>() {
        @Override
        public SearchFormModel createFromParcel(Parcel in) {
            return new SearchFormModel(in);
        }

        @Override
        public SearchFormModel[] newArray(int size) {
            return new SearchFormModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(activeUserId);
        dest.writeByte((byte) (showSportSelection ? 1 : 0));
        dest.writeByte((byte) (showCitySelection ? 1 : 0));
        dest.writeByte((byte) (showFullSelection ? 1 : 0));
        dest.writeByte((byte) (showParticipantSelection ? 1 : 0));
        dest.writeByte((byte) (showOpenSelection ? 1 : 0));
    }
}
