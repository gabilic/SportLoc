package hr.foi.air.sportloc.service.rest;

import hr.foi.air.sportloc.service.model.PrimitiveWrapperModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("login")
    Call<PrimitiveWrapperModel> getLoginUserInfo(@Query("username") String username, @Query("password") String password);
}
