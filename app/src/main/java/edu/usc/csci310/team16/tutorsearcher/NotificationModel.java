package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import edu.usc.csci310.team16.tutorsearcher.network.NotificationDAO;

import java.util.List;

public class NotificationModel extends ViewModel {
    int indexNotification = 0;
    MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();
    final NotificationListAdapter adapter = new NotificationListAdapter(this);

    public NotificationModel(){
        notifications.observeForever(new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                adapter.setNotifications(notifications);
            }
        });
    }

    public void retrieveNotifications(){
        notifications.postValue(NotificationDAO.retrieveNotifications());
    }

    public MutableLiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public NotificationListAdapter getAdapter() {
        return adapter;
    }
}
