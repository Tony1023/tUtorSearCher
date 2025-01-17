package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.usc.csci310.team16.tutorsearcher.databinding.TutorFragmentBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Fragment responsible for the TutorList/Tutor tab
 */
public class TutorFragment extends Fragment {
    TutorListModel tutorModel;
    RecyclerView.LayoutManager layoutManager;
    TutorFragmentBinding binding;
    RecyclerView recyclerView;
    List<UserProfile> tutors;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TutorFragmentBinding.inflate(inflater,container,false);
        tutorModel = new TutorListModel(this);
        binding.setViewModel(tutorModel);

        recyclerView = binding.tutorsView;

        recyclerView.setAdapter(tutorModel.getAdapter());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        View v = (View) binding.getRoot();

        final TextView emptyMessageView = (TextView) v.findViewById(R.id.empty_message);
        final FrameLayout TutorsLayout = (FrameLayout) v.findViewById(R.id.tutors_layout);

        tutorModel.getTutors().observe(this,
                new Observer<List<Tutor>>() {
                    @Override
                    public void onChanged(List<Tutor> tutors) {
                        tutorModel.getAdapter().setTutors(tutors);

                        if(tutors == null || tutors.isEmpty()) {
                            TutorsLayout.setVisibility(View.GONE);
                            emptyMessageView.setVisibility(View.VISIBLE);
                        }
                        else {
                            TutorsLayout.setVisibility(View.VISIBLE);
                            emptyMessageView.setVisibility(View.GONE);
                        }
                    }
                });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tutors = new ArrayList<UserProfile>();
        RemoteServerDAO.getDao().getTutors().enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserProfile>> call, @NonNull Response<List<UserProfile>> response) {
                List<UserProfile> body = response.body();
                if(body != null) {
                    tutors.addAll(body);

                    List<Tutor> notes = new ArrayList<>();

                    for(UserProfile up : tutors){
                        Tutor n1 = new Tutor("fd_FD_f","MSG",up);

                        notes.add(n1);

                    }

                    tutorModel.getTutors().postValue(notes);
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {

            }
        });

    }

    public void goToProfile(View v){
        ProfileFragment profile = new ProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profile)
                .commit();

    }

    public void goToProfile(Tutor tutor){
        TutorProfileFragment profile = new TutorProfileFragment(tutor.getProfile());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, profile)
                .commit();

    }
}