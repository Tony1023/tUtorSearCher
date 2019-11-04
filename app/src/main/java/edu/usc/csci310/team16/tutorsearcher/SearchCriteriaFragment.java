package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class SearchCriteriaFragment extends Fragment {

    private SearchModel searchModel;
    private ToggleButton time_toggle[][];

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);

        searchModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);
        time_toggle = new ToggleButton[searchModel.getDays().length][searchModel.getBlocks().length];

//        // TODO: get courses from call to db
//        courses = new ArrayList<String>(Arrays.asList("CSCI 103", "CSCI 104", "CSCI 201", "CSCI 310"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_criteria_fragment, container, false);

        Spinner courseSpinner = (Spinner) v.findViewById(R.id.course_spinner);
        final ArrayAdapter<String> courseSpinnerArrayAdapter = new ArrayAdapter<String>(
                v.getContext(), R.layout.course_spinner_item, searchModel.getCourses());
        courseSpinnerArrayAdapter.setDropDownViewResource(R.layout.course_spinner_item);
        courseSpinner.setAdapter(courseSpinnerArrayAdapter);

        GridLayout timeSelectGrid = (GridLayout) v.findViewById(R.id.time_select_grid);
        timeSelectGrid.setColumnCount(time_toggle.length);
        timeSelectGrid.setRowCount(1);
        for(int i = 0; i < time_toggle.length; i++){
            GridLayout g = new GridLayout(v.getContext());
            g.setColumnCount(1);
            g.setRowCount(time_toggle[0].length);

            TextView t = new TextView(v.getContext());
            t.setText(searchModel.getDays()[i]);
            g.addView(t);

            for(int j = 0; j < time_toggle[0].length; j++){
                time_toggle[i][j] = new ToggleButton(v.getContext());
                time_toggle[i][j].setText(searchModel.getBlocks()[j]);
                time_toggle[i][j].setTextOn(searchModel.getBlocks()[j]);
                time_toggle[i][j].setTextOff(searchModel.getBlocks()[j]);
                g.addView(time_toggle[i][j]);
            }

            timeSelectGrid.addView(g);
        }
//        ToggleButton tb = new ToggleButton(v.getContext());
//        timeSelectGrid.addView(tb);

        //when clicking search button, transition back to search page, with search results
        Button searchButton = (Button) v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ((MainActivity)getActivity()).getSearch())
                        .commit();
            }
        });

        String classChosen = courseSpinner.getSelectedItem().toString();

        return v;
    }

}
