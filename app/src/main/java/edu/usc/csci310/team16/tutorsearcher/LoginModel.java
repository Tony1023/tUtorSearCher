package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel extends ViewModel {

    private LoginData credentials = new LoginData();
    private MutableLiveData<UserProfile> user = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> token = new MutableLiveData<>();

    public LoginData getCredentials() {
        return credentials;
    }

    MutableLiveData<UserProfile> getUser() {
        return user;
    }

    MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    MutableLiveData<String> getToken() {
        return token;
    }


    void register() {
        RemoteServerDAO.getDao().register(credentials).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                if (response.body() != null) {
                    // Looks like postValue calls are queued
                    token.postValue(response.headers().get("access-token"));
                    UserProfile profile = new UserProfile();
                    profile.setId(response.body());
                    profile.setEmail(credentials.getEmail().getValue());
                    user.postValue(profile);
                } else {
                    errorMessage.postValue("Something wrong occurred.");
                }
            }

            @Override
            public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {

            }
        });
    }

    void login() {
        RemoteServerDAO.getDao().login(credentials).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(@NotNull Call<UserProfile> call, @NotNull Response<UserProfile> response) {

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });

    }

}
