package edu.usc.csci310.team16.tutorsearcher;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.fragment.app.testing.*;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;
import androidx.work.*;
import androidx.work.testing.TestDriver;
import androidx.work.testing.TestWorkerBuilder;
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
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class NotificationWorkerInstrumentedTest extends LiveDataTestBase {
    ActivityTestRule mainRule;
    OneTimeWorkRequest workerRequest;
    WorkManager manager;
    Worker worker;
    NotificationManagerCompat notificationManager;
    UiDevice device = UiDevice.getInstance(getInstrumentation());
    //TestDriver driver;

    @Before
    public void setup(){
        mainRule = new ActivityTestRule<>(MainActivity.class, true, false);

        worker =
                TestWorkerBuilder.from(getApplicationContext(), NotificationWorker.class)
                        .build();

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
        //driver = WorkManagerTestInitHelper.getTestDriver(getApplicationContext());
    }

    @Test
    public void doWorkEmpty(){
        server.enqueue(new MockResponse().setBody("8"));

        ListenableWorker.Result result = worker.doWork();

        assertThat(result).isEqualTo(ListenableWorker.Result.success());



    }

    @Test
    public void doWorkMany(){
        server.enqueue(new MockResponse().setBody("0"));

        ListenableWorker.Result result = worker.doWork();
        assertThat(result).isEqualTo(ListenableWorker.Result.success());



    }
}
