package edu.usc.csci310.team16.tutorsearcher;

import androidx.annotation.NonNull;
import androidx.room.*;
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
    private int receiverId;

    @ColumnInfo(name="transaction_id")
    @SerializedName("request_id")
    private int requestId;

    @ColumnInfo(name="sender_id")
    @SerializedName("receiver_id")
    private int senderId;

    @ColumnInfo(name="sender_name")
    @SerializedName("sender_name")
    private String senderName;

    @SerializedName("type")
    @ColumnInfo(name="type")
    private String mType = "";

    @SerializedName("overlap")
    @ColumnInfo(name = "overlap")
    private String overlap;

    @SerializedName("msg")
    @ColumnInfo(name = "msg")
    private String msg = "";

    @ColumnInfo(name="status")
    private String status="PENDING";

    public Notification(@NonNull String id, int receiverId, int requestId, int senderId, String senderName, String mType, String overlap, String msg, String status) {
        this.id = id;
        this.receiverId = receiverId;
        this.requestId = requestId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.mType = mType;
        this.overlap = overlap;
        this.msg = msg;
        this.status = status;
    }

    public Notification(){}

    @Ignore
    public Notification(@NonNull String id, String type, String msg){
        mType= type;
        this.id = id;
        this.msg = msg;

    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getMsg() {
        return msg;
    }

    public String getSenderName(){
        return senderName;
    }

    public void setSenderName(String name){
        senderName = name;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOverlap() {
        return overlap;
    }

    public void setOverlap(String overlap) {
        this.overlap = overlap;
    }
}
