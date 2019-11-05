package edu.usc.csci310.team16.tutorsearcher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        notificationModel = new NotificationModel();

        View v = super.onCreateView(inflater,container,savedInstanceState);
        //ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.notification_fragment, container,false);
        binding = NotificationFragmentBinding.inflate(inflater,container,false);

        binding.setBind(notificationModel);
        recyclerView = binding.notificationsView;
        final NotificationListAdapter adapter = new NotificationListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<Notification> notes = new ArrayList<>();
        Notification n1 = new Notification("fd_FD_f","MSG","Tutor with Mike");
        notes.add(n1);

        n1 = new Notification("fd_FD_f","MSG","Tutor with Mike");
        notes.add(n1);
        ((NotificationListAdapter)recyclerView.getAdapter()).setNotifications(notes);
    }
}
