package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RatingModel extends ViewModel {
    public final MutableLiveData<UserProfile> previousTutors = new MutableLiveData<>();
}
