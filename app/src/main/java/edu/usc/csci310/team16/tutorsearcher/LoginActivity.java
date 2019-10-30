package edu.usc.csci310.team16.tutorsearcher;

import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import edu.usc.csci310.team16.tutorsearcher.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginModel.class);

        binding.setModel(loginModel);
        binding.setLifecycleOwner(this);

        loginModel.credentials.observe(this, new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                return;
            }
        });

        EditText email = findViewById(R.id.email);
        email.addTextChangedListener(new EmailWatcher());
        EditText password = findViewById(R.id.password);
        password.addTextChangedListener(new PasswordWatcher());

        Button emailRegisterButton = findViewById(R.id.email_register_button);
        emailRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginModel.register();
            }
        });

        Button emailSignInButton = findViewById(R.id.email_sign_in_button);
        emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private class EmailWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            LoginData data = loginModel.credentials.getValue();
            if (data == null) {
                data = new LoginData();
            }
            data.email = s.toString();
            loginModel.credentials.setValue(data);
        }
    }

    private class PasswordWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
