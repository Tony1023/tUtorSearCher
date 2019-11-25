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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import edu.usc.csci310.team16.tutorsearcher.databinding.NotificationFragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Fragment responsible for the Notifications tab
 */
public class NotificationFragment extends Fragment {
    NotificationModel notificationModel;
    RecyclerView.LayoutManager layoutManager;
    NotificationFragmentBinding binding;
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNotificationWorker();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        binding = NotificationFragmentBinding.inflate(inflater,container,false);

        notificationModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication()
                )).get(NotificationModel.class);

        binding.setViewModel(notificationModel);

        recyclerView = binding.notificationsView;

        recyclerView.setAdapter(notificationModel.getAdapter());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        notificationModel.onRefresh();

        binding.notificationSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationModel.onRefresh();
                binding.notificationSwipe.setRefreshing(false);
            }
        });

        return binding.getRoot();
    }

    public void addNotificationWorker(){
//        Data data = new Data.Builder()
//                .putString("TOKEN",getActivity().getSharedPreferences().getString(""))
//                .build(); //TODO find name of token


        //TODO redo this
        OneTimeWorkRequest notificationUpdateRequest =
                new OneTimeWorkRequest.Builder(NotificationWorker.class)
                        //.setInputData(data)
                        .build();

        WorkManager.getInstance(getContext()).enqueue(notificationUpdateRequest);
    }
}
