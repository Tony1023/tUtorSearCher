package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import edu.usc.csci310.team16.tutorsearcher.network.BackendService;

import java.util.List;

public class DataRepository {
    private final BackendService backendService;
    private MediatorLiveData<List<Notification>> mObservableNotifications;

    private DataRepository(){
        mObservableNotifications = new MediatorLiveData<>();
        //mObservableNotifications.addSource();
        backendService = null; //TODO Finish Backendservice repository
    }

    public LiveData<List<Notification>> getNotifications(){
        return mObservableNotifications;
    }
}
