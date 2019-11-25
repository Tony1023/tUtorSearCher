package edu.usc.csci310.team16.tutorsearcher;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)

public class SearchTest extends BaseTests {

    private Gson gson = new Gson();
    private SearchRobot robot = new SearchRobot();
    private LoginRobot loginRobot = new LoginRobot();

    @Override
    public void setUp() throws Exception {
        super.setUp();

        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse()
                        .setBody(gson.toJson(user))
        );
        loginRobot.login("tony@usc.edu", "password");

        robot.navToSearch();
        robot.startSearch();
    }

    // Should display empty list when no results
    @Test
    public void testEmptySearch() {
        final List<UserProfile> expectedResults = new ArrayList<>();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        robot.submitSearch();

        try {
            onView(withId(R.id.search_result_name)).check(matches(isDisplayed()));
            fail("Search result exists, but there should be none");
        } catch (NoMatchingViewException nmve) {

        }
    }

    // Should display results when non-empty results
    @Test
    public void testNonemptySearch() {
        final List<UserProfile> r = new ArrayList<>();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Micah");
        user.setEmail("micah@usc.edu");
        r.add(user);
        final List<UserProfile> expectedResults = new ArrayList<>(r);
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        robot.submitSearch();

        try {
            onView(withId(R.id.search_result_name)).check(matches(withText(user.getName())));
        } catch (NoMatchingViewException nmve) {
            fail("no search results displayed");
        }
    }

    // Network error should display an error message
    @Test
    public void testErrorMessage() throws IOException {
        server.shutdown();

        robot.submitSearch();

        // TODO: add asserts
        try {
            onView(withId(R.id.error_message)).check(matches(withText("Search failed: network error")));
        } catch (NoMatchingViewException nmve) {
            fail("error message not displayed");
        }
    }

    // Query parameters should persist between searches
    @Test
    public void testQueryPersistenceBetweenSearches() {
        String course = "CSCI310";

        List<Integer> availability = new ArrayList<>();
        availability.add(1);
        availability.add(2);
        availability.add(3);

        robot.fillCourse(course);
        robot.fillAvailability(availability);

        final List<UserProfile> expectedResults = new ArrayList<>();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        robot.submitSearch();

        robot.startSearch();

        onView(withId(R.id.course_spinner)).check(matches(withSpinnerText(course)));

        for(int slot : availability) {
            onView(withTagValue(is((Object)("availability_checkbox_" + slot)))).check(matches(isChecked()));
        }
    }

    // Search results should persist after changing tabs
    @Test
    public void testResultPersistenceAfterTabChange() {
        final List<UserProfile> r = new ArrayList<>();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Micah");
        user.setEmail("micah@usc.edu");
        r.add(user);
        final List<UserProfile> expectedResults = new ArrayList<>(r);
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        robot.submitSearch();

        robot.navAway();
        robot.navToSearch();

        try {
            onView(withId(R.id.search_result_name)).check(matches(withText(user.getName())));
        } catch (NoMatchingViewException nmve) {
            fail("no search results displayed");
        }
    }

//    @Test
//    public void testNoEdits() {
////        robot.submitEdits(); //hit the submit button
//
//        //check that everything is empty on the profile page
//        onView(withId(R.id.name)).perform(scrollTo()).check(matches(withText("")));
//        onView(withId(R.id.grade)).check(matches(withText("Freshman"))); //default value
//
//        //can't check availability bc couldn't find way to check background color
//
//        onView(withId(R.id.bio)).check(matches(withText("")));
//        onView(withId(R.id.courses_taken)).check(matches(withText("")));
//        onView(withId(R.id.courses_tutoring)).check(matches(withText("")));
//
//    }

