package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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

        notificationModel = new ViewModelProvider(getActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication()
                )).get(NotificationModel.class);

        binding.setViewModel(notificationModel);

        recyclerView = binding.notificationsView;

        NotificationListAdapter adapter = new NotificationListAdapter(notificationModel);
        notificationModel.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                notificationModel.removePosition(position);
                //TODO add network call
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);



        notificationModel.onRefresh();

        binding.notificationSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notificationModel.onRefresh();
                binding.notificationSwipe.setRefreshing(false);
            }
        });

        notificationModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            /**
             * Called when the data is changed.
             *
             * @param o The new data
             */
            @Override
            public void onChanged(List<Notification> o) {
                NotificationListAdapter adapter = (NotificationListAdapter) recyclerView.getAdapter();
                adapter.setNotifications(o);
            }
        });

        notificationModel.getPickerView().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean.booleanValue()){
                    openPicker();
                }
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
                new OneTimeWorkRequest.Builder(NotificationWorker.class).addTag("WORKER")
                        //.setInputData(data)
                        .build();

        WorkManager.getInstance(getContext()).enqueue(notificationUpdateRequest);
    }

    public void setAvailability(int position, String updated) {
        NotificationListAdapter adapter = (NotificationListAdapter) recyclerView.getAdapter();
        Notification data = notificationModel.mNotifications.getValue().get(position);
        data.setOverlap(updated);
        adapter.notifyItemChanged(position,data);
    }

    public void openPicker(){
        FragmentManager manager = getActivity().getSupportFragmentManager();

        TimePickerFragment timePickerFragment = (TimePickerFragment)
                manager.findFragmentById(R.id.time);

        if (timePickerFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            timePickerFragment.updateScreen();
            manager.popBackStackImmediate("RETURN_NOTIFICATION",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            TimePickerFragment newFragment = new TimePickerFragment();

            FragmentTransaction transaction = manager.beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment, "picker");
            transaction.addToBackStack("OPEN_PICKER");

            // Commit the transaction
            transaction.commit();
        }
    }
}
