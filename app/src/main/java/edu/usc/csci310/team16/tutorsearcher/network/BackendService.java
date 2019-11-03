package edu.usc.csci310.team16.tutorsearcher.network;

import edu.usc.csci310.team16.tutorsearcher.LoginData;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import edu.usc.csci310.team16.tutorsearcher.UserProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

//TODO Finish mapping API
public interface BackendService {

    /**
     * Return user profile from this endpoint
     * @param userId
     * @return
     */
    @GET("/users/{user}")
    public Call<UserProfile> getProfile(@Path("user") String userId);

    @GET("/notifications/{user}")
    public Call<List<Notification>> getNotifications(@Path("user") String usedId);

    @POST("signup")
    public Call<Integer> register(@Body LoginData credentials);

    @POST("signin")
    public Call<UserProfile> login(@Body LoginData credentials);
}
