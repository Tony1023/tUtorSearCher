package edu.usc.csci310.team16.tutorsearcher.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.usc.csci310.team16.tutorsearcher.Notification;

import java.util.List;

public class RoomDBRepository {
    private static volatile RoomDBRepository INSTANCE;
    private NotificationDAO notificationDAO;
    private Application application;

    MutableLiveData<List<Notification>> mAllPosts = new MutableLiveData<>();
    private static MutableLiveData<Boolean> notificationUpdated = new MutableLiveData<>();

    private RoomDBRepository(Application application){
        DataDatabase db = DataDatabase.getDatabase(application);
        notificationDAO = db.notificationDAO();
    }

    public static RoomDBRepository getInstance(Application app) {
        if(INSTANCE == null){
            synchronized (RoomDBRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RoomDBRepository(app);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Notification>> getAllPosts() {
        Log.e("AAHHHHH","AHHHHHH");
        return notificationDAO.loadAll();
    }

    public MutableLiveData<Boolean> getNotificationsChanged(){
        return notificationUpdated;
    }

    public void insertPosts (List<Notification> resultModel) {
        new insertAsyncTask(notificationDAO).execute(resultModel);
    }

    public void changeStatus (final Notification notification){
        new insertAsyncTask(notificationDAO).execute(new Runnable() {
            @Override
            public void run() {
                notificationDAO.modify(notification);
            }
        });
    }

    private static class insertAsyncTask extends AsyncTask<List<Notification>, Void, Void> {

        private NotificationDAO mAsyncTaskDao;

        insertAsyncTask(NotificationDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Notification>... params) {
            mAsyncTaskDao.insertAll(params[0]);
            notificationUpdated.postValue(Boolean.TRUE);
            return null;
        }
    }
}
