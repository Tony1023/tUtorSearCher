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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.is;

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

        onView(withId(R.id.edit_button)).perform(click());
    }

    //BLACK BOX TESTING: Espresso

    //don't fill in any of the fields; make sure everything is empty on profile page
    @Test
    public void testNoEdits() {
        robot.submitEdits(); //hit the submit button

        //check that everything is empty on the profile page
        onView(withId(R.id.name)).perform(scrollTo()).check(matches(withText("")));
        onView(withId(R.id.grade)).check(matches(withText("Freshman"))); //default value

        //can't check availability bc couldn't find way to check background color

        onView(withId(R.id.bio)).check(matches(withText("")));
        onView(withId(R.id.courses_taken)).check(matches(withText("")));
        onView(withId(R.id.courses_tutoring)).check(matches(withText("")));

    }

    //check all class checkboxes to make sure they appear on page
    @Test
    public void testCheckboxes() {
        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        coursesTaken.add("CSCI170");
        coursesTaken.add("CSCI201");
        coursesTaken.add("CSCI270");
        coursesTaken.add("CSCI310");
        coursesTaken.add("CSCI350");
        coursesTaken.add("CSCI356");
        coursesTaken.add("CSCI360");
        robot.fillCoursesTaken(coursesTaken);

        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI103");
        coursesTutoring.add("CSCI104");
        coursesTutoring.add("CSCI170");
        coursesTutoring.add("CSCI201");
        coursesTutoring.add("CSCI270");
        coursesTutoring.add("CSCI310");
        coursesTutoring.add("CSCI350");
        coursesTutoring.add("CSCI356");
        coursesTutoring.add("CSCI360");
        robot.fillTutoringCourses(coursesTutoring);

        robot.submitEdits();

        //check to make sure all the courses appear
        onView(withId(R.id.rating)).perform(scrollTo()); //scroll down to see courses taken/tutoring
        onView(withId(R.id.courses_taken)).perform(scrollTo()).check(matches(withText
                ("CSCI103, CSCI104, CSCI170, CSCI201, CSCI270, CSCI310, CSCI350, CSCI356, CSCI360")));
        onView(withId(R.id.courses_tutoring)).perform(scrollTo()).check(matches(withText
                ("CSCI103, CSCI104, CSCI170, CSCI201, CSCI270, CSCI310, CSCI350, CSCI356, CSCI360")));
    }

    //fill all the fields and make sure the data matches on the profile page
    @Test
    public void testAll() {
        String name = "Teagan";
        robot.fillName(name);

        String grade = "Junior";
        robot.fillGrade(grade);

        String bio = "web development is my Passion";
        robot.fillBio(bio);

        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        robot.fillCoursesTaken(coursesTaken);

        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI356");
        coursesTutoring.add("CSCI360");
        robot.fillTutoringCourses(coursesTutoring);

        robot.submitEdits();

        //check that all the fields are filled correctly on profile page
        onView(withId(R.id.name)).perform(scrollTo()).check(matches(withText("Teagan")));
        onView(withId(R.id.grade)).perform(scrollTo()).check(matches(withText("Junior"))); //default value

        //can't check availability bc couldn't find way to check background color
        onView(withId(R.id.courses_taken)).perform(scrollTo()); //scroll down to see bio
        onView(withId(R.id.bio)).check(matches(withText("web development is my Passion")));

        onView(withId(R.id.rating)).perform(scrollTo()); //scroll down to see courses taken/tutoring
        onView(withId(R.id.courses_taken)).perform(scrollTo()).check(matches(withText("CSCI103, CSCI104")));
        onView(withId(R.id.courses_tutoring)).perform(scrollTo()).check(matches(withText("CSCI356, CSCI360")));
    }

    //make sure the fields on the edit profile page prefill with current values
    @Test
    public void checkEditProfilePrefill() {
        String name = "Teagan";
        robot.fillName(name);

        String grade = "Junior";
        robot.fillGrade(grade);

        String bio = "web development is my Passion";
        robot.fillBio(bio);

        //CAN DELETE THIS PROBABLY
        ArrayList<Integer> availability = new ArrayList<>();
        availability.add(0);

        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        robot.fillCoursesTaken(coursesTaken);

        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI356");
        coursesTutoring.add("CSCI360");
        robot.fillTutoringCourses(coursesTutoring);

        server.enqueue(new MockResponse());

        robot.submitEdits();

        //log out and immediately log back in
        loginRobot.logout();


        UserProfile user = UserProfile.getCurrentUser();

        Gson gson = new Gson();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
        );
        loginRobot.login("tony@usc.edu", "password");

        //get to EditProfile page
        onView(withId(R.id.edit_button)).perform(click());

        onView(withId(R.id.name)).perform(scrollTo()).check(matches(withText("Teagan")));
        onView(withId(R.id.grade)).check(matches(withText("Junior"))); //default value

        //can't check availability bc couldn't find way to check background color

        onView(withId(R.id.bio)).check(matches(withText(bio)));
