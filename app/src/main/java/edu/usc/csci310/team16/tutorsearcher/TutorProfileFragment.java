package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class TutorProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);

        //take UserProfile information and put it on labels in the view

        //edit button to create a new view with a bunch of text boxes in it
        //take the results of those text boxes and change the UserProfile data members
            //once the user clicks another button at the bottom of that page
            //finish() that view and go back to the Profile view

        //ignore the MutableLiveData for now

    }
}
