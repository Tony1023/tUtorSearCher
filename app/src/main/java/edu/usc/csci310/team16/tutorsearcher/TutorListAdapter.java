package edu.usc.csci310.team16.tutorsearcher;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

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

        public void bind(int position) {
            binding.setViewModel(viewModel);
            binding.setPosition(position);

            final Tutor tutor = viewModel.getTutors().getValue().get(position);
            OnClickListener buttonListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TUTOR_ADAPTER","button clicked!");
                    viewModel.fragment.goToProfile(tutor);
                }
            };
            binding.gotoprofile.setOnClickListener(buttonListener);
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
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position){

        holder.bind(position);


        if (mTutors.isEmpty()){
            holder.message.setText(R.string.messages_unavailable);
        }else{
            Tutor current = mTutors.get(position);
            String name = "Tutor profile null";
            if (current.getProfile() != null){
                name = current.getProfile().getName();
            }
            holder.message.setText(name);
            switch (current.getType()){
                case "MSG":
                    Log.d("DebugAdapter", "in case of msg");
                    holder.buttonToggleGroup.setVisibility(View.VISIBLE);
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