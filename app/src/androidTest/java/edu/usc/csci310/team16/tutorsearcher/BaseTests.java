package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;

public abstract class BaseTests {

    protected MockWebServer server;

    // in order to
    @Rule
    public ActivityTestRule<SplashActivity> loginTestRule = new ActivityTestRule<>(SplashActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.play();
        RemoteServerDAO.url = server.getUrl("/").toString();

        Intent intent = new Intent();
        loginTestRule.launchActivity(intent);
        SharedPreferences.Editor editor = loginTestRule.getActivity().getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}
