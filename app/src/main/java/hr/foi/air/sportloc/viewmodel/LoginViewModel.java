package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;
import hr.foi.air.sportloc.service.model.UserModel;

public class LoginViewModel extends ViewModel {
    private LiveData<UserModel> loginInfoObservable;

    public void login(String username, String password) {
        loginInfoObservable = WebServiceCaller.getInstance().getLoginUserInfo(username, password);
    }

    public LiveData<UserModel> getLoginInfoObservable() {
        return loginInfoObservable;
    }
}
