package edu.usc.csci310.team16.tutorsearcher;

import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class SearchRobot extends BaseTestRobot {

    public void navToSearch() {
        super.clickButton(R.id.navigation_search);
    }

    public void navAway() {
        super.clickButton(R.id.navigation_profile);
    }

    public void startSearch() {
        super.clickButton(R.id.search_button);
    }

    public void fillCourse(String course) {
        onView(withId(R.id.course_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(course))).perform(click());
    }

    public void fillAvailability(List<Integer> availability) {
        //access id equal to elements of the availability array
        for(int slot : availability) {
            onView(withTagValue(is((Object)("availability_checkbox_" + slot)))).perform(click());
        }

    }

    public void submitSearch() {
        onView(withId(R.id.bottom_view)).perform(scrollTo());
        super.clickButton(R.id.search_button);
    }
}
