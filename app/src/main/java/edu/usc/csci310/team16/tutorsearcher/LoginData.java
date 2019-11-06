package edu.usc.csci310.team16.tutorsearcher;


import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class LoginData {

    public final String email;
    public final String password;

    LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
