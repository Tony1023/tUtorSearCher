package edu.usc.csci310.team16.tutorsearcher;


import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class LoginData implements JsonSerializer<LoginData> {

    private MutableLiveData<String> email = new MutableLiveData<>("");
    private MutableLiveData<String> password = new MutableLiveData<>("");

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }


    @Override
    public JsonElement serialize(LoginData data, Type typeOfSrc, JsonSerializationContext context) {
        return new Gson().toJsonTree(new LoginDataWrapper(data));
    }

    private class LoginDataWrapper {
        private final String email;
        private final String password;

        LoginDataWrapper(LoginData data) {
            this.email = data.getEmail().getValue();
            this.password = data.getPassword().getValue();
        }
    }
}
