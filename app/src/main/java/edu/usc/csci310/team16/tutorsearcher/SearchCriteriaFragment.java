package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class SearchCriteriaFragment extends Fragment {

    private SearchModel searchModel;
    private MaterialCheckBox time_toggle[][];

    @Override
    public void onCreate(Bundle savedBundleInstance) {
        super.onCreate(savedBundleInstance);
        searchModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);
        time_toggle = new MaterialCheckBox[searchModel.getDays().size()][searchModel.getBlocks().size()];
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_criteria_fragment, container, false);

        Spinner courseSpinner = (Spinner) v.findViewById(R.id.course_spinner);
        final ArrayAdapter<String> courseSpinnerArrayAdapter = new ArrayAdapter<String>(
                v.getContext(), R.layout.course_spinner_item, searchModel.getCourses());
        courseSpinnerArrayAdapter.setDropDownViewResource(R.layout.course_spinner_item);
        courseSpinner.setAdapter(courseSpinnerArrayAdapter);

        int prevCourseIdx = searchModel.getCourses().indexOf(searchModel.getCourse());
        if(prevCourseIdx >= 0){
            courseSpinner.setSelection(prevCourseIdx);
        }

        // Populate time select grid with check boxes for every time

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
                time_toggle[i][j].setTag("availability_checkbox_" + (i*time_toggle[0].length + j)); //ADDED DYNAMIC ID
                timeSelectGrid.addView(time_toggle[i][j]);
            }
        }

        List<Integer> prevAvailability = searchModel.getAvailability();
        for(int i = 0; i < prevAvailability.size(); i++){
            int slot = prevAvailability.get(i);
            time_toggle[slot / time_toggle[0].length][slot % time_toggle[0].length].setChecked(true);
        }


        //when clicking search button, transition back to search page, with search results
        Button searchButton = (Button) v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Spinner courseSpinner = (Spinner) getActivity().findViewById(R.id.course_spinner);
                String course = courseSpinner.getSelectedItem().toString();

                List<Integer> availability = new ArrayList<>();
                for(int i = 0; i < time_toggle.length; i++){
                    for(int j = 0; j < time_toggle[0].length; j++){
                        if(time_toggle[i][j].isChecked()){
                            availability.add(i*time_toggle[0].length + j);
                        }
                    }
                }

                searchModel.setCourse(course);
                searchModel.setAvailability(availability);

                searchModel.search();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, ((MainActivity)getActivity()).getSearch())
                        .commit();
            }
        });

        return v;
    }

}
