package edu.usc.csci310.team16.tutorsearcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotificationModel: ViewModel() {
    var mCurrentNotifications: Int = 0
    private val notifications: MutableLiveData<List<Notifications>> by lazy {
        MutableLiveData<List<Notifications>>().also{
            update()
        }
    }

    private fun update(){
        viewModelScope.launch {

        }
    }
}