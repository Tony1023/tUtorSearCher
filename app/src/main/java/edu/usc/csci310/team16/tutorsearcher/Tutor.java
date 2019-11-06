package edu.usc.csci310.team16.tutorsearcher;

/**
 * Data class for storing notifications
 */
public class Tutor {
    private String mId= "";
    private String mType = "";
    private String mMsg = "";
    private UserProfile profile;

    public Tutor(String id, String type, String msg){
        mType= type;
        mId = id;
        mMsg = msg;
        //profile = p;
    }

    public Tutor(String id, String type, UserProfile p){
        mType= type;
        mId = id;
        mMsg = p.getName();
        profile = p;
    }

    public String getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public String getMsg() { return  mMsg; }

    public UserProfile getProfile() {
        return profile;
    }

}