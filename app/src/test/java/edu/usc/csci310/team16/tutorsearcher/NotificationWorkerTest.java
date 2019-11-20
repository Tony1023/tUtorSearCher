package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.os.Build;
import androidx.test.core.app.ApplicationProvider;
import androidx.work.ListenableWorker;
import androidx.work.testing.TestWorkerBuilder;
import com.squareup.okhttp.mockwebserver.MockResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.truth.Truth.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class NotificationWorkerTest extends TestBase {

    Context mContext;
    Executor mExecutor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mContext = ApplicationProvider.getApplicationContext();
        mExecutor = Executors.newSingleThreadExecutor();
    }

    @Test
    public void doWorkEmpty(){
        server.enqueue(new MockResponse().setBody("0"));
        NotificationWorker worker = (NotificationWorker) TestWorkerBuilder.from(mContext, NotificationWorker.class,mExecutor).build();

        ListenableWorker.Result result = worker.doWork();

        assertThat(result).isEqualTo(ListenableWorker.Result.success());
    }

    @Test
    public void doWorkIncorrect(){
        server.enqueue(new MockResponse().setBody("{"));
        NotificationWorker worker = (NotificationWorker) TestWorkerBuilder.from(mContext, NotificationWorker.class,mExecutor).build();

        ListenableWorker.Result result = worker.doWork();

        assertThat(result).isEqualTo(ListenableWorker.Result.failure());
    }

    @Test
    public void doWorkFull(){
        server.enqueue(new MockResponse().setBody("10"));

        NotificationWorker worker = (NotificationWorker) TestWorkerBuilder.from(mContext, NotificationWorker.class,mExecutor).build();

        ListenableWorker.Result result = worker.doWork();

        assertThat(result).isEqualTo(ListenableWorker.Result.success());
    }


}
