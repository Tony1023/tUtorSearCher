package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class LoginModelTest extends LiveDataTestBase {

    private LoginModel model = new LoginModel();

    @Test
    public void testLogin() {
        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
                .addHeader("access-token", "accessToken") // not used
        );

        model.getUser().observeForever(new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                assertThat(userProfile.getName()).isEqualTo("Tony");
            }
        });
        model.getToken().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertThat(s).isEqualTo("accessToken");
            }
        });
        model.getCredentials().setValue(new LoginData("tony@usc.edu", "password"));
        model.getCredentials().observeForever(new Observer<LoginData>() {
            @Override
            public void onChanged(LoginData loginData) {
                assertThat(loginData.email).isEqualTo("tony@usc.edu");
                assertThat(loginData.password).isEqualTo("password");
            }
        });
        model.login();
    }

    @Test
    public void testWrongCredentials() {
        server.enqueue(new MockResponse().setBody("{}"));
        model.getCredentials().setValue(new LoginData("tony@usc.edu", "wrong-password"));
        model.getErrorMessage().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertThat(s).isEqualTo("Wrong credentials");
            }
        });

        model.login();
    }

    @Test
    public void testRegistration() {
        Gson gson = new Gson();
        Map<String, Object> res = new HashMap<>();
        res.put("err", "Email already registered");
        res.put("success", false);
        server.enqueue(new MockResponse().setBody(gson.toJson(res)));
        res.remove("err");
        res.put("success", true);
        res.put("id", 20);
        server.enqueue(new MockResponse().setBody(gson.toJson(res)).addHeader("access-token", "access-token"));
        model.getCredentials().setValue(new LoginData("exist@usc.edu", "password"));
        model.getErrorMessage().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertThat(s).isEqualTo("Email already registered");
            }
        });
        model.register();

        model.getCredentials().setValue(new LoginData("new@usc.edu", "password"));
        model.getUser().observeForever(new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                assertThat(userProfile.getId()).isEqualTo(20);
                assertThat(userProfile.getName()).isEmpty();
            }
        });
        model.getToken().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertThat(s).isEqualTo("access-token");
            }
        });
        model.register();
    }

    @Test
    public void testValidation() {
        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse().setBody(gson.toJson(user)));
        server.enqueue(new MockResponse().setBody("{}"));
        model.getUser().observeForever(new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                assertThat(userProfile.getId()).isEqualTo(1);
                assertThat(userProfile.getEmail()).isEqualTo("tony@usc.edu");
                assertThat(userProfile.getName()).isEqualTo("Tony");
            }
        });
        model.validate(1, "token");

        model.getErrorMessage().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertThat(s).isEqualTo("Your session has ended, please log in again");
            }
        });
        model.validate(1, "expired-token");
    }

    @Test
    public void testNetworkErrors() throws IOException {
        server.shutdown();
        model.getErrorMessage().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                assertThat(s).isEqualTo("Network errors");
            }
        });

        model.register();
        model.login();
        model.validate(1, "token");
    }

}
