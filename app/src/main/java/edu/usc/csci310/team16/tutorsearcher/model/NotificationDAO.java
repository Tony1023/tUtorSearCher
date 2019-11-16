package edu.usc.csci310.team16.tutorsearcher.model;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import edu.usc.csci310.team16.tutorsearcher.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {

    @Query("SELECT * FROM `data_database.notifications` where `status` != 'DISMISSED'")
    LiveData<List<Notification>> loadAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Notification> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void modify(Notification items);

    @Query("DELETE FROM `data_database.notifications`")
    void deleteAll();
}
