package edu.usc.csci310.team16.tutorsearcher;

import android.util.Log;
import androidx.annotation.IntegerRes;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.*;
import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

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
    UiDevice device;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        loginRobot = new LoginRobot();
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        gson = new Gson();

        server.setDispatcher(new Dispatcher() {
            int times = 1;
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                Log.i("NOTIFICATION",request.getPath());
                if (request.getPath().endsWith("ount")){

                    return new MockResponse().setBody(gson.toJson(0));
                } else if(request.getPath().endsWith("signin")){
                    UserProfile user = new UserProfile();
                    user.setId(1);
                    user.setName("Tony");
                    user.setEmail("tony@usc.edu");
                    gson = new Gson();
                    return (new MockResponse()
                            .setBody(gson.toJson(user))
                    );
                }else{
                    Notification notification1 = new Notification("uuid1",1,2,3,"me",0,"0111000111001101101010","msg",0);
                    Notification notification2 = new Notification("uuid2",1,2,3,"me",0,"0111000111001101101010","msg",0);
                    final List<Notification> notifications1 = new ArrayList<>();
                    notifications1.add(notification1);

                    final List<Notification> notifications2 = new ArrayList<>();
                    notifications2.add(notification1);
                    notifications2.add(notification2);
                    if (times == 1) {
                        times++;
                        return new MockResponse().setBody(gson.toJson(notifications1));
                    }else{
                        return new MockResponse().setBody(gson.toJson(notifications2));
                    }
                }
            }
        });

        loginRobot.login("tony@usc.edu", "password");

    }

    @Test
    public void fragmentExists(){

        server.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().endsWith("ount")){
                    return new MockResponse().setBody(gson.toJson(0));
                }else if(request.getPath().endsWith("signin")){
                    UserProfile user = new UserProfile();
                    user.setId(1);
                    user.setName("Tony");
                    user.setEmail("tony@usc.edu");
                    gson = new Gson();
                    return (new MockResponse()
                            .setBody(gson.toJson(user))
                    );
                }else{
                    return new MockResponse().setBody(gson.toJson(new ArrayList<>()));
                }
            }
        });

        onView(withId(R.id.navigation_notifications)).perform(click());

        getView().check(matches(isDisplayed())).check(matches(hasChildCount(0)));
    }

    @Test
    public void getNotifications() throws InterruptedException {

        onView(withId(R.id.navigation_notifications)).perform(click());

        getView().check(withItemCount(1));

        //assertThat(getView()).isNotNull();
    }

    @Test
    public void updateNotifications() throws InterruptedException {

        onView(withId(R.id.navigation_notifications)).perform(click());

        getView().perform(swipeDown());
        synchronized (device) {
            device.wait(3000);
        }
        //getView().check(withItemCount(1));
    }

    @Test
    public void testNotificationPopup() throws UiObjectNotFoundException {

        onView(withId(R.id.navigation_notifications)).perform(click());

        device.openNotification();
        device.wait(Until.hasObject(By.textStartsWith("tUtorSearCher")),50000);
        List<UiObject2> objects = device.findObjects(By.textStartsWith("tUtorSearCher"));
        //assertThat(objects).hasSize(1);
    }


    public ViewInteraction getView(){
        return onView(withId(R.id.notifications_view));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        device.pressBack();
    }
}
