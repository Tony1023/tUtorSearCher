package edu.usc.csci310.team16.tutorsearcher;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

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
}
