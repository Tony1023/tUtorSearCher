package edu.usc.csci310.team16.tutorsearcher.model;

import android.content.Context;
import android.util.Log;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import edu.usc.csci310.team16.tutorsearcher.RemoteServerDAO;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationMsgBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;


public class WebServiceRepository {

    static final String TAG = "WEBSERVICE_REPO";

    private static volatile WebServiceRepository INSTANCE;

    private Context application;
    private final MutableLiveData<List<Notification>> data = new MutableLiveData<>();
    private final RemoteServerServices service;

    private WebServiceRepository(Context application){
        this.application = application;
        service = RemoteServerDAO.getDao();
    }

    public static WebServiceRepository getInstance(Context app) {
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

    public void getNotificationUpdates(final MutableLiveData<List<Notification>> data) {
        String response = "";

        try {
            //TODO check userID type
            service.getNotifications().enqueue(new Callback<List<Notification>>() {
                @Override
                public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                    List<Notification> webserviceResponseList = new ArrayList<>();
                    webserviceResponseList = response.body();

                    data.postValue(webserviceResponseList);

                    Log.i("WEB_REPO","Notifications recieved success");
                }

                @Override
                public void onFailure(Call<List<Notification>> call, Throwable t) {

                    Log.d("WEB_REPO","Failed:::");
                }
            });
        }catch (Exception e){
            Log.e("WEB_REPO",e.getMessage());
        }

        //  return retrofit.create(ResultModel.class);
    }

    public void acceptRequest(final Notification notification, final MutableLiveData<String> callFinished) {
        //  response = service.makeRequest().execute().body();
        //TODO check userID type
        service.acceptRequest(notification.getRequestId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.i(TAG, response.body());

                if ("Success".equals(response.body())) {
                    callFinished.postValue("ACCEPTED");
                } else if ("Tutee taken".equals(response.body())) {
                    callFinished.postValue("REJECTED");
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "ACCEPT_FAILURE");

                callFinished.postValue("FAILED");
            }
        });
    }

    public void rejectRequest(final Notification notification, final MutableLiveData<String> callFinished) {
        service.rejectRequest(notification.getRequestId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                callFinished.postValue("SUCCESS");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callFinished.postValue("FAILED");
                Log.e("WEBSERVICE_REPO", "REJECT_FAILURE");
            }
        });
    }

    public Call<Integer> pollNotifications(){
        return service.getNotificationUpdates();
    }


//    public void getNotificationUpdates() {
//        String response = "";
//
//        //  response = service.makeRequest().execute().body();
//        //TODO check userID type
//
//        List<Notification> notes = new ArrayList<>();
//        Notification n1 = new Notification("fd_FD_f","MSG","Tutor with Mike");
//        notes.add(n1);
//
//        n1 = new Notification("fd_f","MSG","Tutor with Bob");
//        notes.add(n1);
//
//        n1 = new Notification("ayyy","TUTOR_REQUEST","Accept Bob");
//        notes.add(n1);
//
//        n1 = new Notification("ay2","TUTOR_REQUEST","Accept Bob");
//        notes.add(n1);
//
//        n1 = new Notification("1","MSG","Tutor with Bob");
//        notes.add(n1);
//
//        n1 = new Notification("2","MSG","Tutor with Bob");
//        notes.add(n1);
//
//        n1 = new Notification("3","MSG","Tutor with Bob");
//        notes.add(n1);
//        n1 = new Notification("3","MSG","Fun with Bob");
//        notes.add(n1);
//
//        RoomDBRepository roomDBRepository = RoomDBRepository.getInstance(application);
//        roomDBRepository.insertPosts(notes);
//
//        //  return retrofit.create(ResultModel.class);
//    }
}