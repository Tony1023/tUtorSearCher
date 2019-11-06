package edu.usc.csci310.team16.tutorsearcher.model;

import edu.usc.csci310.team16.tutorsearcher.LoginData;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import edu.usc.csci310.team16.tutorsearcher.UserProfile;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface RemoteServerServices {
    @POST("signup")
    Call<Map<String, Object>> register(@Body LoginData credentials);

    @POST("signin")
    Call<UserProfile> login(@Body LoginData credentials);

    @GET("getNotifications")
    Call<List<Notification>> getNotifications();

    @FormUrlEncoded
    @POST("validateToken")
    Call<UserProfile> validate(@Field("id") Integer id, @Field("token") String token);
    // TODO: change to UserProfile
    // TODO: make sure @FormUrlEncoded works
    // TODO: add header to every method

    @GET("user/getTutors")
    Call<List<UserProfile>> getTutors();

    @Multipart
    @POST("user/updateProfileImage")
    Call<String> uploadImage(@Part("userId") Integer id, @Part MultipartBody.Part file);

    @POST("acceptRequest")
    Call<String> acceptRequest(@Body Integer id);

    @POST("acceptRequest")
    Call<String> rejectRequest(@Body Integer id);
}
