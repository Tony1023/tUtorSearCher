package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

//changed from extends Fragment
public class ProfileFragment extends Fragment {

    private UserProfile user;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        user = new UserProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView name = (TextView)v.findViewById(R.id.name);
        name.setText(user.getName());

        return v;

        //take UserProfile information and put it on labels in the view

        //edit button to create a new view with a bunch of text boxes in it
        //take the results of those text boxes and change the UserProfile data members
            //once the user clicks another button at the bottom of that page
            //finish() that view and go back to the Profile view

        //ignore the MutableLiveData for now

    }

}
