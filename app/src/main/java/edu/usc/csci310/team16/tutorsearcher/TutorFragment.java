package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.usc.csci310.team16.tutorsearcher.databinding.TutorFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment responsible for the TutorList/Tutor tab
 */
public class TutorFragment extends Fragment {
    TutorListModel tutorModel;
    RecyclerView.LayoutManager layoutManager;
    TutorFragmentBinding binding;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TutorFragmentBinding.inflate(inflater,container,false);
        tutorModel = new TutorListModel();
        binding.setViewModel(tutorModel);

        recyclerView = binding.tutorsView;

        recyclerView.setAdapter(tutorModel.getAdapter());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        tutorModel.getTutors().observe(this,
                new Observer<List<Tutor>>() {
                    @Override
                    public void onChanged(List<Tutor> tutors) {
                        tutorModel.getAdapter().setTutors(tutors);
                    }
                });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<Tutor> notes = new ArrayList<>();
        Tutor n1 = new Tutor("fd_FD_f","MSG","Tutor with Mike");
        notes.add(n1);

        n1 = new Tutor("fd_FD_f","MSG","Tutor with Bob");
        notes.add(n1);
        tutorModel.getTutors().postValue(notes);
    }
}