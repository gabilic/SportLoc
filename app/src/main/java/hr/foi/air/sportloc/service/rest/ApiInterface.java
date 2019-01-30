package hr.foi.air.sportloc.service.rest;

import java.util.List;

import hr.foi.air.sportloc.service.model.EventFilterModel;
import hr.foi.air.sportloc.service.model.EventModel;
import hr.foi.air.sportloc.service.model.LocationModel;
import hr.foi.air.sportloc.service.model.ParticipantModel;
import hr.foi.air.sportloc.service.model.PrimitiveWrapperModel;
import hr.foi.air.sportloc.service.model.SportModel;
import hr.foi.air.sportloc.service.model.UserModel;
import hr.foi.air.sportloc.service.model.CommentModel;
import hr.foi.air.sportloc.service.serviceUtil.WebServiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("login")
    Call<UserModel> getLoginUserInfo(@Query("username") String username, @Query("password") String password);

    @GET("resetPassword")
    Call<PrimitiveWrapperModel> getResetPasswordInfo(@Query("email") String email);

    @GET("getCities")
    Call<List<LocationModel>> getCities();

    @GET("getSports")
    Call<List<SportModel>> getSports();

    @POST("getEvents")
    Call<List<EventModel>> getEvents(@Body EventFilterModel filter);

    @POST("createEvent")
    Call<WebServiceResponse> createEvent(@Body EventModel event);

    @POST("register")
    Call<WebServiceResponse> registerUser(@Body UserModel user);

    @POST("updateEvent")
    Call<WebServiceResponse> updateEvent(@Body EventModel event);

    @POST("resolveParticipant")
    Call<WebServiceResponse> resolveParticipant(@Body ParticipantModel participant);

    @GET("getProfile")
    Call<UserModel> getProfile(@Query("username") String username);

    @POST("updateProfile")
    Call<WebServiceResponse> updateProfile(@Body UserModel user);

    @GET("getComments")
    Call<List<CommentModel>> getComments(@Query("id") Integer id);

    @POST("writeComment")
    Call<WebServiceResponse> writeComment(@Body CommentModel comment);

    @GET("getParticipants")
    Call<List<ParticipantModel>> getParticipants(@Query("id") Integer id);
}
