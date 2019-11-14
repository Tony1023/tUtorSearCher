package edu.usc.csci310.team16.tutorsearcher;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import edu.usc.csci310.team16.tutorsearcher.databinding.ActivityLoginBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        final MaterialButton loginBtn;
        final MaterialButton registerBtn;
        // TODO: make network configs HTTP-secure (network_security_config.xml and AndroidManifest.xml)

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(LoginModel.class);

        binding.setModel(loginModel);
        binding.setLifecycleOwner(this);

        SharedPreferences shared = getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE);
        int id = shared.getInt("userId", -1);
        String token = shared.getString("accessToken", null);
        if (id != -1 && token != null) {
            loginModel.validate(id, token);
        }

        loginBtn = findViewById(R.id.email_login_button);
        registerBtn = findViewById(R.id.email_register_button);

        loginModel.getUser().observe(this, new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile profile) {
                UserProfile.setCurrentUser(profile); // The user session object
                SharedPreferences.Editor editor = getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE).edit();
                editor.putInt("userId", profile.getId());
                editor.putString("email", profile.getEmail());
                editor.apply();
                RemoteServerDAO.setId(profile.getId());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        loginModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView error = findViewById(R.id.error_message);
                error.setText(s);
            }
        });

        loginModel.getToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                SharedPreferences.Editor editor = getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE).edit();
                editor.putString("accessToken", s);
                editor.apply();
                RemoteServerDAO.setToken(s);
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

        loginModel.getValidating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean validating) {
//                showProgress(validating);
            }
        });

        TextInputEditText emailInput = findViewById(R.id.email);
        TextInputEditText passwordInput = findViewById(R.id.password);

        emailInput.addTextChangedListener(emailWatcher);
        passwordInput.addTextChangedListener(passwordWatcher);

    }

    private void showProgress(final boolean show) {
        long animTime = 250;

        final ScrollView loginForm = findViewById(R.id.login_form);
        final ProgressBar loginProgress = findViewById(R.id.login_progress);
        loginProgress.setVisibility(View.VISIBLE);
        loginProgress.animate()
                .setDuration(animTime)
                .alpha(show ? 1 : 0)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                });

        loginForm.setVisibility(View.VISIBLE);
        loginForm.animate()
                .setDuration(animTime)
                .alpha(show ? 0 : 1)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                });
    }

    private TextWatcher emailWatcher = new TextWatcher() {
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

    private TextWatcher passwordWatcher = new TextWatcher() {
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

    public void onClickRegister(View view) {
        loginModel.register();
    }

    public void onClickLogin(View view) {
        loginModel.login();
    }
}
