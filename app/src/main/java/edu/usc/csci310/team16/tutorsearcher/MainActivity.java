package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    private MainModel mainModel;
    private FragmentContainer fragmentContainer;
    private ProfileFragment profile;
    private SearchFragment search;
    private NotificationFragment notification;
    private RatingFragment rating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profile = new ProfileFragment();
        search = new SearchFragment();
        notification = new NotificationFragment();
        rating = new RatingFragment();

        setContentView(R.layout.activity_main);
        mainModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainModel.class);
        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profile)
                .commit();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == mainModel.getPage()) {
            return false;
        } else {
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

    public RatingFragment getRating() {
        return rating;
    }
}
