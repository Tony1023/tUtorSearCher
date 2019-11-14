package edu.usc.csci310.team16.tutorsearcher;

import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTests extends BaseTests {

    /**
     * Inherited:
     * protected MockWebServer server
     */

    private LoginRobot robot = new LoginRobot();

    @Test
    public void testLanding() {
        onView(withId(R.id.error_message)).check(matches(withText("")));
    }

    @Test
    public void testLogin() {
        Gson gson = new Gson();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("tony");
        user.setEmail("tony@usc.edu");
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
                .addHeader("access-token", "someToken") // not used
        );
        robot.login("tony@usc.edu", "password");
        robot.getTextView(R.id.name).check(matches(withText("tony")));
    }

}
