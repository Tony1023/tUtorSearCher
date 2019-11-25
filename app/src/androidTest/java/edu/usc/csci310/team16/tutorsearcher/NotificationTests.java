package edu.usc.csci310.team16.tutorsearcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class NotificationTests {

    public void prepareDB(){

    }

    @Test
    public void reloadNotifications(){
        prepareDB();
        // onView(withId(R.id.notification_swipe)).perform(swipeDown()).check();
    }

    @Test
    public void acceptNotification(){
        prepareDB();

    }
}
