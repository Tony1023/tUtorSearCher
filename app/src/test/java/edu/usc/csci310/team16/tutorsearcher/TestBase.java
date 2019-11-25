package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

public class TestBase {
    protected MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.play();
        RemoteServerDAO.setUrl(server.getUrl("/").toString());
        RemoteServerDAO.setToken("");
        RemoteServerDAO.setId(-1);
    }

    @After
    public void tearDown() {

    }
}
