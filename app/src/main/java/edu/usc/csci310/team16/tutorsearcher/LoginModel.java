package edu.usc.csci310.team16.tutorsearcher;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel extends ViewModel {

    private MutableLiveData<LoginData> credentials = new MutableLiveData<>(new LoginData("", ""));
    private MutableLiveData<UserProfile> user = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<String> token = new MutableLiveData<>();
    private MutableLiveData<Boolean> validating = new MutableLiveData<>();

    public MutableLiveData<LoginData> getCredentials() {
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

    MutableLiveData<Boolean> getValidating() {
        return validating;
    }

    void register() {
        validating.setValue(true);
//        RemoteServerDAO.getDao().register(credentials.getValue()).enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
//                if (response.body() != null) {
//                    // Looks like postValue calls are queued
//                    token.postValue(response.headers().get("access-token"));
//                    UserProfile profile = new UserProfile();
//                    profile.setId(response.body());
//                    profile.setEmail(credentials.getValue().email);
//                    user.postValue(profile);
//                } else {
//                    validating.postValue(false);
//                    errorMessage.postValue("Something wrong occurred.");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
//                t.printStackTrace();
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    token.postValue("ACCESS_TOKEN");
                    UserProfile profile = new UserProfile();
                    profile.setId(1);
                    profile.setEmail("email@usc.edu");
                    user.postValue(profile);
                }
            }
        }).start();
    }

    void login() {
//        validating.setValue(true);
//        RemoteServerDAO.getDao().login(credentials.getValue()).enqueue(new Callback<UserProfile>() {
//            @Override
//            public void onResponse(@NonNull Call<UserProfile> call, @NonNull Response<UserProfile> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<UserProfile> call, Throwable t) {
//
//            }
//        });

    }

    void validate(String email, String token) {
        validating.setValue(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    validating.postValue(false);
                }
            }
        }).start();
//        RemoteServerDAO.getDao().validate(email, token).enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                return;
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//
//            }
//        });
    }

}
