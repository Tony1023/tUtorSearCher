package edu.usc.csci310.team16.tutorsearcher;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

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
        LoginData hashedData = new LoginData(credentials.getValue().email, getSHAString(credentials.getValue().password));
        RemoteServerDAO.getDao().register(hashedData).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                validating.postValue(false);
                // Looks like postValue calls are queued
                Map<String, Object> res = response.body();
                Boolean success = (Boolean) res.get("success");
                if (success) {
                    Integer id = ((Double)res.get("id")).intValue();
                    token.postValue(response.headers().get("access-token"));
                    UserProfile profile = new UserProfile();
                    profile.setId(id);
                    profile.setEmail(credentials.getValue().email);
                    user.postValue(profile);
                } else if (res.get("err") instanceof String){
                    errorMessage.postValue((String) res.get("err"));
                } else {
                    errorMessage.postValue("Oof, something went wrong.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                validating.postValue(false);
                errorMessage.postValue("Network errors");
            }
        });
    }

    void login() {
        validating.setValue(true);
        LoginData hashedData = new LoginData(credentials.getValue().email, getSHAString(credentials.getValue().password));
        Log.i("hash", hashedData.password);
        RemoteServerDAO.getDao().login(hashedData).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(@NonNull Call<UserProfile> call, @NonNull Response<UserProfile> response) {
                validating.postValue(false);
                UserProfile profile = response.body();
                if (profile.getId() >= 0) {
                    token.postValue(response.headers().get("access-token"));
                    user.postValue(profile);
                } else {
                    errorMessage.postValue("Wrong credentials");
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                validating.postValue(false);
                errorMessage.postValue("Network errors");
                t.printStackTrace();
            }
        });

    }

    void validate(Integer id, String token) {
        validating.setValue(true);
        RemoteServerDAO.getDao().validate(id, token).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                validating.postValue(false);
                UserProfile profile = response.body();
                if (profile.getId() >= 0) {
                    user.postValue(profile);
                } else {
                    errorMessage.postValue("Your session has ended, please log in again");
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                validating.postValue(false);
                errorMessage.postValue("Network errors");
                t.printStackTrace();
            }
        });
    }

    private String getSHAString(String input) {
        String sha = input;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);

            // Convert message digest into hex value
            StringBuilder hexString = new StringBuilder(number.toString(16));

            // Pad with leading zeros
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            sha = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha;
    }
}
