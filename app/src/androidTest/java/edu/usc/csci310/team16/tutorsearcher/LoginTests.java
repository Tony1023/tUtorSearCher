package edu.usc.csci310.team16.tutorsearcher;

import android.content.Intent;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTests  {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    private LoginRobot robot = new LoginRobot();

    @Test
    public void testLanding() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);

        onView(withId(R.id.error_message)).check(matches(withText("Your session has ended, please log in again")));
    }

    @Test
    public void testLogin() {
        Intent intent = new Intent();
        activityRule.launchActivity(intent);
        robot.login("tony@usc.edu", "password");

    }

}
