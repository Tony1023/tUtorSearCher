package edu.usc.csci310.team16.tutorsearcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class LoginModel:ViewModel(){
    val cred = MutableLiveData<Login>()

    val remember = MutableLiveData<Boolean>()

    

    fun login(){
        viewModelScope.run {  }
        return
    }

    fun register(){

    }

}