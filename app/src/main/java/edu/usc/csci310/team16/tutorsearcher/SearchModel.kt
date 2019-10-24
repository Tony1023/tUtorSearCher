package edu.usc.csci310.team16.tutorsearcher

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchModel:ViewModel() {
    val classes = MutableLiveData<List<String>>()
    val freeTime = MutableLiveData<List<IntArray>>()
    val results = MutableLiveData<List<IntArray>>()

    fun create(){
        TODO("Create the Search activity")
    }
}