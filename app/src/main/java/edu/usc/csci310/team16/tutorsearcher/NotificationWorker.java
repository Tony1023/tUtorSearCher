package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.*;

public class NotificationWorker extends Worker {
    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        // Check the number of new requests
        String url = getInputData().getString("URL");
        String token = getInputData().getString("TOKEN");

        int newNumber = 0;

        try{
            Integer response = RemoteServerDAO.getDao().getNotificationUpdates().execute().body();
            newNumber = response.intValue();

            //Launch the notification
            NotificationHelper helper = new NotificationHelper(getApplicationContext());
            helper.createNotification("tUtorSearCher", String.format("You have %d new messages",newNumber));

            return Result.success();
        }catch(Exception e){

        }

        // Indicate whether the task finished successfully with the Result
        return Result.failure();
    }
}
