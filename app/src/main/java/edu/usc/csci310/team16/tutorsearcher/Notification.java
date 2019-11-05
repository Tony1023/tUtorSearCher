package edu.usc.csci310.team16.tutorsearcher;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * Data class for storing notifications
 */
@Entity(tableName = "data_database.notifications")
public class Notification {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @NonNull
    private String id;

    @SerializedName("type")
    @ColumnInfo(name="type")
    private String mType = "";

    @SerializedName("msg")
    @ColumnInfo(name = "msg")
    private String mMsg = "";

    public Notification(@NonNull String id, String type, String msg){
        mType= type;
        this.id = id;
        mMsg = msg;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return mType;
    }

    public String getMsg() {
        return mMsg;
    }

}
