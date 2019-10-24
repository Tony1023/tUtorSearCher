package edu.usc.csci310.team16.tutorsearcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RatingModel:ViewModel(){
    val previousTutors = MutableLiveData<UserProfile>()
    fun setup(){
        TODO("Populate previous tutors")
    }
    fun updateTutors():MutableLiveData<UserProfile>{
        TODO("Update previous tutors")
    }
}