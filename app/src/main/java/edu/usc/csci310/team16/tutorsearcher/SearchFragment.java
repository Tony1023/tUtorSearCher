package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class SearchFragment extends Fragment {

    private SearchModel searchModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);

        //when clicking search button, transition to search criteria page
        Button searchButton = (Button)v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SearchCriteriaFragment())
                        .commit();
            }
        });

        Log.d("search fragment", searchModel.getCourse());
        Log.d("search fragment", searchModel.getAvailability().toString());

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);
    }


    public SearchModel getSearchModel() {
        return searchModel;
    }
}
