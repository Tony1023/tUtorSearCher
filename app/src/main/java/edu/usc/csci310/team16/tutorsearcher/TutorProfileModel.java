package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TutorProfileModel extends ViewModel {

    //changing this should change the actual view for the user
    public final MutableLiveData<UserProfile> profile = new MutableLiveData<>();

    public MutableLiveData<UserProfile> updateProfile() {
        return profile;
    }
}
