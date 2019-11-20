package edu.usc.csci310.team16.tutorsearcher;

import edu.usc.csci310.team16.tutorsearcher.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
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
    public void checkNotifications(){

    }


    @Test
    public void getId() {
    }

    @Test
    public void setId() {
    }

    @Test
    public void getReceiverId() {
    }

    @Test
    public void setReceiverId() {
    }

    @Test
    public void getRequestId() {
    }

    @Test
    public void setRequestId() {
    }

    @Test
    public void getSenderId() {
    }

    @Test
    public void setSenderId() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void setType() {
    }

    @Test
    public void getMsg() {
    }

    @Test
    public void getSenderName() {
    }

    @Test
    public void setSenderName() {
    }

    @Test
    public void setStatus() {
    }

    @Test
    public void getStatus() {
    }

    @Test
    public void setMsg() {
    }

    @Test
    public void getOverlap() {
    }

    @Test
    public void setOverlap() {
    }
}