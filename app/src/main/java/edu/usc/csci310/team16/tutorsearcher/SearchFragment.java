package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

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

        if(searchModel.getCourses().contains(searchModel.getCourse()) && !searchModel.getAvailability().isEmpty()){
            Log.d("search fragment", "Less make da kalll");
            searchModel.search();

//            RemoteServerDAO.getDao().register(credentials.getValue()).enqueue(new Callback<Map<String, Object>>() {
//                @Override
//                public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
//                    validating.postValue(false);
//                    // Looks like postValue calls are queued
//                    Map<String, Object> res = response.body();
//                    Boolean success = (Boolean) res.get("success");
//                    if (success) {
//                        Integer id = (Integer) res.get("id");
//                        token.postValue(response.headers().get("access-token"));
//                        UserProfile profile = new UserProfile();
//                        profile.setId(id);
//                        profile.setEmail(credentials.getValue().email);
//                        user.postValue(profile);
//                    } else if (res.get("err") instanceof String){
//                        errorMessage.postValue((String) res.get("err"));
//                    } else {
//                        errorMessage.postValue("Oof, something went wrong.");
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
//                    validating.postValue(false);
//                    errorMessage.postValue("Network errors");
//                }
//            });
        }

        return v;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchModel = ViewModelProviders.of(requireActivity()).get(SearchModel.class);
    }


    public SearchModel getSearchModel() {
        return searchModel;
    }
}
