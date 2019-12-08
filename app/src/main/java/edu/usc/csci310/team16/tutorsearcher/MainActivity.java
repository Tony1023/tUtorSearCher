package edu.usc.csci310.team16.tutorsearcher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, TimePickerFragment.OnAvailabilityUpdatedListener,
                    NotificationListAdapter.OnCardClickedListener{

    private MainModel mainModel;
    private FragmentContainer fragmentContainer;
    private ProfileFragment profile;
    private EditProfileFragment editProfile;
    private SearchFragment search;
    private NotificationFragment notification;
    private TutorFragment rating;

    public EditProfileFragment getEditProfile() {
        return editProfile;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profile = new ProfileFragment();
        editProfile = new EditProfileFragment();
        search = new SearchFragment();
        notification = new NotificationFragment();
        rating = new TutorFragment();

        setContentView(R.layout.activity_main);
        mainModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainModel.class);
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profile)
                .commit();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment view;
        switch (item.getItemId()) {
            case R.id.navigation_search:
                view = search; break;
            case R.id.navigation_notifications:
                view = notification; break;
            case R.id.navigation_tutors:
                view = rating; break;
            case R.id.navigation_profile:
            default:
                view = profile;
        }
        mainModel.setPage(item.getItemId());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, view)
                .commit();
        return true;

    }

    private void showNotification(String message){
        Intent intent=new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data", message);
        intent.putExtra("KEY", "YOUR VAL");
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        android.app.Notification.Builder builder=new android.app.Notification.Builder(this,"My ID")
                .setAutoCancel(true)
                .setContentTitle("Registry")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentIntent(pendingIntent);
        NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    public ProfileFragment getProfile() {
        return profile;
    }

    public SearchFragment getSearch() {
        return search;
    }

    public NotificationFragment getNotification() {
        return notification;
    }

    public TutorFragment getRating() {
        return rating;
    }

    public void onCardClicked(int position, String availability){
        TimePickerFragment timePickerFragment = (TimePickerFragment)
                getSupportFragmentManager().findFragmentById(R.id.time);

        if (timePickerFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            timePickerFragment.updateInformation(position,availability);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            TimePickerFragment newFragment = new TimePickerFragment();
            Bundle args = new Bundle();
            args.putInt("position",position);
            args.putString("availability", availability);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment, "picker");
            transaction.addToBackStack("CLOSE_PICKER");

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void onAvailabilityUpdated(int position, String updated) {
        NotificationFragment notificationFragment = (NotificationFragment)
                getSupportFragmentManager().findFragmentById(R.id.notification_fragment);

        if (notificationFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            // notificationFragment.setAvailability(updated);
            getSupportFragmentManager().popBackStackImmediate("fun", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            NotificationFragment newFragment = new NotificationFragment();
            Bundle args = new Bundle();
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment, "notifications");
            transaction.addToBackStack("NOTIFICATION_RETURN");

            // Commit the transaction
            transaction.commit();
        }
    }
}

