package edu.usc.csci310.team16.tutorsearcher.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import edu.usc.csci310.team16.tutorsearcher.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {

    @Query("SELECT * FROM `data_database.notifications`")
    LiveData<List<Notification>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Notification> items);
}
