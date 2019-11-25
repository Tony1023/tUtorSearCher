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

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;

import edu.usc.csci310.team16.tutorsearcher.Tutor;
import edu.usc.csci310.team16.tutorsearcher.TutorFragment;
import edu.usc.csci310.team16.tutorsearcher.UserProfile;


@RunWith(AndroidJUnit4.class)
public class RatingTest extends BaseTests {

    /**
     * Inherited:
     * protected MockWebServer server
     */

    private RatingRobot robot = new RatingRobot();
    private LoginRobot loginRobot = new LoginRobot();
    private  ArrayList<UserProfile> tutors;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        user.setRating(0);
        server.enqueue(new MockResponse()
                        .setBody(gson.toJson(user))
        );
        loginRobot.login("tony@usc.edu", "password");


       tutors = new ArrayList<UserProfile>();
        tutors.add(user);

        server.enqueue(new MockResponse()
                .setBody(gson.toJson(tutors))
        );

        onView(withId(R.id.navigation_tutors)).perform(click());
        onView(withId(R.id.gotoprofile)).perform(click());
    }

    // Viewing tutor profile should show their rating -> 0 at first
    @Test
    public void testViewRating() {
        String rating = "0.0";
        onView(withId(R.id.ratingText)).check(matches(withText(rating))); //default value
    }

    // Rating tutor should submit new chosen rating
    @Test
    public void testRate() {
        String rating = "2.5";
        tutors.get(0).setRating(2.5);
        onView(withId(R.id.simpleRatingBar)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.ratingText)).check(matches(withText(rating)));
    }

    // Logging in as other user and going to same tutor should show their new rating
    @Test
    public void testSavedRating() {

        tutors.get(0).setRating(2.5);
        loginRobot.logout();
        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Adina");
        user.setEmail("adina@usc.edu");
        user.setRating(0);
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
        );
        loginRobot.login("adina@usc.edu", "password");


        server.enqueue(new MockResponse()
                .setBody(gson.toJson(tutors))
        );

        onView(withId(R.id.navigation_tutors)).perform(click());
        onView(withId(R.id.gotoprofile)).perform(click());

        String rating = "2.5";
        onView(withId(R.id.ratingText)).check(matches(withText(rating))); //default value
    }
}
