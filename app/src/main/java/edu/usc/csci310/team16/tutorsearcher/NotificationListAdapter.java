package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationMsgBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private final NotificationModel viewModel;
    private List<Notification> mNotifications = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        private final NotificationMsgBinding binding;
        private final TextView name;
        private final TextView message;
        private final MaterialButtonToggleGroup buttonToggleGroup;

        public ViewHolder(ViewDataBinding bind) {
            super(bind.getRoot());
            binding = (NotificationMsgBinding) bind;
            name =  binding.notificationType;
            message = binding.notificationText;
            buttonToggleGroup = binding.notificationButtons;
        }

        public void bind(int position){
            binding.setViewModel(viewModel);
            binding.setPosition(position);
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
            holder.message.setText(current.getMsg());
            switch (current.getType()){
                case "MSG":
                    holder.buttonToggleGroup.setVisibility(View.GONE);
                    break;
                case "TUTOR_REQUEST":
                    holder.buttonToggleGroup.setVisibility(View.VISIBLE);
                    break;
                case "STUDENT_REQUEST":
                    holder.buttonToggleGroup.setVisibility(View.VISIBLE);
                    break;
                default:
            }

        }
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
