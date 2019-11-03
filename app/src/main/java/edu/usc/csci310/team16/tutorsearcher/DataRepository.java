package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import edu.usc.csci310.team16.tutorsearcher.network.BackendService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class DataRepository {
    private final BackendService backendService;
    // private MediatorLiveData<List<Notification>> mObservableNotifications;

    private DataRepository(){
        //mObservableNotifications = new MediatorLiveData<>();
        //mObservableNotifications.addSource();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        backendService = retrofit.create(BackendService.class);
    }

    public LiveData<List<Notification>> getNotifications(String userID){
        final MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();
        backendService.getNotifications(userID).enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                notifications.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {

            }
        });
        return notifications;
    }
}
