package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;

import java.util.List;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.ParticipantModel;

public class EventParticipantViewModel {
    private LiveData<List<ParticipantModel>> participantObservable;

    public void getParticipants(Integer id) {
        participantObservable = WebServiceCaller.getInstance().getParticipants(id);
    }

    public LiveData<List<ParticipantModel>> getParticipantsObservable() {
        return participantObservable;
    }
}
