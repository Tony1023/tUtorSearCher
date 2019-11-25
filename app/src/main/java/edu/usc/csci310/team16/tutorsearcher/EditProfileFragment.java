package edu.usc.csci310.team16.tutorsearcher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private UserProfile user;
    private MaterialCheckBox time_toggle[][]; //for availability

    //added instance fields to store the courses taken and tutoring courses
//    private MaterialCheckBox coursesTakenBoxes[][];
//    private MaterialCheckBox coursesTutoringBoxes[][];
//
//    public MaterialCheckBox[][] getCoursesTakenBoxes() {
//        return coursesTakenBoxes;
//    }
//
//    public MaterialCheckBox[][] getCoursesTutoringBoxes() {
//        return coursesTutoringBoxes;
//    }


    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        //get data from the singleton
        user = UserProfile.getCurrentUser();
        time_toggle = new MaterialCheckBox[SearchModel.getDays().size()][SearchModel.getBlocks().size()];

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        final String[] coursesTakenArray = {"cs103_taken", "cs104_taken",
                "cs170_taken", "cs201_taken", "cs270_taken", "cs310_taken",
                "cs350_taken", "cs356_taken", "cs360_taken"};

        final String[] coursesTutoringArray = {"cs103_tutoring", "cs104_tutoring",
                "cs170_tutoring", "cs201_tutoring", "cs270_tutoring",
                "cs310_tutoring", "cs350_tutoring", "cs356_tutoring", "cs360_tutoring"};

        //if UserProfile has filled values, prefill the corresponding fields of this form
        //name
        EditText name = (EditText) v.findViewById(R.id.name);
        name.setText(user.getName(), TextView.BufferType.EDITABLE);

        //grade
        Spinner grade = (Spinner) v.findViewById(R.id.grade_spinner);
        grade.setSelection(((ArrayAdapter)grade.getAdapter()).getPosition(user.getGrade()));

        //bio
        EditText bio = (EditText) v.findViewById(R.id.bio);
        bio.setText(user.getBio(), TextView.BufferType.EDITABLE);

        //availability dealt with below

        //courses taken and courses tutoring
        for(int i = 0; i < coursesTakenArray.length; i++) {

            //courses taken
            final CheckBox takenCheckbox = (CheckBox)v.findViewById(getResources()
                    .getIdentifier(coursesTakenArray[i], "id", getActivity().getPackageName()));

            String code = (String)takenCheckbox.getTag();
            code =  "CSCI"+code.substring(0,3);

            if (user.getCoursesTaken().contains(code)) {
                takenCheckbox.setChecked(true);
            }

            //courses tutoring
            final CheckBox tutoringCheckbox = (CheckBox)v.findViewById(getResources()
                    .getIdentifier(coursesTutoringArray[i], "id", getActivity().getPackageName()));

            String tutoringCode = (String)tutoringCheckbox.getTag();
            tutoringCode = "CSCI"+tutoringCode.substring(0,3);

            if(user.getTutorClasses().contains(code)) {
                tutoringCheckbox.setChecked(true);
            }
        }

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
            t.setText(SearchModel.getBlocks().get(j));
            timeSelectGrid.addView(t);
        }

        for(int i = 0; i < time_toggle.length; i++){
            t = new TextView(v.getContext());
            t.setWidth(100);
            t.setText(SearchModel.getDays().get(i));
            t.setGravity(Gravity.CENTER);
            timeSelectGrid.addView(t);

            for(int j = 0; j < time_toggle[0].length; j++){
                time_toggle[i][j] = new MaterialCheckBox(v.getContext());

                //if this availability is already in the user's list, check box
                if(user.getAvailability().contains(i*time_toggle[0].length + j)) {
                    time_toggle[i][j].setChecked(true);
                }

                time_toggle[i][j].setTag((i*time_toggle[0].length + j)+"box"); //ADDED DYNAMIC ID
                timeSelectGrid.addView(time_toggle[i][j]);

            }
        }
        //END CODE STOLEN FROM MICAH




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


                String[] courseCodes = {"CSCI103", "CSCI104", "CSCI170", "CSCI201", "CSCI270", "CSCI310", "CSCI350",
                        "CSCI356", "CSCI360"};

                String[] courseNumbers = {"103", "104", "170", "201", "270", "310", "350",
                        "356", "360"};

                ArrayList<String> coursesTaken = new ArrayList<String>();
                ArrayList<String> coursesTutoring = new ArrayList<String>();

                //loop through all checkboxes to see if they are checked
                for(int i = 0; i < coursesTakenArray.length; i++) {

                    //courses taken
                    final CheckBox takenCheckbox = (CheckBox)getActivity().findViewById(getResources()
                            .getIdentifier(coursesTakenArray[i], "id", getActivity().getPackageName()));

//                    takenCheckbox.setTag(courseNumbers[i]+"taken"); //ADDED

                    if (takenCheckbox.isChecked()) {
                        coursesTaken.add(courseCodes[i]);
                    }

                    //courses tutoring
                    final CheckBox tutoringCheckbox = (CheckBox)getActivity().findViewById(getResources()
                            .getIdentifier(coursesTutoringArray[i], "id", getActivity().getPackageName()));

//                    tutoringCheckbox.setTag(courseNumbers[i]+"tutoring");

                    if(tutoringCheckbox.isChecked()) {
                        coursesTutoring.add(courseCodes[i]);
                    }
                }

                //determine availability (adapted from Micah's code)
                ArrayList<Integer> availability = new ArrayList<Integer>();
                for(int i = 0; i < time_toggle.length; i++){
                    for(int j = 0; j < time_toggle[0].length; j++){
                        if(time_toggle[i][j].isChecked()){
                            availability.add(i*time_toggle[0].length + j);
                        }
                    }
                }


                //put them in the UserProfile fields
                user.setName(name);
                user.setGrade(grade);
                user.setBio(bio);
                user.setCoursesTaken(coursesTaken);
                user.setTutorClasses(coursesTutoring);
                user.setAvailability(availability);

                //TODO: MAKE SURE THIS IS WORKING
                //Add call to update profile endpoint and test case to check for it
                RemoteServerDAO.getDao().updateProfile(user).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    }
                });

                //transition back to profile fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ((MainActivity)getActivity()).getProfile())
                        .commit();
            }
        });

        return v;
    }
}
