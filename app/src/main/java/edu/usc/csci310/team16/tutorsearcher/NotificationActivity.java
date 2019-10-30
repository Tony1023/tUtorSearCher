package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationFragmentBinding;

public class NotificationActivity extends Fragment {
    NotificationModel notificationModel;
    RecyclerView.LayoutManager layoutManager;
    NotificationFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationModel = new NotificationModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.notification_fragment,container,false);
        binding.setBind(notificationModel);

        layoutManager = new LinearLayoutManager(getActivity());

        //TODO Finish this part
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
