package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.bouncycastle.util.Integers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class ProfileTest extends BaseTests {

    private ProfileRobot robot = new ProfileRobot();
    private LoginRobot loginRobot = new LoginRobot();

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
                //.addHeader("access-token", "accessToken") // not used
        );
        loginRobot.login("tony@usc.edu", "password");
    }

    @Test
    public void testTest() {

        onView(withId(R.id.edit_button)).perform(click());

        List<Integer> availability = new ArrayList<Integer>();
        availability.add(1);
        availability.add(2);
        availability.add(3);

        robot.fillAvailability(availability);

    }

    @Test
    public void testAll() {
        onView(withId(R.id.edit_button)).perform(click());

        String name = "teagan";
        String grade = "Freshman";

        String bio = "i hate android development";

        List<Integer> availability = new ArrayList<Integer>();
        availability.add(1);
        availability.add(2);
        availability.add(3);

        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI356");
        coursesTutoring.add("CSCI360");

        robot.fillAll(name, grade, bio, coursesTaken, coursesTutoring, availability);

    }
}
