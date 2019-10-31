package edu.usc.csci310.team16.tutorsearcher.network;

import edu.usc.csci310.team16.tutorsearcher.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

//TODO Finish mapping API
public interface BackendService {

    /**
     * Return user profile from this endpoint
     * @param userId
     * @return
     */
    @GET("/users/{user}")
    public Call<UserProfile> getProfile(@Path("user") String userId);
}
