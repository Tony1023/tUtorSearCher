package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView message;

        public ViewHolder(View v) {
            super(v);
            // TODO Implement view holder
            name =  v.findViewById(R.id.notification_text);
            message = v.findViewById(R.id.notification_type);
        }
    }

    private final LayoutInflater mInflater;
    private List<Notification> mNotifications = new ArrayList<>();

    NotificationListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.notification_msg, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        if (mNotifications.isEmpty()){
            holder.message.setText(R.string.messages_unavailable);
        }else{
            Notification current = mNotifications.get(position);
            holder.message.setText(current.getMsg());
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
