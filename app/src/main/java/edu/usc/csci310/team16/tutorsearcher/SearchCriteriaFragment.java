package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SearchCriteriaFragment extends Fragment {

    private SearchModel searchModel;
    private ArrayList<String> courses;

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

//        searchModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);

        // TODO: get courses from call to db
        courses = new ArrayList<String>(Arrays.asList("CSCI 103", "CSCI 104", "CSCI 201", "CSCI 310"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_criteria_fragment, container, false);

        Spinner courseSpinner = (Spinner) v.findViewById(R.id.course_spinner);
        final ArrayAdapter<String> courseSpinnerArrayAdapter = new ArrayAdapter<String>(
                v.getContext(), R.layout.course_spinner_item, courses);
        courseSpinnerArrayAdapter.setDropDownViewResource(R.layout.course_spinner_item);
        courseSpinner.setAdapter(courseSpinnerArrayAdapter);

        //when clicking search button, transition back to search page, with search results
        Button searchButton = (Button) v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SearchCriteriaFragment())
                        .commit();
            }
        });

        return v;
    }

}
