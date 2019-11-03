package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchModel.class);
    }

}
