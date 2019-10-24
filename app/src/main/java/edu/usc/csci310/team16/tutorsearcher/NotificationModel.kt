package edu.usc.csci310.team16.tutorsearcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationModel: ViewModel() {
    var mCurrentNotifications: Int = 0
    val notification = MutableLiveData<Notifications>()

    fun update(){

    }
}