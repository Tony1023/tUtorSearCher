package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.usc.csci310.team16.tutorsearcher.Notification;

import java.util.List;

public class NotificationModel extends ViewModel {
    int indexNotification = 0;
    MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();
    final NotificationListAdapter adapter = new NotificationListAdapter(this);

    public MutableLiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public NotificationListAdapter getAdapter() {
        return adapter;
    }
}