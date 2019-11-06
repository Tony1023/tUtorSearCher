package edu.usc.csci310.team16.tutorsearcher;

// importing required libraries

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;


//Tutor Profile -> go to from TutorList or Search Results
public class TutorProfileFragment extends Fragment {

    private UserProfile user;
    private SearchModel searchModel;
    private TextView time_toggle[][];

    RatingBar rt;

    public TutorProfileFragment() {}

    public TutorProfileFragment(Tutor tutor){
        if(tutor != null && tutor.getProfile() != null) {
            user = tutor.getProfile();
        }
        else{
            user = new UserProfile();
            user.setName("User null");
        }

    }

    public TutorProfileFragment(UserProfile user){
        //this.user = user;

        if(user!= null) {
            this.user = user;
        }
        else{
            this.user = new UserProfile();
        }
    }

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        //availability variables
        searchModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);
        time_toggle = new TextView[searchModel.getDays().size()][searchModel.getBlocks().size()];

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tutorprofile_fragment, container, false);
        rt = (RatingBar) v.findViewById(R.id.simpleRatingBar);


        Button submitButton = (Button)v.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("tutorprofilefragment", "in onClick");
                RemoteServerDAO.getDao().rateTutor(user.getId(), UserProfile.getCurrentUser().getId(), (double)rt.getRating()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        Log.d("tutorprofilefragment", "submit rating succeeded");
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.e("tutor profile fragment", "submit rating failed");
                    }
                });
            }
        });


        //finding the specific RatingBar with its unique ID
        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();

        //Use for changing the color of RatingBar
        stars.getDrawable(2).setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);


        //SHOW PROFILE ATTRIBUTES ON PROFILE LAYOUT
        //put name on page
        TextView name = (TextView)v.findViewById(R.id.name);
        name.setText(user.getName());

        //put grade on page
        TextView grade = (TextView)v.findViewById(R.id.grade);
        grade.setText(user.getGrade());

        //AVAILABILITY
        GridLayout timeSelectGrid = (GridLayout) v.findViewById(R.id.availability_grid);
        timeSelectGrid.setColumnCount(time_toggle.length + 1);
        timeSelectGrid.setRowCount(time_toggle[0].length + 1);
        timeSelectGrid.setOrientation(GridLayout.VERTICAL);

        TextView t = new TextView(v.getContext());
        t.setText("");
        timeSelectGrid.addView(t);

        for(int j = 0; j < time_toggle[0].length; j++){
            t = new TextView(v.getContext());
            t.setText(searchModel.getBlocks().get(j));
            timeSelectGrid.addView(t);
        }

        for(int i = 0; i < time_toggle.length; i++){
            t = new TextView(v.getContext());
            t.setWidth(100);
            t.setText(searchModel.getDays().get(i));
            t.setGravity(Gravity.CENTER);
            timeSelectGrid.addView(t);


            //t.setBackgroundColor();
            //i*time_toggle[0].length + j

            for(int j = 0; j < time_toggle[0].length; j++){

                time_toggle[i][j] = new TextView(v.getContext());

                //set green if available during that time
                if(searchModel.getAvailability().contains(i*time_toggle[0].length + j)) {

                    time_toggle[i][j].setBackgroundColor(0xff00ff00);
                }

                //set red otherwise

                else {
                    time_toggle[i][j].setBackgroundColor(0xFFFF0000);
                }
//                time_toggle[i][j] = new MaterialCheckBox(v.getContext());
                timeSelectGrid.addView(time_toggle[i][j]);
            }

            //END CODE STOLEN FROM MICAH
        }

        //TUTOR:

        //put bio on page
        TextView bio = (TextView)v.findViewById((R.id.bio));
        bio.setText(user.getBio());

        //put rating on page if not -1 (initial value)
        if(user.getRating() != -1) {
            TextView rating = (TextView)v.findViewById(R.id.rating);
            rating.setText(Double.toString(user.getRating()));
            rt.setRating((float)user.getRating());

        }

        //put list of courses taken on page
        TextView coursesTaken = (TextView)v.findViewById(R.id.courses_taken);
        String coursesTakenString = "";
        List<String> coursesTakenList = user.getCoursesTaken();

        for(String course : coursesTakenList) {
            coursesTakenString += course + ", ";
        }

        if(coursesTakenString.length() != 0)
            coursesTaken.setText(coursesTakenString.substring(0, coursesTakenString.length()-2));

        //put list of courses tutoring on page
        TextView coursesTutoring = (TextView)v.findViewById(R.id.courses_tutoring);
        String coursesTutoringString = "";
        List<String> coursesTutoringList = user.getTutorClasses();

        for(String course : coursesTutoringList) {
            coursesTutoringString += course + ", ";
        }

        if(coursesTutoringString.length() != 0)
            coursesTutoring.setText(coursesTutoringString.substring(0, coursesTutoringString.length()-2));


        return v;


        //take the results of those text boxes and change the UserProfile data members
        //once the user clicks another button at the bottom of that page
        //finish() that view and go back to the Profile view

        //ignore the MutableLiveData for now

    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
