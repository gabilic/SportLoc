package hr.foi.air.search.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import hr.foi.air.core.EventModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceCaller {
    private ApiInterface api;
    private static WebServiceCaller instance;

    private WebServiceCaller() {
    }

    public static synchronized WebServiceCaller getInstance() {
        if (instance == null) {
            instance = new WebServiceCaller();
        }
        return instance;
    }

    public void setupRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiInterface.class);
    }

    public LiveData<EventModel[]> getEvents(EventFilterModel filter) {
        final MutableLiveData<EventModel[]> data = new MutableLiveData<>();
        api.getEvents(filter).enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                data.setValue(response.body().toArray(new EventModel[0]));
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
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
                    data.setValue(response.body().toArray(new SportModel[0]));
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
                    data.setValue(response.body().toArray(new LocationModel[0]));
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

}
