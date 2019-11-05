package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class EditProfileFragment extends Fragment {

    private UserProfile user;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        //get data from the singleton
        user = UserProfile.getCurrentUser();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        //get values from all of the form elements on the page on click of button
        //borrow code from profileFragment for button onclick listener

        final Fragment view = new ProfileFragment();

        Button editButton = (Button)v.findViewById(R.id.submit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //get all the values from the text fields/boxes

                //name
                final EditText nameText = (EditText) v.findViewById(R.id.name);
                String name = nameText.getText().toString();

                //grade
                final Spinner gradeSpinner = (Spinner)v.findViewById(R.id.grade_spinner);
                String grade = gradeSpinner.getSelectedItem().toString();

                //bio
                final EditText bioText = (EditText) v.findViewById(R.id.bio);
                String bio = bioText.getText().toString();

                //checkboxes ones
                String[] coursesTakenArray = {"cs103_taken", "cs104_taken",
                         "cs170_taken", "cs201_taken", "cs270_taken", "cs310_taken",
                        "cs350_taken", "cs356_taken", "cs360_taken"};

                String[] coursesTutoringArray = {"cs103_tutoring", "cs104_tutoring",
                         "cs170_tutoring", "cs201_tutoring", "cs270_tutoring",
                        "cs310_tutoring", "cs350_tutoring", "cs356_tutoring", "cs360_tutoring"};

                String[] courseCodes = {"CS103", "CS104", "CS170", "CS201", "CS270",
                "CS310", "CS350", "CS356", "CS360"};

                ArrayList<String> coursesTaken = new ArrayList<String>();
                ArrayList<String> coursesTutoring = new ArrayList<String>();

                //loop through all checkboxes to see if they are checked
                for(int i = 0; i < coursesTakenArray.length; i++) {

                    //courses taken
                    final CheckBox takenCheckbox = (CheckBox)v.findViewById(getResources()
                            .getIdentifier(coursesTakenArray[i], "id", getActivity().getPackageName()));
                    if (takenCheckbox.isChecked()) {
                        coursesTaken.add(courseCodes[i]);
                    }

                    //courses tutoring
                    final CheckBox tutoringCheckbox = (CheckBox)v.findViewById(getResources()
                            .getIdentifier(coursesTutoringArray[i], "id", getActivity().getPackageName()));

                    if(tutoringCheckbox.isChecked()) {
                        coursesTutoring.add(courseCodes[i]);
                    }
                }

                /*
                    cs103, cs104, cs109, cs170, cs201, cs270, cs350, cs356, cs360
                 */

                

                //put them in the UserProfile fields

                //transition back to profile fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, view)
                        .commit();
            }
        });

        return v;
    }
}
