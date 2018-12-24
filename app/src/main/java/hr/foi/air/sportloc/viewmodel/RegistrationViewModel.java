package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.UserModel;

public class RegistrationViewModel extends ViewModel {
    private LiveData<Boolean> registrationObservable;

    public void register(UserModel user) {
        registrationObservable = WebServiceCaller.getInstance().registerUser(user);
    }

    public LiveData<Boolean> getRegistrationObservable() {
        return registrationObservable;
    }
}