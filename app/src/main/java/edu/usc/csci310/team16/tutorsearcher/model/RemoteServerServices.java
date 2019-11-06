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

    @FormUrlEncoded
    @POST("validateToken")
    Call<UserProfile> validate(@Field("id") Integer id, @Field("token") String token);
    // TODO: change to UserProfile
    // TODO: make sure @FormUrlEncoded works

    @GET("user/getTutors")
    Call<List<UserProfile>> getTutors();

    @Multipart
    @POST("user/updateProfileImage")
    Call<String> uploadImage(@Part("userId") Integer id, @Part MultipartBody.Part file);

    @POST("user/updateProfile")
    Call<String> updateProfile(@Body UserProfile profile);

    @POST("user/searchTutor")
    Call<List<UserProfile>> searchTutor(@Body Map<String, Object> q);

    /**
     * body: {
     *     "tutee_id": 1,
     *     "tutor_id": 2,
     *     "course": "CSCI103",
     *     "availability": [0, 1, 2]
     * }
     */
    @POST("user/sendRequest")
    Call<String> sendRequest(@Body Map<String, Object> body);

    @POST("user/acceptRequest")
    Call<String> acceptRequest(@Body Integer requestId);

    @POST("user/rejectRequest")
    Call<String> rejectRequest(@Body Integer requestId);

    @FormUrlEncoded
    @POST("user/getRating")
    Call<Double> getRating(@Field("tutor_id") Integer tutorId, @Field("tuteeId") Integer tuteeId);

    @FormUrlEncoded
    @POST("user/rateTutor")
    Call<String> rateTutor(@Field("tutor_id") Integer tutorId, @Field("tuteeId") Integer tuteeId, @Field("rating") Double rating);

    @GET("user/getNotifications")
    Call<List<Notification>> getNotifications();

    @GET("user/getNotifications")
    Call<List<Notification>> checkNotifications();


}