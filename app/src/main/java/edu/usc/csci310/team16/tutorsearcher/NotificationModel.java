package edu.usc.csci310.team16.tutorsearcher;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

import java.util.List;

public class NotificationModel extends AndroidViewModel {
    int indexNotification = 0;
    MutableLiveData<List<Notification>> mNotifications = new MutableLiveData<>();
    final NotificationListAdapter adapter = new NotificationListAdapter(this);

    public NotificationModel(@NonNull Application application){
        super(application);
        //TODO check room deletion
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
        WebServiceRepository.getInstance(getApplication()).getNotificationUpdates(mNotifications);
    }

}
