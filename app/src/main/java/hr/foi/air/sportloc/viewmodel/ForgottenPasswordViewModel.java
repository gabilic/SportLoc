package hr.foi.air.sportloc.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import hr.foi.air.sportloc.service.caller.WebServiceCaller;

public class ForgottenPasswordViewModel extends ViewModel {
    private LiveData<Boolean> resetPasswordInfoObservable;

    public void resetPassword(String email) {
        resetPasswordInfoObservable = WebServiceCaller.getInstance().getResetPasswordInfo(email);
    }

    public LiveData<Boolean> getResetPasswordInfoObservable() {
        return resetPasswordInfoObservable;
    }
}
