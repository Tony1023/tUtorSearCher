package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.*;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

import java.io.IOException;

public class NotificationWorker extends Worker {

    NotificationHelper helper;

    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        helper = new NotificationHelper(getApplicationContext());
        while(true) {
            Integer response = null;
            //Launch the notification

            try {
                SystemClock.sleep(2000);
                getAndSendNotification();
                SystemClock.sleep(2000);
            } catch (Exception e) {
                Log.e("WORKER",e.getMessage());
            }
        }
            //TODO Infinite Loop?


        // Indicate whether the task finished successfully with the Result
        //return Result.failure();
    }

    public Result getAndSendNotification() {
        try {
            Integer response = WebServiceRepository.getInstance(getApplicationContext()).pollNotifications().execute().body();
            int newNumber = response.intValue();
            if (newNumber > 0) {
                helper.createNotification("tUtorSearCher", String.format("You have %d new messages", newNumber));
                return Result.success();
            }
        }catch(Exception e){
            Log.e("WORKER", e.getMessage());
        }
        return Result.failure();
    }
}
