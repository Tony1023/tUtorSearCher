package edu.usc.csci310.team16.tutorsearcher;

import android.app.Application;
import android.util.MutableBoolean;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import edu.usc.csci310.team16.tutorsearcher.model.RoomDBRepository;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

import java.util.List;

public class NotificationModel extends AndroidViewModel {
    int indexNotification = 0;
    MutableLiveData<List<Notification>> mNotifications = new MutableLiveData<>();
    final NotificationListAdapter adapter = new NotificationListAdapter(this);

    public NotificationModel(@NonNull Application application){
        super(application);
        RoomDBRepository.getInstance(getApplication()).getAllPosts().observeForever(
                new Observer<List<Notification>>() {
                    @Override
                    public void onChanged(List<Notification> notifications) {
                        mNotifications.postValue(notifications);
                        adapter.setNotifications(notifications);
                    }
                });
    }


    public LiveData<List<Notification>> getNotifications() {
        return mNotifications;
    }

    public NotificationListAdapter getAdapter() {
        return adapter;
    }

    public void onRefresh(){
        //TODO check userID type
        //TODO actually implement networking
        WebServiceRepository.getInstance(getApplication()).getNotificationUpdates();
    }

}
