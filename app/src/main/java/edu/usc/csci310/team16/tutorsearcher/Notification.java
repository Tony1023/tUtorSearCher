package edu.usc.csci310.team16.tutorsearcher;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * Data class for storing notifications
 *
 * [
 *   {
 *     "msg": "Empty Message",
 *     "receiver_id": 2,
 *     "id": 1,
 *     "type": 0,
 *     "request_id": 1,
 *     "sender_id": 1
 *   }
 * ]
 */
@Entity(tableName = "data_database.notifications")
public class Notification {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @NonNull
    private String id;

    @ColumnInfo(name="receiver_id")
    @SerializedName("receiver_id")
    private String receiver_id;

    @ColumnInfo(name="request_id")
    @SerializedName("request_id")
    private String request_id;

    @ColumnInfo(name="sender_id")
    @SerializedName("receiver_id")
    private String sender_id;

    @SerializedName("type")
    @ColumnInfo(name="type")
    private String mType = "";

    @SerializedName("msg")
    @ColumnInfo(name = "msg")
    private String msg = "";

    public Notification(@NonNull String id, String type, String msg){
        mType= type;
        this.id = id;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return mType;
    }

    public String getMsg() {
        return msg;
    }

}
