package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.*;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

import java.io.IOException;

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

            Integer response = null;
            //Launch the notification
            NotificationHelper helper = new NotificationHelper(getApplicationContext());
            try {
                response = WebServiceRepository.getInstance(getApplicationContext()).pollNotifications().execute().body();
                newNumber = response.intValue();
                helper.createNotification("tUtorSearCher", String.format("You have %d new messages",newNumber));
            } catch (IOException e) {
                e.printStackTrace();
            }




            //TODO Infinite Loop?


        // Indicate whether the task finished successfully with the Result
        return Result.failure();
    }
}
