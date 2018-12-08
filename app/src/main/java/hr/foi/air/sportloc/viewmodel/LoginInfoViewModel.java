package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;

public class LoginInfoViewModel extends ViewModel {
    private LiveData<Integer> loginInfoObservable;

    public void login(String username, String password) {
        loginInfoObservable = WebServiceCaller.getInstance().getLoginUserInfo(username, password);
    }

    public LiveData<Integer> getLoginInfoObservable() {
        return loginInfoObservable;
    }
}
