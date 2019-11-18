package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginTest extends BaseTests {

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
        );
        robot.login("tony@usc.edu", "password");
        onView(withId(R.id.name)).check(matches(withText("Tony")));
        robot.logout();
        onView(withId(R.id.login_form)).check(matches(isDisplayed()));
    }

    @Test
    public void testAutoLogin() throws InterruptedException {
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
        SharedPreferences.Editor editor = loginRule.getActivity().getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
//        RecordedRequest recordedRequest = server.takeRequest();
//        recordedRequest = server.takeRequest();
//        final Thread currentThread = Thread.currentThread();
//        Thread timeoutThread = new Thread() {
//            @Override public void run() {
//                try {
//                    Thread.sleep(1000);
//                    currentThread.interrupt();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        timeoutThread.start();
//        try {
//            recordedRequest = server.takeRequest();
//        } catch (InterruptedException e) {
//            recordedRequest = null;
//        }
        loginRule.finishActivity();
    }


}
