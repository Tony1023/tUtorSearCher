package edu.usc.csci310.team16.tutorsearcher.model;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import edu.usc.csci310.team16.tutorsearcher.Notification;

@Database(entities = {Notification.class}, version = 2, exportSchema = false)
public abstract class DataDatabase extends RoomDatabase {
    public abstract NotificationDAO notificationDAO();


    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile DataDatabase INSTANCE;

    static DataDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataDatabase.class, "data_database").build();
                    INSTANCE.clearAllTables();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * Creates the open helper to access the database. Generated class already implements this
     * method.
     * Note that this method is called when the RoomDatabase is initialized.
     *
     * @param config The configuration of the Room database.
     * @return A new SupportSQLiteOpenHelper to be used while connecting to the database.
     */
    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    /**
     * Called when the RoomDatabase is created.
     * <p>
     * This is already implemented by the generated code.
     *
     * @return Creates a new InvalidationTracker.
     */
    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

}
