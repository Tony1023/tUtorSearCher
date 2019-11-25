package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.squareup.okhttp.mockwebserver.MockResponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class TutorListModelTest extends LiveDataTestBase {

    TutorFragment tf;
    private TutorListModel model = new TutorListModel(tf);

    @Test
    public void testTutorEmptyResponse() {
        Gson gson = new Gson();
        final List<Tutor> expectedResults = new ArrayList<>();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        model.getTutors().observeForever(new Observer<List<Tutor>>() {
            @Override
            public void onChanged(List<Tutor> actualResults) {
                assertThat(expectedResults.size() == actualResults.size());
                for(int i = 0; i < actualResults.size(); i++) {
                    assertThat(expectedResults.get(i).getProfile().getName().equals(actualResults.get(i).getProfile().getName()));
                }
            }
        });
    }

    @Test
    public void testTutorListNonemptyResponse() {
        Gson gson = new Gson();
        final List<UserProfile> r = new ArrayList<>();
        UserProfile user = new UserProfile();

        user = new UserProfile();
        user.setId(5);
        user.setName("Eric");
        user.setEmail("eric@usc.edu");
        r.add(user);

        final List<Tutor> expectedResults = new ArrayList<Tutor>();
        expectedResults.add(new Tutor("5", "", user));

        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
        );

        model.getTutors().observeForever(new Observer<List<Tutor>>() {
            @Override
            public void onChanged(List<Tutor> actualResults) {
                assertThat(expectedResults.size() == actualResults.size());
                for(int i = 0; i < actualResults.size(); i++) {
                    assertThat(expectedResults.get(i).getProfile().getName().equals(actualResults.get(i).getProfile().getName()));
                }
            }
        });
    }
}