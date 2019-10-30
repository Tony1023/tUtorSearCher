package edu.usc.csci310.team16.tutorsearcher;


import androidx.lifecycle.MutableLiveData;

public class LoginData {

    private MutableLiveData<String> email = new MutableLiveData<>("");
    private MutableLiveData<String> password = new MutableLiveData<>("");

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

}
