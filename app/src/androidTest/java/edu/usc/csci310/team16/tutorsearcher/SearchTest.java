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

    // Should display results when non-empty results
    @Test
    public void testDisplayResultProfile() {
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
            onView(withId(R.id.search_result_card)).perform(click());
        } catch (NoMatchingViewException nmve) {
            fail("no search results displayed");
        }

        onView(withId(R.id.name)).check(matches(withText(user.getName())));
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
}
