package edu.usc.csci310.team16.tutorsearcher;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;

public class LiveDataTestBase {

    protected MockWebServer server = new MockWebServer();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUpMockServer() throws IOException {
        server = new MockWebServer();
        server.play();
        RemoteServerDAO.setUrl(server.getUrl("/").toString());
        RemoteServerDAO.setToken("");
        RemoteServerDAO.setId(-1);
    }

}
