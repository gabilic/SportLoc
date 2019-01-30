package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.UserModel;

public class ProfileViewModel {
    private LiveData<UserModel> profileObservable;
    private LiveData<Boolean> profileUpdateObservable;

    public void getProfile(String username){
        profileObservable=WebServiceCaller.getInstance().getProfile(username);
    }
    public void updateProfile(UserModel user){
        profileUpdateObservable=WebServiceCaller.getInstance().updateProfile(user);
    }
    public LiveData<UserModel> getProfileObservable(){
        return  profileObservable;
    }
    public LiveData<Boolean> getProfileUpdateObservable(){
        return profileUpdateObservable;
    }

}
