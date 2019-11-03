package edu.usc.csci310.team16.tutorsearcher.model;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class WebServiceRepository {

    private static volatile WebServiceRepository INSTANCE;
    private final JsonParser parser;
    Application application;
    final Retrofit retrofit;
    BackendService service;
    final MutableLiveData<List<Notification>> data = new MutableLiveData<>();

    private WebServiceRepository(Application application){
        this.application = application;
        GsonBuilder builder = new GsonBuilder();

        retrofit = new Retrofit.Builder()
                .baseUrl("http:10.0.2.2:8080/server/")
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();

        //Defining retrofit api service
        service = retrofit.create(BackendService.class);
        parser = new JsonParser();

    }

    public static WebServiceRepository getInstance(Application app) {
        if (INSTANCE == null){
            synchronized (WebServiceRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new WebServiceRepository(app);
                }
            }
        }
        return INSTANCE;
    }

    public MutableLiveData<List<Notification>> getNotifications(){
        return data;
    }

    public LiveData<List<Notification>> getNotificationUpdates(int userId) {
        String response = "";

        try {
            //  response = service.makeRequest().execute().body();
            //TODO check userID type
            service.getNotifications(Integer.toString(userId)).enqueue(new Callback<List<Notification>>() {

                @Override
                public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                    List<Notification> webserviceResponseList;
                    webserviceResponseList = response.body();

                    RoomDBRepository roomDBRepository = RoomDBRepository.getInstance(application);

                    roomDBRepository.insertPosts(webserviceResponseList);
                }

                @Override
                public void onFailure(Call<List<Notification>> call, Throwable t) {
                    Log.d("Repository","Failed:::");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //  return retrofit.create(ResultModel.class);
        return  data;
    }

    public LiveData<List<Notification>> getNotificationUpdates() {
        String response = "";


        //  response = service.makeRequest().execute().body();
        //TODO check userID type

        List<Notification> notes = new ArrayList<>();
        Notification n1 = new Notification("fd_FD_f","MSG","Tutor with Mike");
        notes.add(n1);

        n1 = new Notification("fd_FD_f","MSG","Tutor with Bob");
        notes.add(n1);

        n1 = new Notification("ayyy","TUTOR_REQUEST","Accept Bob");
        notes.add(n1);

        n1 = new Notification("ay2","TUTOR_REQUEST","Accept Bob");
        notes.add(n1);

        RoomDBRepository roomDBRepository = RoomDBRepository.getInstance(application);
        roomDBRepository.insertPosts(notes);

        //  return retrofit.create(ResultModel.class);
        return  data;
    }
}