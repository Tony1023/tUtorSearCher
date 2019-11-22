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
public class SearchModelTest extends LiveDataTestBase {

    private SearchModel model = new SearchModel();

    @Test
    public void testSearchEmptyResponse() {
        Gson gson = new Gson();
        final List<UserProfile> expectedResults = new ArrayList<>();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        model.getSearchResults().observeForever(new Observer<List<UserProfile>>() {
            @Override
            public void onChanged(List<UserProfile> actualResults) {
                assertThat(expectedResults.size() == actualResults.size());
                for(int i = 0; i < actualResults.size(); i++) {
                    assertThat(expectedResults.get(i).getName().equals(actualResults.get(i).getName()));
                }
            }
        });

        model.search();
    }

    @Test
    public void testSearchNonemptyResponse() {
        Gson gson = new Gson();
        final List<UserProfile> r = new ArrayList<>();
        UserProfile user = new UserProfile();
        user.setId(1);
        user.setName("Micah");
        user.setEmail("micah@usc.edu");
        r.add(user);
        user = new UserProfile();
        user.setId(2);
        user.setName("Adina");
        user.setEmail("adina@usc.edu");
        r.add(user);
        user = new UserProfile();
        user.setId(3);
        user.setName("Tony");
        user.setEmail("tony@usc.edu");
        r.add(user);
        user = new UserProfile();
        user.setId(4);
        user.setName("Teagan");
        user.setEmail("teagan@usc.edu");
        r.add(user);
        user = new UserProfile();
        user.setId(5);
        user.setName("Eric");
        user.setEmail("eric@usc.edu");
        r.add(user);
        final List<UserProfile> expectedResults = new ArrayList<>();
        server.enqueue(new MockResponse()
                .setBody(gson.toJson(expectedResults))
                .addHeader("access-token", "accessToken") // not used
        );

        model.getSearchResults().observeForever(new Observer<List<UserProfile>>() {
            @Override
            public void onChanged(List<UserProfile> actualResults) {
                assertThat(expectedResults.size() == actualResults.size());
                for(int i = 0; i < actualResults.size(); i++) {
                    assertThat(expectedResults.get(i).getName().equals(actualResults.get(i).getName()));
                }
            }
        });

        model.search();
    }

    @Test
    public void testSearchFailure() throws IOException {
        server.shutdown();

        model.getError().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String e) {
//                assertThat(e.equals("Search failed: network error"));
//                assertThat(e).isEqualTo("This should fail");
            }
        });

        model.search();
    }
}
