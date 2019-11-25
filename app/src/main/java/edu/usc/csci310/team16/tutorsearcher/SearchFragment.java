package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.usc.csci310.team16.tutorsearcher.databinding.SearchFragmentBinding;

public class SearchFragment extends Fragment {

    private SearchModel searchModel;
    SearchFragmentBinding binding;
    SearchCriteriaFragment criteriaFragment;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SearchFragmentBinding.inflate(inflater,container,false);
        binding.setViewModel(searchModel);

        searchModel.getError().observe(this,
                new Observer<String>() {
                    @Override
                    public void onChanged(String errorMessage) {
                        ((TextView)getActivity().findViewById(R.id.error_message)).setText(errorMessage);
                        if (errorMessage.equals("")) {
                            getActivity().findViewById(R.id.error_message).setVisibility(View.GONE);
                        } else {
                            getActivity().findViewById(R.id.error_message).setVisibility(View.VISIBLE);
                        }
                    }
                });

        recyclerView = binding.searchResultsView;

        recyclerView.setAdapter(searchModel.getAdapter());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        searchModel.getSearchResults().observe(this,
                new Observer<List<UserProfile>>() {
                    @Override
                    public void onChanged(List<UserProfile> results) {
                        searchModel.getAdapter().setResults(results);
                    }
                });

        View v = binding.getRoot();

//        getActivity().getSupportFragmentManager().be

        //when clicking search button, transition to search criteria page
        Button searchButton = (Button)v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, criteriaFragment)
                        .commit();
            }
        });

        Log.d("search fragment", searchModel.getCourse());
        Log.d("search fragment", searchModel.getAvailability().toString());
        if(searchModel.getSearchResults().getValue() != null)
            Log.d("search fragment", searchModel.getSearchResults().getValue().toString());

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchModel = ViewModelProviders.of(requireActivity()).get(SearchModel.class);
        searchModel.setFragmentManager(getActivity().getSupportFragmentManager());
        criteriaFragment = new SearchCriteriaFragment();
    }


    public SearchModel getSearchModel() {
        return searchModel;
    }
}
