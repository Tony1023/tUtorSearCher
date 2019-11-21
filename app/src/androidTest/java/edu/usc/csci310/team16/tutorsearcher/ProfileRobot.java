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

public class ProfileRobot extends BaseTestRobot {

    //checkbox stuff
    // True test - checkbox checked manually with perform(click())
    //onView(withId(R.id.checkbox)).perform(click()).check(matches(ViewMatchers.isChecked()));
    // False test - checkbox not checked
    //onView(withId(R.id.checkbox)).check(matches(ViewMatchers.isChecked()));
    private String[] courseCodes = {"CSCI103", "CSCI104", "CSCI170", "CSCI201", "CSCI270", "CSCI310", "CSCI350",
            "CSCI356", "CSCI360"};

    //functions to fill in each field on edit profile individually
    public void fillName(String name) {
        super.fillEditText(R.id.name, name);
    }

    public void fillGrade(String grade) {

        onView(withId(R.id.grade_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(grade))).perform(click());

//        onView(allOf(withId(R.id.grade_spinner), withText(grade))).perform(click());
    }

    //NOT WORKING BECAUSE OF EDIT TEXT ISSUES
    public void fillBio(String bio) {

//        super.fillEditText(R.id.bio, bio);

        onView(withId(R.id.bio)).perform(replaceText(bio));
    }

    //NOT WORKING BECAUSE OF SCROLLING ISSUES
    public void fillCoursesTaken(List<String> coursesTaken) {

        //loop through courses the user has taken
        for(int i = 0; i < coursesTaken.size(); i++) {

            String courseNumber = coursesTaken.get(i).substring(4);
            onView(withId(R.id.submit_button)).perform(scrollTo());
            onView(withTagValue(is((Object)(courseNumber+"taken")))).perform(click());

        }

    }

    //NOT WORKING BECAUSE OF SCROLLING ISSUES
    public void fillTutoringCourses(List<String> tutorClasses) {
        //loop through courses the user has taken
        for(int i = 0; i < tutorClasses.size(); i++) {

            String courseNumber = tutorClasses.get(i).substring(4);
            onView(withTagValue(is((Object)(courseNumber+"tutoring")))).perform(scrollTo()).perform(closeSoftKeyboard()).perform(click());

        }
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
        //BIO NOT WORKING BECAUSE APPARENTLY IT ISN'T ASSIGNABLE FROM ANDROID.WIDGET.EDITTEXT
        fillBio(bio);
        fillAvailability(availability);
        fillCoursesTaken(coursesTaken);
        fillTutoringCourses(tutorClasses);


        submitEdits();
    }

    public void submitEdits() {

        onView(withId(R.id.bottom_view)).perform(scrollTo());
//        onView(withId(R.id.submit_button)).perform(click());
        super.clickButton(R.id.submit_button);
    }
}
