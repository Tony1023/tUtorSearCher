package edu.usc.csci310.team16.tutorsearcher;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.*;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

import java.util.List;

public class NotificationModel extends AndroidViewModel {
    int indexNotification = 0;
    private NotificationListAdapter adapter;
    MutableLiveData<List<Notification>> mNotifications = new MutableLiveData<>();
    MutableLiveData<Boolean> pickerView = new MutableLiveData<>();

    public NotificationModel(@NonNull Application application){
        super(application);
        //TODO check room deletion
    }


    public LiveData<List<Notification>> getNotifications() {
        return mNotifications;
    }

    public void onRefresh(){
        //TODO check userID type
        WebServiceRepository.getInstance(getApplication()).getNotificationUpdates(mNotifications);
    }

    public NotificationListAdapter getAdapter(){
        return adapter;
    }

    public void setAdapter(NotificationListAdapter adapter) {
        this.adapter = adapter;
    }

    public MutableLiveData<Boolean> getPickerView(){
        return pickerView;
    }

    public void setPosition(int position) {
        indexNotification = position;
    }

    public int getPosition(){
        return indexNotification;
    }

    public Notification getNotification(){
        return mNotifications.getValue().get(indexNotification);
    }

    public void removePosition(int position) {
        List<Notification> notifications = mNotifications.getValue();
        notifications.remove(position);
        mNotifications.postValue(notifications);
    }
}
