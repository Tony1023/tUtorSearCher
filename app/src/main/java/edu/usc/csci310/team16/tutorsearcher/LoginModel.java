package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginModel extends ViewModel {

    private final LoginData credentials = new LoginData();
    public final MutableLiveData<Boolean> remember = new MutableLiveData<>();

    public LoginData getCredentials() {
        return credentials;
    }



    void register() {

    }

}
