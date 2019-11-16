package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

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

@RunWith(AndroidJUnit4.class)
public class LoginTests extends BaseTests {

    /**
     * Inherited:
     * protected MockWebServer server
     */

    private LoginRobot robot = new LoginRobot();

    @Test
    public void testLoginAndLogout() {
        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
                .addHeader("access-token", "accessToken") // not used
        );
        robot.login("tony@usc.edu", "password");
        onView(withId(R.id.name)).check(matches(withText("Tony")));
        robot.logout();
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
    }

    @Test
    public void testWrongCredentials() {
        server.enqueue(new MockResponse().setBody("{}"));
        robot.login("tony@usc.edu", "wrong-password");
        onView(withId(R.id.error_message)).check(matches(withText("Wrong credentials")));
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
        res.put("access-token", "access-token");
        server.enqueue(new MockResponse().setBody(gson.toJson(res)));
        robot.register("usedemail@usc.edu", "secure-password");
        onView(withId(R.id.error_message)).check(matches(withText("Email already registered")));
        robot.register("newemail@usc.edu", "secure-password");
        onView(withId(R.id.name_label)).check(matches(isDisplayed()));
        onView(withId(R.id.name)).check(matches(withText("")));
    }

    @Test
    public void testNetworkErrors() throws Exception {
        server.shutdown();
        robot.register("newemail@usc.edu", "password");
        onView(withId(R.id.error_message)).check(matches(withText("Network errors")));
        robot.login("tony@usc.edu", "password");
        onView(withId(R.id.error_message)).check(matches(withText("Network errors")));
    }

    @Test
    public void testAutoLogin() throws IOException {
        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
                .addHeader("access-token", "someToken") // not used
        );
        robot.login("tony@usc.edu", "password");
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
                .addHeader("access-token", "someToken") // not used
        );
        loginTestRule.finishActivity();
        ActivityTestRule<LoginActivity> loginRule = new ActivityTestRule<>(LoginActivity.class, true, false);
        Intent intent = new Intent();
        loginRule.launchActivity(intent);
        onView(withId(R.id.name)).check(matches(withText("Tony")));
        loginRule.finishActivity();
        server.shutdown();
        ActivityTestRule<LoginActivity> loginRule2 = new ActivityTestRule<>(LoginActivity.class, true, false);
        intent = new Intent();
        loginRule2.launchActivity(intent);
        onView(withId(R.id.error_message)).check(matches(withText("Network errors")));
        SharedPreferences.Editor editor = loginRule2.getActivity().getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }


}
