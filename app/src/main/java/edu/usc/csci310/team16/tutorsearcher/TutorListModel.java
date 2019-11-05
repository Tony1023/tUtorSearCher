package edu.usc.csci310.team16.tutorsearcher;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import edu.usc.csci310.team16.tutorsearcher.Tutor;
import edu.usc.csci310.team16.tutorsearcher.databinding.TutorMsgBinding;

import java.util.List;

public class TutorListModel extends ViewModel {

    Fragment fragment;

    public TutorListModel(TutorFragment f){
        fragment = f;
    }


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