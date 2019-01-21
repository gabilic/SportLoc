package hr.foi.air.sportloc.service.serviceUtil;

import android.arch.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Response;

public class BooleanCallback implements retrofit2.Callback<WebServiceResponse> {
    private MutableLiveData<Boolean> data;

    public BooleanCallback(MutableLiveData<Boolean> data) {
        this.data=data;
    }

    @Override
    public void onResponse(Call<WebServiceResponse> call, Response<WebServiceResponse> response) {
        data.setValue(response.body().isSuccessful());
    }

    @Override
    public void onFailure(Call<WebServiceResponse> call, Throwable t) {
        data.setValue(Boolean.FALSE);
    }
}
