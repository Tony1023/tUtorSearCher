package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import edu.usc.csci310.team16.tutorsearcher.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginModel loginModel;
    private boolean authenticated = false;

    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        // TODO: Checks if user profile is valid
        // TODO: make network configs HTTP-secure (network_security_config.xml and AndroidManifest.xml)


        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginModel.class);

        binding.setModel(loginModel);
        binding.setLifecycleOwner(this);

        final MaterialButton loginBtn = findViewById(R.id.email_login_button);
        final MaterialButton registerBtn = findViewById(R.id.email_register_button);

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

        loginModel.getCredentials().observe(this, new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                boolean enabled = loginData.email.endsWith("@usc.edu") && loginData.password.length() >= 6;
                loginBtn.setEnabled(enabled);
                registerBtn.setEnabled(enabled);
            }
        });

        TextInputEditText emailInput = findViewById(R.id.email);
        TextInputEditText passwordInput = findViewById(R.id.password);

        emailInput.addTextChangedListener(emailWatcher);
        passwordInput.addTextChangedListener(passwordWatcher);

    }

    private TextWatcher emailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            LoginData cred = loginModel.getCredentials().getValue();
            LoginData updated = new LoginData(cred.email, s.toString());
            loginModel.getCredentials().setValue(updated);
        }
    };

    private TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            LoginData cred = loginModel.getCredentials().getValue();
            LoginData updated = new LoginData(s.toString(), cred.password);
            loginModel.getCredentials().setValue(updated);
        }
    };

    public void onClickRegister(View view) {
        loginModel.register();
    }

    public void onClickLogin(View view) {
        loginModel.login();
    }

}
