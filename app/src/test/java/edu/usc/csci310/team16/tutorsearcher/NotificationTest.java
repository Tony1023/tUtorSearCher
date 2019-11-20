package edu.usc.csci310.team16.tutorsearcher;

import android.os.Build;
import edu.usc.csci310.team16.tutorsearcher.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.google.common.truth.Truth.assertThat;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class NotificationTest extends TestBase {

    Notification nullNotification;
    Notification fullNotification;

    @Override
    public void setUp() throws Exception {
        nullNotification = new Notification();
        fullNotification = new Notification("id", 0, 0, 0, "Eric",
                0, "1010101", "This is a test", 0);
    }


    @Test
    public void getId() {
        assertThat(nullNotification.getId()).isEqualTo(null);

        assertThat(fullNotification.getId()).isEqualTo("id");
    }

    @Test
    public void setId() {
        nullNotification.setId("a");
        assertThat(nullNotification.getId()).isEqualTo("a");

        nullNotification.setId(null);
        assertThat(fullNotification.getId()).isEqualTo("id");

        fullNotification.setId("25");
        assertThat(fullNotification.getId()).isEqualTo("25");
    }

    @Test
    public void getReceiverId() {
        assertThat(nullNotification.getReceiverId()).isEqualTo(0);

        assertThat(fullNotification.getReceiverId()).isEqualTo(0);
    }

    @Test
    public void setReceiverId() {
        nullNotification.setReceiverId(10);

        assertThat(nullNotification.getReceiverId()).isEqualTo(10);
    }

//    @Test
//    public void getRequestId() {
//    }
//
//    @Test
//    public void setRequestId() {
//    }
//
//    @Test
//    public void getSenderId() {
//    }
//
//    @Test
//    public void setSenderId() {
//    }
//
//    @Test
//    public void getType() {
//    }
//
//    @Test
//    public void setType() {
//    }
//
//    @Test
//    public void getMsg() {
//    }
//
//    @Test
//    public void getSenderName() {
//    }
//
//    @Test
//    public void setSenderName() {
//    }
//
//    @Test
//    public void setStatus() {
//    }
//
//    @Test
//    public void getStatus() {
//    }
//
//    @Test
//    public void setMsg() {
//    }
//
//    @Test
//    public void getOverlap() {
//    }
//
//    @Test
//    public void setOverlap() {
//    }
}