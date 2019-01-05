package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Map;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.UserModel;

public class RegistrationViewModel extends ViewModel {
    private LiveData<Map<Boolean, String>> registrationObservable;

    public void register(UserModel user) {
        registrationObservable = WebServiceCaller.getInstance().registerUser(user);
    }

    public LiveData<Map<Boolean, String>> getRegistrationObservable() {
        return registrationObservable;
    }
}