package hr.foi.air.search.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    //TODO replace with dynamic paths
    @GET("getCities")
    Call<List<LocationModel>> getCities();

    @GET("getSports")
    Call<List<SportModel>> getSports();

    @POST("getEvents")
    Call<List<EventModel>> getEvents(@Body EventFilterModel filter);

}
