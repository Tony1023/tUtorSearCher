package edu.usc.csci310.team16.tutorsearcher;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginModel extends ViewModel {

    public final MutableLiveData<LoginData> credentials = new MutableLiveData<>();

    public final MutableLiveData<Boolean> remember = new MutableLiveData<>();

    void register() {

    }

}
