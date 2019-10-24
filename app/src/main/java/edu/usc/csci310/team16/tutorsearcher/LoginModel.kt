package edu.usc.csci310.team16.tutorsearcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginModel:ViewModel(){
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login(){

    }

    fun register(){

    }

}