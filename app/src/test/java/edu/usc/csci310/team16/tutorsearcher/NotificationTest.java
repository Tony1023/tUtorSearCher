package edu.usc.csci310.team16.tutorsearcher;

import edu.usc.csci310.team16.tutorsearcher.Notification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotificationTest {

    Notification nullNotification = new Notification();
    Notification fullNotification = new Notification();

    @Before
    public void setUp() throws Exception {
        nullNotification = new Notification();
        fullNotification = new Notification("id", 0, 0, 0, "Eric",
                0, "1010101", "This is a test", 0);
    }

    @After
    public void tearDown() throws Exception {
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