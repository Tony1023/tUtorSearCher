package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

        recyclerView = binding.searchResultsView;

        recyclerView.setAdapter(searchModel.getAdapter());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        View v = binding.getRoot();

        final TextView errorMessageView = (TextView) v.findViewById(R.id.error_message);
        final TextView emptyMessageView = (TextView) v.findViewById(R.id.empty_message);
        final FrameLayout searchResultsLayout = (FrameLayout) v.findViewById(R.id.search_results_layout);

        searchModel.getError().observe(this,
                new Observer<String>() {
                    @Override
                    public void onChanged(String errorMessage) {
                        errorMessageView.setText(errorMessage);
                        if (errorMessage.equals("")) {
                            errorMessageView.setVisibility(View.GONE);
                        } else {
                            errorMessageView.setVisibility(View.VISIBLE);
                        }
                    }
                });

        searchModel.getSearchResults().observe(this,
                new Observer<List<UserProfile>>() {
                    @Override
                    public void onChanged(List<UserProfile> results) {
                        searchModel.getAdapter().setResults(results);

                        if(results == null || results.isEmpty()) {
                            searchResultsLayout.setVisibility(View.GONE);
                            emptyMessageView.setVisibility(View.VISIBLE);
                        }
                        else {
                            searchResultsLayout.setVisibility(View.VISIBLE);
                            emptyMessageView.setVisibility(View.GONE);
                        }
                    }
                });

        //when clicking search button, transition to search criteria page
        Button searchButton = (Button)v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, criteriaFragment)
                        .commit();
            }
        });

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
