package hr.foi.air.sportloc.service.serviceUtil;

import android.arch.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Response;

public class MessageCallback implements retrofit2.Callback<WebServiceResponse> {
    MutableLiveData<Map<Boolean, String>> data;

    public MessageCallback(MutableLiveData<Map<Boolean, String>> data) {
        this.data=data;
    }

    @Override
    public void onResponse(Call<WebServiceResponse> call, Response<WebServiceResponse> response) {
        Map<Boolean, String> map = new HashMap<>();
        map.put(response.body().isSuccessful(), response.body().getMessage());
        data.setValue(map);
    }

    @Override
    public void onFailure(Call<WebServiceResponse> call, Throwable t) {
        data.setValue(null);
    }
}
