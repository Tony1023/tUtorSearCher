package edu.usc.csci310.team16.tutorsearcher;

import java.util.List;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class ProfileRobot extends BaseTestRobot {

    //checkbox stuff
    // True test - checkbox checked manually with perform(click())
    //onView(withId(R.id.checkbox)).perform(click()).check(matches(ViewMatchers.isChecked()));
    // False test - checkbox not checked
    //onView(withId(R.id.checkbox)).check(matches(ViewMatchers.isChecked()));

    //functions to fill in each field on edit profile individually
    public void fillName(String name) {
        super.fillEditText(R.id.name, name);
    }

    public void fillGrade(String grade) {
        onView(allOf(withId(R.id.grade_spinner), withText(grade))).perform(click());
    }

    public void fillBio(String bio) {
        super.fillEditText(R.id.bio, bio);
    }

    public void fillCoursesTaken(List<String> coursesTaken) {

    }

    public void fillTutoringCourses(List<String> tutorClasses) {

    }

    public void fillAvailability(List<Integer> availability) {

        //access id equal to elements of the availability array
        for(int element : availability) {
            onView(withTagValue(is((Object)(element+"box")))).perform(click());
        }

    }

    //shortcut to testing filling all of the profile fields
    public void fillAll(String name, String grade, String bio, List<String> coursesTaken,
                        List<String> tutorClasses, List<Integer> availability) {
        fillName(name);
        fillGrade(grade);
        fillBio(bio);
        fillCoursesTaken(coursesTaken);
        fillTutoringCourses(tutorClasses);
        fillAvailability(availability);

        submitEdits();
    }

    public void submitEdits() {
        super.clickButton(R.id.submit_button);
    }
}
