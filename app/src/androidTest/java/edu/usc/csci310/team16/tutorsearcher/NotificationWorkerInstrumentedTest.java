package edu.usc.csci310.team16.tutorsearcher;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.testing.*;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.TestDriver;
import androidx.work.testing.WorkManagerTestInitHelper;
import com.squareup.okhttp.mockwebserver.MockResponse;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class NotificationWorkerInstrumentedTest extends LiveDataTestBase {
    ActivityTestRule mainRule;
    OneTimeWorkRequest workerRequest;
    WorkManager manager;
    //TestDriver driver;

    @Before
    public void setup(){
        mainRule = new ActivityTestRule<>(MainActivity.class, true, false);

        workerRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();
        manager = WorkManager.getInstance(getApplicationContext());

        //driver = WorkManagerTestInitHelper.getTestDriver(getApplicationContext());
    }

    @Test
    public void doWorkEmpty(){
        server.enqueue(new MockResponse().setBody("0"));
        manager.enqueue(workerRequest);

        try {
            manager.enqueue(workerRequest).getResult().get();
            WorkInfo workInfo = manager.getWorkInfoById(workerRequest.getId()).get();

            assertThat(workInfo.getState()).isEqualTo(WorkInfo.State.SUCCEEDED);

        } catch (ExecutionException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Assert.fail();
            e.printStackTrace();
        }

    }

    @Test
    public void doWorkMany(){

    }
}
