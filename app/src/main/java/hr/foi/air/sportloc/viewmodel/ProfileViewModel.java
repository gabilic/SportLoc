package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.UserModel;

public class ProfileViewModel {
    private LiveData<UserModel> profileObservable;

    public void getProfile(String username){
        profileObservable=WebServiceCaller.getInstance().getProfile(username);
    }
    public LiveData<UserModel> getProfileObservable(){
        return  profileObservable;
    }

}
