package edu.usc.csci310.team16.tutorsearcher;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface RemoteServerServices {
    @POST("signup")
    Call<Integer> register(@Body LoginData credentials);

    @POST("signin")
    Call<UserProfile> login(@Body LoginData credentials);

    @FormUrlEncoded
    @POST("validateToken")
    Call<Integer> validate(@Field("email") String email, @Field("token") String token);
    // TODO: change to UserProfile
    // TODO: make sure @FormUrlEncoded works
    // TODO: add header to every method
}

public class RemoteServerDAO {

    private static Retrofit retrofit = null;
    private static RemoteServerServices dao = null;

    public static RemoteServerServices getDao() {
        if (retrofit == null) {

            GsonBuilder builder = new GsonBuilder();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .baseUrl("http:10.0.2.2:8080/server/") // Android emulator's hack to access localhost
                    .build();
        }
        if (dao == null) {
            dao = retrofit.create(RemoteServerServices.class);
        }
        return dao;
    }

}
