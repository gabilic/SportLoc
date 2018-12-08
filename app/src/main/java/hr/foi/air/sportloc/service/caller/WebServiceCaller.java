package hr.foi.air.sportloc.service.caller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import hr.foi.air.sportloc.service.model.PrimitiveWrapperModel;
import hr.foi.air.sportloc.service.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceCaller {
    private ApiInterface api;
    private static WebServiceCaller instance;
    private static final String BASE_URL = "https://sportloc-backend-test.herokuapp.com/";

    private WebServiceCaller() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiInterface.class);
    }

    public static synchronized WebServiceCaller getInstance() {
        if (instance == null) {
            instance = new WebServiceCaller();
        }
        return instance;
    }

    public LiveData<Integer> getLoginUserInfo(String username, String password) {
        final MutableLiveData<Integer> data = new MutableLiveData<>();
        api.getLoginUserInfo(username, password).enqueue(new Callback<PrimitiveWrapperModel>() {
            @Override
            public void onResponse(Call<PrimitiveWrapperModel> call, Response<PrimitiveWrapperModel> response) {
                data.setValue(response.body().getUserId());
            }

            @Override
            public void onFailure(Call<PrimitiveWrapperModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<Boolean> getResetPasswordInfo(String email) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        api.getResetPasswordInfo(email).enqueue(new Callback<PrimitiveWrapperModel>() {
            @Override
            public void onResponse(Call<PrimitiveWrapperModel> call, Response<PrimitiveWrapperModel> response) {
                data.setValue(response.body().isSuccess());
            }

            @Override
            public void onFailure(Call<PrimitiveWrapperModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
