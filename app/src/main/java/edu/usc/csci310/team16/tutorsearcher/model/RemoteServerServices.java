package edu.usc.csci310.team16.tutorsearcher.model;

import edu.usc.csci310.team16.tutorsearcher.LoginData;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import edu.usc.csci310.team16.tutorsearcher.UserProfile;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface RemoteServerServices {
    @GET("user/getNotifications")
    Call<List<Notification>> getNotifications();

    @GET("user/getNotifications")
    Call<List<Notification>> checkNotifications();

    @GET("user/getNotificationCount")
    Call<Integer> getNotificationUpdates();

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

        @GET("user/getProfileImage/{userId}")
        Call<ResponseBody> getImage(@Path("userId") Integer id);

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

        @FormUrlEncoded
        @POST("user/acceptRequest")
        Call<String> acceptRequest(@Field("id") Integer requestId);

        @FormUrlEncoded
        @POST("user/rejectRequest")
        Call<String> rejectRequest(@Field("id") Integer requestId);

        @FormUrlEncoded
        @POST("user/getRating")
        Call<Double> getRating(@Field("tutor_id") Integer tutorId, @Field("tutee_id") Integer tuteeId);

        @FormUrlEncoded
        @POST("user/rateTutor")
        Call<String> rateTutor(@Field("tutor_id") Integer tutorId, @Field("tutee_id") Integer tuteeId, @Field("rating") Double rating);
}
