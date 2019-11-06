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

import edu.usc.csci310.team16.tutorsearcher.LoginData;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import edu.usc.csci310.team16.tutorsearcher.UserProfile;
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

public class RemoteServerDAO {
    private static Retrofit retrofit = null;
    private static RemoteServerServices dao = null;
    final private static String url = "http://104.248.66.152:8080/server_main_war_exploded/";
    private static Integer id = -1;
    private static String token = "";
    private static boolean headerChanged = false;

    public static void setId(Integer id) {
        RemoteServerDAO.id = id;
        headerChanged = true;
    }

    public static void setToken(String token) {
        RemoteServerDAO.token = token;
        headerChanged = true;
    }

    public static RemoteServerServices getDao() {
        if (retrofit == null || headerChanged) {
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

            dao = retrofit.create(RemoteServerServices.class);
        }

        headerChanged=false;
        return dao;
    }

}
