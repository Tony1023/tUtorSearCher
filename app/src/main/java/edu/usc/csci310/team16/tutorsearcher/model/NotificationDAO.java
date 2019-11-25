package edu.usc.csci310.team16.tutorsearcher.model;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import edu.usc.csci310.team16.tutorsearcher.Notification;

import java.util.List;

@Dao
public abstract class NotificationDAO {

    @Transaction
    public void insertAll(List<Notification> items){
        deleteAll();
        addAll(items);
    }

    @Query("SELECT * FROM `data_database.notifications` where `status` != 'DISMISSED'")
    public abstract LiveData<List<Notification>> loadAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void addAll(List<Notification> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void modify(Notification items);

    @Query("DELETE FROM `data_database.notifications`")
    public abstract void deleteAll();
}
