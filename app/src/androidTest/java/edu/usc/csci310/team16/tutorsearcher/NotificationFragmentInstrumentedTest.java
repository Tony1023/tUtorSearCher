package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.google.common.truth.Truth.assertThat;
import static edu.usc.csci310.team16.tutorsearcher.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
public class NotificationFragmentInstrumentedTest extends BaseTests {
    LoginRobot loginRobot;
    Gson gson;
    ViewInteraction view;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        loginRobot = new LoginRobot();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        gson = new Gson();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(user))
        );
        loginRobot.login("tony@usc.edu", "password");

        view = onView(withId(R.id.navigation_notifications)).perform(click());

    }

    @Test
    public void fragmentExists(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getView().check(matches(isDisplayed())).check(matches(hasChildCount(0)));
    }

    @Test
    public void getNotifications(){
        Notification notification1 =
                new Notification("uuid1",1,2,3,"me",0,
                        "0111000111001101101010","msg",0);

        server.enqueue(new MockResponse()
                .setBody(gson.toJson(new Notification())));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getView().perform(swipeDown());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getView().check(withItemCount(1));
        //assertThat(getView()).isNotNull();
    }

    @Test
    public void updateNotifications(){
        Notification notification1 = new Notification("uuid1",1,2,3,"me",0,"0111000111001101101010","msg",0);
        Notification notification2 = new Notification("uuid2",1,2,3,"me",0,"0111000111001101101010","msg",0);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ViewInteraction getView(){
        return onView(withId(R.id.notifications_view));
    }
}
