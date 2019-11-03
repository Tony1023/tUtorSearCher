package edu.usc.csci310.team16.tutorsearcher;

// importing required libraries
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import edu.usc.csci310.team16.tutorsearcher.databinding.TutorProfileFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class TutorProfileFragment extends Fragment {
    RatingBar rt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //binding = TutorFragmentBinding.inflate(inflater,container,false);

        View v = inflater.inflate(R.layout.tutorprofile_fragment, container);
        //binding MainActivity.java with activity_main.xml file
        rt = (RatingBar) v.findViewById(R.id.simpleRatingBar);

        //finding the specific RatingBar with its unique ID
        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();

        //Use for changing the color of RatingBar
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        return v;
       // return binding.getRoot();
    }

    public void Call(View v)
    {
        // This function is called when button is clicked. 
        // Display ratings, which is required to be converted into string first. 
        //TextView t = (TextView)findViewById(R.id.textView2);
        //t.setText("You Rated :"+String.valueOf(rt.getRating()));
    }
} 