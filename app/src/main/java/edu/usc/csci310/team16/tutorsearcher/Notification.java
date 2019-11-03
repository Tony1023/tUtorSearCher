package edu.usc.csci310.team16.tutorsearcher;

/**
 * Data class for storing notifications
 */
public class Notification {
    private String mId= "";
    private String mType = "";
    private String mMsg = "";

    public Notification(String id, String type, String msg){
        mType= type;
        mId = id;
        mMsg = msg;
    }

    public String getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public String getMsg() {
        return mMsg;
    }

}