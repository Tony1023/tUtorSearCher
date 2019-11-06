package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;

public class EditProfileFragment extends Fragment {

    private UserProfile user;
    private SearchModel searchModel;
    private MaterialCheckBox time_toggle[][];

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        //get data from the singleton
        user = UserProfile.getCurrentUser();

        searchModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);
        time_toggle = new MaterialCheckBox[searchModel.getDays().size()][searchModel.getBlocks().size()];

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        //get values from all of the form elements on the page on click of button
        //borrow code from profileFragment for button onclick listener

        //AVAILABILITY CODE STOLEN FROM MICAH
        GridLayout timeSelectGrid = (GridLayout) v.findViewById(R.id.time_select_grid);
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

            for(int j = 0; j < time_toggle[0].length; j++){
                time_toggle[i][j] = new MaterialCheckBox(v.getContext());
                timeSelectGrid.addView(time_toggle[i][j]);
            }

            //END CODE STOLEN FROM MICAH
        }


        Button editButton = (Button)v.findViewById(R.id.submit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //get all the values from the text fields/boxes

                //name
                final EditText nameText = (EditText) getActivity().findViewById(R.id.name);
                String name = nameText.getText().toString();

                //grade
                final Spinner gradeSpinner = (Spinner)getActivity().findViewById(R.id.grade_spinner);
                String grade = gradeSpinner.getSelectedItem().toString();

                //bio
                final EditText bioText = (EditText) getActivity().findViewById(R.id.bio);
                String bio = bioText.getText().toString();

                //checkboxes ones
                String[] coursesTakenArray = {"cs103_taken", "cs104_taken",
                         "cs170_taken", "cs201_taken", "cs270_taken", "cs310_taken",
                        "cs350_taken", "cs356_taken", "cs360_taken"};

                String[] coursesTutoringArray = {"cs103_tutoring", "cs104_tutoring",
                         "cs170_tutoring", "cs201_tutoring", "cs270_tutoring",
                        "cs310_tutoring", "cs350_tutoring", "cs356_tutoring", "cs360_tutoring"};

                String[] courseCodes = {"CSCI103", "CSCI104", "CSCI170", "CSCI201", "CSCI270", "CSCI310", "CSCI350",
                        "CSCI356", "CSCI360"};

                ArrayList<String> coursesTaken = new ArrayList<String>();
                ArrayList<String> coursesTutoring = new ArrayList<String>();

                //loop through all checkboxes to see if they are checked
                for(int i = 0; i < coursesTakenArray.length; i++) {

                    //courses taken
                    final CheckBox takenCheckbox = (CheckBox)getActivity().findViewById(getResources()
                            .getIdentifier(coursesTakenArray[i], "id", getActivity().getPackageName()));
                    if (takenCheckbox.isChecked()) {
                        coursesTaken.add(courseCodes[i]);
                    }

                    //courses tutoring
                    final CheckBox tutoringCheckbox = (CheckBox)getActivity().findViewById(getResources()
                            .getIdentifier(coursesTutoringArray[i], "id", getActivity().getPackageName()));

                    if(tutoringCheckbox.isChecked()) {
                        coursesTutoring.add(courseCodes[i]);
                    }
                }



                /*
                    cs103, cs104, cs109, cs170, cs201, cs270, cs350, cs356, cs360
                 */

                //put them in the UserProfile fields
                user.setName(name);
                user.setGrade(grade);
                user.setBio(bio);
                user.setCoursesTaken(coursesTaken);
                user.setTutorClasses(coursesTutoring);


                //transition back to profile fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ((MainActivity)getActivity()).getProfile())
                        .commit();
            }
        });

        return v;
    }
}
