package edu.usc.csci310.team16.tutorsearcher;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationMsgBinding;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private final NotificationModel viewModel;
    private List<Notification> mNotifications = new ArrayList<>();


    class ViewHolder extends RecyclerView.ViewHolder {
        private final NotificationMsgBinding binding;
        private final TextView message;
        private final MaterialButtonToggleGroup buttonToggleGroup;
        private MutableLiveData<Boolean> openButtons;
        private Observer observer;
        private final MutableLiveData<String> finished;

        public ViewHolder(ViewDataBinding bind) {
            super(bind.getRoot());
            binding = (NotificationMsgBinding) bind;
            message = binding.notificationText;
            buttonToggleGroup = binding.notificationButtons;
            finished = new MutableLiveData<String>();
            observer = new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if(s.equals("FAILED")){
                        buttonToggleGroup.setVisibility(View.VISIBLE);
                        // TODO: Implement error message view
                        binding.notificationAccept.setError("Your request has failed");
                    }
                }
            };
        }

        public void bind(final int position){
            binding.setViewModel(viewModel);
            binding.setPosition(position);

            List<Notification> notes = mNotifications;
            if (notes != null) {
                final Notification notification = notes.get(position);

                finished.observeForever( observer );

                binding.notificationAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonToggleGroup.setVisibility(View.GONE);
                        viewModel.setPosition(position);
                        viewModel.getPickerView().postValue(true);
                    }
                });

                binding.notificationReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buttonToggleGroup.setVisibility(View.GONE);
                        WebServiceRepository.getInstance(viewModel.getApplication()).rejectRequest(notification,finished);
                    }
                });
            }

        }
    }



    public NotificationListAdapter(NotificationModel model){
        viewModel = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NotificationMsgBinding binding = NotificationMsgBinding.inflate( inflater,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        holder.bind(position);
        if (mNotifications.isEmpty()){
            holder.message.setText(R.string.messages_unavailable);
        }else{
            Notification current = mNotifications.get(position);
            holder.binding.notificationSender.setText("From: " + current.getSenderName());

            StringBuilder sb = new StringBuilder();
            sb.append(current.getMsg());
            holder.buttonToggleGroup.setVisibility(View.GONE);
            if (current.getStatus() == 0){
                if (current.getType() == 0) {
                    sb.append("\nYou are a candidate for a tutor.");
                    holder.buttonToggleGroup.setVisibility(View.VISIBLE);
                }else{
                    sb.append("\nThis request has expired");
                    holder.buttonToggleGroup.setVisibility(View.GONE);
                }
            } else if(current.getStatus() == 1) {

                sb.append("\nYou found a ").append(
                        (current.getType()==0)?"tutee":"tutor");
            }else if (current.getStatus() == 2){
                sb.append("\nUnfortunately, you have been rejected.\nBetter luck next time");
            } else if (current.getStatus() == 3) {
                sb.append("\nUnfortunately, the tutee has found another tutor.\nBetter luck next time");
            }else{
                sb.append("\nThis notification has lapsed");
            }
            holder.message.setText(sb.toString());
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder){
        holder.finished.removeObserver(holder.observer);
    }

    // Clean all elements of the recycler
    public void clear() {
        mNotifications.clear();
        notifyDataSetChanged();
    }

    public void setNotifications(List<Notification> notifications){
        mNotifications = notifications;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }
}