//    //check all class checkboxes to make sure they appear on page
//    @Test
//    public void testAllCheckboxes() {
//        List<String> coursesTaken = new ArrayList<String>();
//        coursesTaken.add("CSCI103");
//        coursesTaken.add("CSCI104");
//        coursesTaken.add("CSCI170");
//        coursesTaken.add("CSCI201");
//        coursesTaken.add("CSCI270");
//        coursesTaken.add("CSCI310");
//        coursesTaken.add("CSCI350");
//        coursesTaken.add("CSCI356");
//        coursesTaken.add("CSCI360");
//        robot.fillCoursesTaken(coursesTaken);
//
//        List<String> coursesTutoring = new ArrayList<String>();
//        coursesTutoring.add("CSCI103");
//        coursesTutoring.add("CSCI104");
//        coursesTutoring.add("CSCI170");
//        coursesTutoring.add("CSCI201");
//        coursesTutoring.add("CSCI270");
//        coursesTutoring.add("CSCI310");
//        coursesTutoring.add("CSCI350");
//        coursesTutoring.add("CSCI356");
//        coursesTutoring.add("CSCI360");
//        robot.fillTutoringCourses(coursesTutoring);
//
//        robot.submitEdits();
//
//        //check to make sure all the courses appear
//        onView(withId(R.id.rating)).perform(scrollTo()); //scroll down to see courses taken/tutoring
//        onView(withId(R.id.courses_taken)).perform(scrollTo()).check(matches(withText
//                ("CSCI103, CSCI104, CSCI170, CSCI201, CSCI270, CSCI310, CSCI350, CSCI356, CSCI360")));
//        onView(withId(R.id.courses_tutoring)).perform(scrollTo()).check(matches(withText
//                ("CSCI103, CSCI104, CSCI170, CSCI201, CSCI270, CSCI310, CSCI350, CSCI356, CSCI360")));
//    }
//
//    //fill all the fields and make sure the data matches on the profile page
//    @Test
//    public void testAll() {
//        String name = "Teagan";
//        robot.fillName(name);
//
//        String grade = "Junior";
//        robot.fillGrade(grade);
//
//        String bio = "web development is my Passion";
//        robot.fillBio(bio);
//
//        List<Integer> availability = new ArrayList<Integer>();
//        availability.add(1);
//        availability.add(2);
//        availability.add(3);
//        robot.fillAvailability(availability);
//
//        List<String> coursesTaken = new ArrayList<String>();
//        coursesTaken.add("CSCI103");
//        coursesTaken.add("CSCI104");
//        robot.fillCoursesTaken(coursesTaken);
//
//        List<String> coursesTutoring = new ArrayList<String>();
//        coursesTutoring.add("CSCI356");
//        coursesTutoring.add("CSCI360");
//        robot.fillTutoringCourses(coursesTutoring);
//
//        robot.submitEdits();
//
//        //check that all the fields are filled correctly on profile page
//        onView(withId(R.id.name)).perform(scrollTo()).check(matches(withText("Teagan")));
//        onView(withId(R.id.grade)).perform(scrollTo()).check(matches(withText("Junior"))); //default value
//
//        //can't check availability bc couldn't find way to check background color
//        onView(withId(R.id.courses_taken)).perform(scrollTo()); //scroll down to see bio
//        // FILL THIS ONCE BIO IS WORKING
//        onView(withId(R.id.bio)).check(matches(withText("")));
//
//        onView(withId(R.id.rating)).perform(scrollTo()); //scroll down to see courses taken/tutoring
//        onView(withId(R.id.courses_taken)).perform(scrollTo()).check(matches(withText("CSCI103, CSCI104")));
//        onView(withId(R.id.courses_tutoring)).perform(scrollTo()).check(matches(withText("CSCI356, CSCI360")));
//    }
//    //---------
//
//    //BLACK BOX TESTING
//
//    //fill up user profile and check that singleton changes values
//    @Test
//    public void checkUserProfile() {
//        String name = "Eric";
//        robot.fillName(name);
//
//        String grade = "Junior";
//        robot.fillGrade(grade);
//
//        String bio = "I like League";
//        robot.fillBio(bio);
//
//        List<Integer> availability = new ArrayList<Integer>();
//        availability.add(0);
//        availability.add(1);
//        robot.fillAvailability(availability);
//
//        List<String> coursesTaken = new ArrayList<String>();
//        coursesTaken.add("CSCI103");
//        coursesTaken.add("CSCI104");
//        coursesTaken.add("CSCI170");
//        coursesTaken.add("CSCI201");
//        coursesTaken.add("CSCI270");
//        coursesTaken.add("CSCI310");
//        robot.fillCoursesTaken(coursesTaken);
//
//        List<String> coursesTutoring = new ArrayList<String>();
//        coursesTutoring.add("CSCI104");
//        coursesTutoring.add("CSCI310");
//        robot.fillTutoringCourses(coursesTutoring);
//
//        robot.submitEdits();
//
//        //check values of UserProfile singleton
//        UserProfile user = UserProfile.getCurrentUser();
//
//        //DO THIS FOR ALL OF THE FIELDS
//        assertEquals(name, user.getName());
//        assertEquals(grade, user.getGrade());
//
//        //  FIX THIS WHEN FIXING BIO
//        assertEquals("", user.getBio());
//
//
//        for(int i = 0; i < availability.size(); i++) {
//            assertEquals(availability.get(i), user.getAvailability().get(i));
//        }
//
//        for(int i = 0; i < coursesTaken.size(); i++) {
//            assertEquals(coursesTaken.get(i), user.getCoursesTaken().get(i));
//        }
//
//        for(int j = 0; j < coursesTutoring.size(); j++) {
//            assertEquals(coursesTutoring.get(j), user.getTutorClasses().get(j));
//        }
//    }
//
//    //edits profile, then edits again and change nothing: the profile should stay the same
//    @Test
//    public void testEditTwice() {
//
//    }
//
//    //Edit profile, log out, log back in, and make sure the changes saved
//    @Test
//    public void testEditLogOutLogIn() {
//
//    }
//
//    //-------
//
//    @Test
//    public void testAvailability() {
//
//        List<Integer> availability = new ArrayList<Integer>();
//        availability.add(1);
//        availability.add(2);
//        availability.add(3);
//
//        robot.fillAvailability(availability);
//
//        //ADD CHECKING THAT THE RIGHT BOXES ARE CHECKED
//
//    }
//
//
//    @Test
//    public void testEditButton() {
////        onView(withId(R.id.edit_button)).perform(click());
//
//        robot.submitEdits();
//    }
//
//    @Test
//    public void testCoursesTaken() {
////        onView(withId(R.id.edit_button)).perform(click());
//
//        List<String> coursesTaken = new ArrayList<String>();
//        coursesTaken.add("CSCI103");
//        coursesTaken.add("CSCI104");
//
//        robot.fillCoursesTaken(coursesTaken);
//
//        //FAILS BECAUSE IT AUTOMATICALLY CLICKS OVER TO SEARCH FOR SOME REASON
//        // D/search fragment: init []
//
//        onView(withId(R.id.cs103_taken)).check(matches(isChecked()));
//        onView(withId(R.id.cs104_taken)).check(matches(isChecked()));
//    }
//
//    @Test
//    public void testCoursesTutoring() {
////        onView(withId(R.id.edit_button)).perform(click());
//
//        List<String> coursesTutoring = new ArrayList<String>();
//        coursesTutoring.add("CSCI356");
//        coursesTutoring.add("CSCI360");
//
//        robot.fillTutoringCourses(coursesTutoring);
//
//        onView(withId(R.id.cs356_tutoring)).check(matches(isChecked()));
//        onView(withId(R.id.cs360_tutoring)).check(matches(isChecked()));
//    }
}
