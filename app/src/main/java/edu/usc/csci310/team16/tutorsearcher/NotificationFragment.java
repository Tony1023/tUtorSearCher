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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment responsible for the Notifications tab
 */
public class NotificationFragment extends Fragment {
    NotificationModel notificationModel;
    RecyclerView.LayoutManager layoutManager;
    NotificationFragmentBinding binding;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        binding = NotificationFragmentBinding.inflate(inflater,container,false);
        notificationModel = new NotificationModel(getActivity().getApplication());
        binding.setViewModel(notificationModel);

        recyclerView = binding.notificationsView;

        recyclerView.setAdapter(notificationModel.getAdapter());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        binding.notificationSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationModel.onRefresh();
                binding.notificationSwipe.setRefreshing(false);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        notificationModel.onCreate();
    }
}
