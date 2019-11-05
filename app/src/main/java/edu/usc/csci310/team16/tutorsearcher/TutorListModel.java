package edu.usc.csci310.team16.tutorsearcher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.usc.csci310.team16.tutorsearcher.Tutor;

import java.util.List;

public class TutorListModel extends ViewModel {
    int indexTutor = 0;

    MutableLiveData<List<Tutor>> tutors = new MutableLiveData<>();
    final TutorListAdapter adapter = new TutorListAdapter(this);

    public MutableLiveData<List<Tutor>> getTutors() {
        return tutors;
    }

    public TutorListAdapter getAdapter() {
        return adapter;
    }
}