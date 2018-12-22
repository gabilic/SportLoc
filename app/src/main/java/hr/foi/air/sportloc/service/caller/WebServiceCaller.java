package hr.foi.air.sportloc.service.caller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.service.model.LocationModel;
import hr.foi.air.sportloc.service.model.PrimitiveWrapperModel;
import hr.foi.air.sportloc.service.model.SportModel;
import hr.foi.air.sportloc.service.rest.ApiInterface;
import hr.foi.air.sportloc.service.serviceUtil.BooleanCallback;
import hr.foi.air.sportloc.service.serviceUtil.DataUtil;
import hr.foi.air.sportloc.service.serviceUtil.WebServiceResponse;
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

    //TODO make a more generic method for picklists
    public LiveData<SportModel[]> getSports() {
        final MutableLiveData<SportModel[]> data = new MutableLiveData<>();
        if (DataUtil.getInstance().getSports() != null && DataUtil.getInstance().getSports().length > 0) {
            data.setValue(DataUtil.getInstance().getSports());
        } else {
            api.getSports().enqueue(new Callback<List<SportModel>>() {
                @Override
                public void onResponse(Call<List<SportModel>> call, Response<List<SportModel>> response) {
                    data.setValue(response.body().toArray(new SportModel[response.body().size()]));
                    DataUtil.getInstance().setSports(data.getValue());
                }

                @Override
                public void onFailure(Call<List<SportModel>> call, Throwable t) {
                    data.setValue(null);
                }
            });
        }
        return data;
    }

    public LiveData<LocationModel[]> getLocations() {
        final MutableLiveData<LocationModel[]> data = new MutableLiveData<>();
        if (DataUtil.getInstance().getLocations() != null && DataUtil.getInstance().getLocations().length > 0) {
            data.setValue(DataUtil.getInstance().getLocations());
        } else {
            api.getCities().enqueue(new Callback<List<LocationModel>>() {
                @Override
                public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                    data.setValue(response.body().toArray(new LocationModel[response.body().size()]));
                    DataUtil.getInstance().setLocations(data.getValue());
                }

                @Override
                public void onFailure(Call<List<LocationModel>> call, Throwable t) {
                    data.setValue(null);
                }
            });
        }

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

    public LiveData<Boolean> createEvent(EventModel event) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        api.createEvent(event).enqueue(new BooleanCallback(data));
        return data;
    }

}
