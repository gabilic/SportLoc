package hr.foi.air.sportloc.service.caller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Map;

import hr.foi.air.sportloc.service.model.CommentModel;
import hr.foi.air.sportloc.service.model.EventFilterModel;
import hr.foi.air.core.EventModel;
import hr.foi.air.core.LocationModel;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.service.model.PrimitiveWrapperModel;
import hr.foi.air.core.SportModel;
import hr.foi.air.sportloc.service.model.UserModel;
import hr.foi.air.sportloc.service.rest.ApiInterface;
import hr.foi.air.sportloc.service.serviceUtil.BooleanCallback;
import hr.foi.air.sportloc.service.serviceUtil.DataUtil;
import hr.foi.air.sportloc.service.serviceUtil.MessageCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static hr.foi.air.sportloc.view.util.Constants.BASE_URL;

public class WebServiceCaller {
    private ApiInterface api;
    private static WebServiceCaller instance;

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

    public LiveData<UserModel> getLoginUserInfo(String username, String password) {
        final MutableLiveData<UserModel> data = new MutableLiveData<>();
        api.getLoginUserInfo(username, password).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
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

    public LiveData<Map<Boolean, String>> registerUser(UserModel user) {
        final MutableLiveData<Map<Boolean, String>> data = new MutableLiveData<>();
        api.registerUser(user).enqueue(new MessageCallback(data));
        return data;
    }


    public LiveData<Boolean> updateEvent(EventModel event) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        api.updateEvent(event).enqueue(new BooleanCallback(data));
        return data;
    }

    public LiveData<Boolean> resolveParticipant(ParticipantModel participant) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        api.resolveParticipant(participant).enqueue(new BooleanCallback(data));
        return data;
    }
    public LiveData<Boolean> updateProfile(UserModel user) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        api.updateProfile(user).enqueue(new BooleanCallback(data));
        return data;
    }

    public LiveData<UserModel> getProfile(String username) {
        final MutableLiveData<UserModel> data = new MutableLiveData<>();
        api.getProfile(username).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<CommentModel>> getComments(Integer id){
        final MutableLiveData<List<CommentModel>> data=new MutableLiveData<>();
        api.getComments(id).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<Boolean> writeComment(CommentModel comment) {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        api.writeComment(comment).enqueue(new BooleanCallback(data));
        return data;
    }

    public LiveData<List<ParticipantModel>> getParticipants(Integer id){
        final MutableLiveData<List<ParticipantModel>> data=new MutableLiveData<>();
        api.getParticipants(id).enqueue(new Callback<List<ParticipantModel>>() {
            @Override
            public void onResponse(Call<List<ParticipantModel>> call, Response<List<ParticipantModel>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ParticipantModel>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }


}
