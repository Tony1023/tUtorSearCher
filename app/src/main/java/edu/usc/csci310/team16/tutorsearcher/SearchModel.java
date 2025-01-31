package edu.usc.csci310.team16.tutorsearcher;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchModel extends ViewModel {
    private static final List<String> days = Arrays.asList("M", "Tu", "W", "Th", "F", "Sa", "Su");
    private static final List<String> blocks = Arrays.asList(
            "8:00am", "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am",
            "11:30am", "12:00pm", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm",
            "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm",
            "6:30pm", "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm");
    private static final List<String> courses = Arrays.asList(
            "CSCI103", "CSCI104", "CSCI170", "CSCI201", "CSCI270", "CSCI310", "CSCI350",
            "CSCI356", "CSCI360");

    private String course = "init";
    private List<Integer> availability = new ArrayList<>();
    private FragmentManager fragmentManager;

    MutableLiveData<List<UserProfile>> searchResults = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();
    final SearchResultsAdapter adapter = new SearchResultsAdapter(this);


    public void search() {
        Map<String,Object> q = new HashMap<>();
        q.put("class", course);
        q.put("availability", availability);

        RemoteServerDAO.getDao().searchTutor(q).enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserProfile>> call, @NonNull Response<List<UserProfile>> response) {
                Log.d("search model", "succeeded " + call.toString() + " " + response.toString() + " " + response.body());
                searchResults.postValue(response.body());
                error.setValue("");
            }

            @Override
            public void onFailure(@NonNull Call<List<UserProfile>> call, @NonNull Throwable t) {
                Log.e("search model", "failed " + call.toString() + " " + t.toString());
                error.setValue("Search failed: network error");
            }
        });
    }


    public List<Integer> intersect(List<Integer> a1, List<Integer> a2) {
        List<Integer> r = new ArrayList<>();
        for(int i : a1) {
            if(a2.contains(i)) {
                r.add(i);
            }
        }
        return r;
    }


    public static List<String> getDays() {
        return days;
    }

    public static List<String> getBlocks() {
        return blocks;
    }

    public static List<String> getCourses() {
        return courses;
    }

    public String getCourse() {
        return course;
    }

    public List<Integer> getAvailability() {
        return availability;
    }

    public FragmentManager getFragmentManager() { return fragmentManager; }

    public MutableLiveData<List<UserProfile>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public SearchResultsAdapter getAdapter() { return adapter; }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

}
