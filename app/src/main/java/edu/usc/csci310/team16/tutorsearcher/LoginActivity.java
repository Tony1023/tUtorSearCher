package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import edu.usc.csci310.team16.tutorsearcher.databinding.ActivityLoginBinding;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginModel loginModel;
    private boolean authenticated = false;

    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        // Checks if user profile is valid


        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginModel.class);

        binding.setModel(loginModel);
        binding.setLifecycleOwner(this);

        loginModel.getUser().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile profile) {
                UserProfile.setCurrentUser(profile); // The user session object
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        loginModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                System.out.println(s);
            }
        });

        loginModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                editor.putString("accessToken", s);
                editor.commit();
            }
        });

    }

    public void onClickRegister(View view) {
        loginModel.register();
    }

    public void onClickLogin(View view) {
        loginModel.login();
    }

}
