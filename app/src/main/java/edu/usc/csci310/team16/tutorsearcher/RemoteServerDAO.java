package edu.usc.csci310.team16.tutorsearcher;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

interface RemoteServerServices {
    @POST("signup")
    Call<Map<String, Object>> register(@Body LoginData credentials);

    @POST("signin")
    Call<UserProfile> login(@Body LoginData credentials);

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

    @GET("searchTutor")
    Call<List<UserProfile>> searchTutor(@QueryMap Map<String, Object> q);
}

public class RemoteServerDAO {

    private static Retrofit retrofit = null;
    private static RemoteServerServices dao = null;
    final private static String url = "http:10.0.2.2:8080/";
    private static Integer id = -1;
    private static String token = "";

    public static void setId(Integer id) {
        RemoteServerDAO.id = id;
    }

    public static void setToken(String token) {
        RemoteServerDAO.token = token;
    }

    public static RemoteServerServices getDao() {
        if (retrofit == null) {
            OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
            httpBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request req = chain.request().newBuilder()
                            .addHeader("access-token", token)
                            .addHeader("user-id", id.toString())
                            .build();
                    return chain.proceed(req);
                }
            });

            GsonBuilder gsonbuilder = new GsonBuilder();
            // Might need to configure builder
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsonbuilder.create()))
                    .baseUrl(url) // Android emulator's hack to access localhost
                    .client(httpBuilder.build())
                    .build();
        }
        if (dao == null) {
            dao = retrofit.create(RemoteServerServices.class);
        }
        return dao;
    }

}
