package edu.usc.csci310.team16.tutorsearcher;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import edu.usc.csci310.team16.tutorsearcher.databinding.TutorFragmentBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;

import edu.usc.csci310.team16.tutorsearcher.databinding.TutorMsgBinding;

public class TutorListAdapter extends RecyclerView.Adapter<TutorListAdapter.ViewHolder> {

    private final TutorListModel viewModel;

    private List<Tutor> mTutors = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TutorMsgBinding binding;
        private final TextView name;
        private final TextView message;
        private final MaterialButtonToggleGroup buttonToggleGroup;

        public ViewHolder(ViewDataBinding bind) {
            super(bind.getRoot());
            binding = (TutorMsgBinding) bind;
            name =  binding.tutorType;
            message = binding.tutorText;
            buttonToggleGroup = binding.tutorButtons;

        }

        public void bind(int position){
            binding.setViewModel(viewModel);
            binding.setPosition(position);
        }
    }

    public TutorListAdapter(TutorListModel model){
        viewModel = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TutorMsgBinding binding = TutorMsgBinding.inflate(inflater, parent, false);
        //TutorprofileFragmentBinding binding = TutorprofileFragmentBinding.inflate( inflater,parent,false);
        return new ViewHolder(binding);
    }


    public void setButtons(ViewHolder holder){
        Log.d("DebugAdapter", "in setButtons");

        /* trying other solution
        Button btnSave = (Button)viewModel.findViewById(R.id.btnSave);

        OnClickListener btnListener = new OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                finish();
            }
        };
        btnSave.setOnClickListener(btnListener);

         */

        holder.buttonToggleGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("DebugAdapter", "onClick: ");
                Activity a = viewModel.fragment.getActivity();

                viewModel.fragment.getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new TutorProfileFragment())
                        .commit();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position){

        holder.bind(position);
        if (mTutors.isEmpty()){
            holder.message.setText(R.string.messages_unavailable);
        }else{
            Tutor current = mTutors.get(position);
            holder.message.setText(current.getMsg());
            switch (current.getType()){
                case "MSG":
                    Log.d("DebugAdapter", "in case of msg");
                    holder.buttonToggleGroup.setVisibility(View.VISIBLE);
                    setButtons(holder);
                    break;

                default:
            }

        }

    }




    public void setTutors(List<Tutor> tutors){
        mTutors = tutors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTutors.size();
    }
}