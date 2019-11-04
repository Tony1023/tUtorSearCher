package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

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

        // Spinner element


        /*
            CS 103, CS 104, CS 170,
            CS 270, CS 201, CS 310,
            CS 356, CS 350, CS 360

         */


        return v;
    }
}
