package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.usc.csci310.team16.tutorsearcher.Notification;

import java.util.List;

public class NotificationModel extends ViewModel {
    int indexNotification = 0;
    MutableLiveData<List<Notification>> notifications = new MutableLiveData<List<Notification >>();


}
