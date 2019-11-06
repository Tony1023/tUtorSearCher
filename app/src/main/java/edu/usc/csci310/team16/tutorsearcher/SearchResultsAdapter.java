package edu.usc.csci310.team16.tutorsearcher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import edu.usc.csci310.team16.tutorsearcher.databinding.SearchResultItemBinding;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private final SearchModel viewModel;
    private List<UserProfile> mResults = new ArrayList<>();

    private TutorProfileFragment profileFragment = new TutorProfileFragment();

    class ViewHolder extends RecyclerView.ViewHolder {
        private final SearchResultItemBinding binding;
        private final MaterialCardView card;
        private final TextView name;

        public ViewHolder(ViewDataBinding bind) {
            super(bind.getRoot());
            binding = (SearchResultItemBinding) bind;
            card = binding.searchResultCard;
            name = binding.searchResultName;
        }

        public void bind(int position) {
            binding.setViewModel(viewModel);
            binding.setPosition(position);
        }
    }

    public SearchResultsAdapter(SearchModel model){
        viewModel = model;
    }

    @NonNull
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SearchResultItemBinding binding = SearchResultItemBinding.inflate(inflater,parent,false);
        return new SearchResultsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.ViewHolder holder, int position){
        holder.bind(position);
        if (mResults.isEmpty()){
            holder.name.setText(R.string.no_search_results);
        }else{
            final UserProfile current = mResults.get(position);
            Log.d("search results adapter", "updating card with name " + current.getName());
            holder.name.setText(current.getName());

            holder.card.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    profileFragment.setUser(current);
                    viewModel.getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new TutorProfileFragment(current))
                            .commit();
                }
            });
        }
    }

    public void setResults(List<UserProfile> results){
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }
}
