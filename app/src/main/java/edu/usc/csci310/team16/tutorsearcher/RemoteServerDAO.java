package edu.usc.csci310.team16.tutorsearcher;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import edu.usc.csci310.team16.tutorsearcher.model.RemoteServerServices;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RemoteServerDAO {
    private static Retrofit retrofit = null;
    private static RemoteServerServices service = null;
    private static String url = "http://104.248.66.152:9000/";
    private static Integer id = -1;
    private static String token = "";
    private static boolean headerChanged = false;

    public static void setId(Integer id) {
        RemoteServerDAO.id = id;
        headerChanged = true;
    }

    public static void setToken(String token) {
        if (token == null) {
            token = "";
        }
        RemoteServerDAO.token = token;
        headerChanged = true;
    }

    public static void setUrl(String url) {
        RemoteServerDAO.url = url;
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

            // Might need to configure builder
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url) // Android emulator's hack to access localhost
                    .client(httpBuilder.build())
                    .build();

            service = retrofit.create(RemoteServerServices.class);
        }

        headerChanged=false;
        return service;
    }

}