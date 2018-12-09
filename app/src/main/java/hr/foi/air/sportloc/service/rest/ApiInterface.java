package hr.foi.air.sportloc.service.rest;

import java.util.List;

import hr.foi.air.sportloc.service.model.LocationModel;
import hr.foi.air.sportloc.service.model.PrimitiveWrapperModel;
import hr.foi.air.sportloc.service.model.SportModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("login")
    Call<PrimitiveWrapperModel> getLoginUserInfo(@Query("username") String username, @Query("password") String password);
    @GET("getCities")
    Call<List<LocationModel>> getCities();
    @GET("getSports")
    Call<List<SportModel>> getSports();
}
