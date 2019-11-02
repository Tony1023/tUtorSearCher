package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

//changed from extends Fragment
public class ProfileFragment extends Fragment {

    private UserProfile user;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        user = UserProfile.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        //put name on page
        TextView name = (TextView)v.findViewById(R.id.name);
        name.setText(user.getName());

        //put grade on page
        TextView grade = (TextView)v.findViewById(R.id.grade);
        grade.setText(user.getGrade());

        //TUTOR:
        //put rating on page if not -1 (initial value)
        if(user.getRating() != -1) {
            TextView rating = (TextView)v.findViewById(R.id.rating);
            rating.setText(Double.toString(user.getRating()));
        }

        //put list of courses taken on page
        TextView coursesTaken = (TextView)v.findViewById(R.id.courses_taken);
        String coursesTakenString = "";
        List<String> coursesTakenList = user.getCoursesTaken();

        for(String course : coursesTakenList) {
            coursesTakenString += course + "\n";
        }
        coursesTaken.setText(coursesTakenString);

        //put list of courses tutoring on page
        TextView coursesTutoring = (TextView)v.findViewById(R.id.courses_tutoring);
        String coursesTutoringString = "";
        List<String> coursesTutoringList = user.getTutorClasses();

        for(String course : coursesTutoringList) {
            coursesTutoringString += course + "\n";
        }
        coursesTutoring.setText(coursesTutoringString);


        return v;

        //take UserProfile information and put it on labels in the view

        //edit button to create a new view with a bunch of text boxes in it
        //take the results of those text boxes and change the UserProfile data members
            //once the user clicks another button at the bottom of that page
            //finish() that view and go back to the Profile view

        //ignore the MutableLiveData for now

    }

}
