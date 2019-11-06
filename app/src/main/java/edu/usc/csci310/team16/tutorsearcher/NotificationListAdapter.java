package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationMsgBinding;
import edu.usc.csci310.team16.tutorsearcher.model.RoomDBRepository;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

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
        private MutableLiveData<Boolean> openButtons;


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



            List<Notification> notes = mNotifications;
            if (notes != null) {
                final Notification notification = notes.get(position);

                if ("ACCEPTED".equals(notification.getStatus()) || "REJECTED".equals(notification.getStatus())){
                    binding.notificationButtons.setVisibility(View.GONE);
                }else {
                    binding.notificationAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebServiceRepository.getInstance(viewModel.getApplication()).acceptRequest(notification);
                            notification.setStatus("ACCEPTED");
                            binding.notificationButtons.setClickable(false);
                        }
                    });

                    binding.notificationReject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebServiceRepository.getInstance(viewModel.getApplication()).rejectRequest(notification);
                            binding.getRoot().setVisibility(View.GONE);
                            notification.setStatus("REJECTED");
                            RoomDBRepository.getInstance(viewModel.getApplication()).changeStatus(notification);

                        }
                    });
                }
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
