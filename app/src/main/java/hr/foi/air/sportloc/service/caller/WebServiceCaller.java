package hr.foi.air.sportloc.service.caller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class WebServiceCaller {
    private static WebServiceCaller instance;

    private WebServiceCaller() {
    }

    public static synchronized WebServiceCaller getInstance() {
        if (instance == null) {
            instance = new WebServiceCaller();
        }
        return instance;
    }

    public LiveData<Integer> getLoginUserInfo(String username, String password) {
        final MutableLiveData<Integer> data = new MutableLiveData<>();
        if (username.equals("admin_test") && password.equals("password")) {
            data.setValue(1);
        }
        else {
            data.setValue(0);
        }
        return data;
    }
}