//        onView(withId(R.id.courses_taken)).check(matches(withText("")));
//        onView(withId(R.id.courses_tutoring)).check(matches(withText("")));

    }

    //---------

    //WHITE BOX TESTING: JUnit

    //fill up user profile and check that singleton changes values
    @Test
    public void checkUserProfile() {
        String name = "Eric";
        robot.fillName(name);

        String grade = "Junior";
        robot.fillGrade(grade);

        String bio = "I like League";
        robot.fillBio(bio);

        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        coursesTaken.add("CSCI170");
        coursesTaken.add("CSCI201");
        coursesTaken.add("CSCI270");
        coursesTaken.add("CSCI310");
        robot.fillCoursesTaken(coursesTaken);

        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI104");
        coursesTutoring.add("CSCI310");
        robot.fillTutoringCourses(coursesTutoring);

        robot.submitEdits();

        //check values of UserProfile singleton
        UserProfile user = UserProfile.getCurrentUser();

        //DO THIS FOR ALL OF THE FIELDS
        assertEquals(name, user.getName());
        assertEquals(grade, user.getGrade());
        assertEquals(bio, user.getBio());

        //verify checkboxes
        for(int i = 0; i < coursesTaken.size(); i++) {
            assertEquals(coursesTaken.get(i), user.getCoursesTaken().get(i));
        }

        for(int j = 0; j < coursesTutoring.size(); j++) {
            assertEquals(coursesTutoring.get(j), user.getTutorClasses().get(j));
        }
    }

    //edits profile, then edits again and change nothing: the profile should stay the same
    @Test
    public void testEditTwice() {
        String name = "Teagan";
        robot.fillName(name);

        String grade = "Junior";
        robot.fillGrade(grade);

        String bio = "android development is hard :(";
        robot.fillBio(bio);

        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        robot.fillCoursesTaken(coursesTaken);

        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI170");
        coursesTutoring.add("CSCI270");
        robot.fillTutoringCourses(coursesTutoring);


        robot.submitEdits();

        //now on profile page; click to edit again
        onView(withId(R.id.edit_button)).perform(click());

        //don't change anything and immediately submit
        robot.submitEdits();

        //check values of UserProfile singleton
        UserProfile user = UserProfile.getCurrentUser();

        //check that the name, grade, and bio are the same in UserProfile
        assertEquals(name, user.getName());
        assertEquals(grade, user.getGrade());
        assertEquals(bio, user.getBio());

        //check that courses taken are the same
        assertEquals(coursesTaken.get(0), user.getCoursesTaken().get(0));
        assertEquals(coursesTaken.get(1), user.getCoursesTaken().get(1));

        //check that courses tutoring are the same
        assertEquals(coursesTutoring.get(0), user.getTutorClasses().get(0));
        assertEquals(coursesTutoring.get(1), user.getTutorClasses().get(1));
    }

    //Edit profile, log out, log back in, and make sure the changes saved
    @Test
    public void testEditLogOutLogIn() {

        String name = "Teagan";
        robot.fillName(name);

        String grade = "Junior";
        robot.fillGrade(grade);

        String bio = "web development is my Passion";
        robot.fillBio(bio);

        List<String> coursesTaken = new ArrayList<String>();
        coursesTaken.add("CSCI103");
        coursesTaken.add("CSCI104");
        robot.fillCoursesTaken(coursesTaken);

        List<String> coursesTutoring = new ArrayList<String>();
        coursesTutoring.add("CSCI356");
        coursesTutoring.add("CSCI360");
        robot.fillTutoringCourses(coursesTutoring);

        server.enqueue(new MockResponse());

        robot.submitEdits();

        //log out and immediately log back in
        loginRobot.logout();


        UserProfile user = UserProfile.getCurrentUser();

        Gson gson = new Gson();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
        );
        loginRobot.login("tony@usc.edu", "password");

//        onView(withId(R.id.edit_button)).perform(click());

        //check all the UserProfile fields
        //check values of UserProfile singleton
        user = UserProfile.getCurrentUser();

        //check that the name, grade, and bio are the same in UserProfile
        assertEquals(name, user.getName());
        assertEquals(grade, user.getGrade());
        assertEquals(bio, user.getBio());

        //check that courses taken are the same
        assertEquals(coursesTaken.get(0), user.getCoursesTaken().get(0));
        assertEquals(coursesTaken.get(1), user.getCoursesTaken().get(1));

        //check that courses tutoring are the same
        assertEquals(coursesTutoring.get(0), user.getTutorClasses().get(0));
        assertEquals(coursesTutoring.get(1), user.getTutorClasses().get(1));
    }

    //-------

}
