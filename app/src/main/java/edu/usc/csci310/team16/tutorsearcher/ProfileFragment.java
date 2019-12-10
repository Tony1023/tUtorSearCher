package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.checkbox.MaterialCheckBox;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static androidx.annotation.InspectableProperty.ValueType.GRAVITY;

//changed from extends Fragment
public class ProfileFragment extends Fragment {

    private UserProfile user;
    private TextView time_toggle[][];

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        //get data from the singleton
        user = UserProfile.getCurrentUser();



        //availability variables
        time_toggle = new TextView[SearchModel.getDays().size()][SearchModel.getBlocks().size()];

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.profile_fragment, container, false);

//        final Fragment view = new EditProfileFragment();
//

        //when clicking edit button, transition to edit profile page
        Button editButton = (Button)v.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ((MainActivity)getActivity()).getEditProfile())
                        .commit();
            }
        });

        Button logoutButton = v.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("tutorsearcher", Context.MODE_PRIVATE).edit();
                editor.putInt("userId", -1);
                editor.putString("email", "");
                editor.putString("token", "");
                editor.commit();
                RemoteServerDAO.setId(-1);
                RemoteServerDAO.setToken("");
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        
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

                time_toggle[i][j] = new TextView(v.getContext());
                time_toggle[i][j].setWidth(50);
                time_toggle[i][j].setHeight(40);
                //time_toggle[i][j].setTag((i*time_toggle[0].length + j)+"colorBox");

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(25,0,0,0);
                time_toggle[i][j].setLayoutParams(params);

//                time_toggle[i][j].setGravity(Gravity.CENTER);

                //make green if a disabled slot
                if(user.getDisabledSlots().contains(i*time_toggle[0].length + j)) {
                    time_toggle[i][j].setBackgroundColor(Color.parseColor("#808080"));
                }

                //set green if available during that time
                else if(user.getAvailability().contains(i*time_toggle[0].length + j)) {

                    time_toggle[i][j].setBackgroundColor(Color.parseColor("#90ee90"));
                }

                //set red otherwise
                else {
                    time_toggle[i][j].setBackgroundColor(Color.parseColor("#ff0000"));
                }
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

}