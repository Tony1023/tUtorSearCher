package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.*;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableParcelable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.checkbox.MaterialCheckBox;
import edu.usc.csci310.team16.tutorsearcher.model.WebServiceRepository;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnAvailabilityUpdatedListener} interface
 * to handle interaction events.
 * Use the {@link TimePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimePickerFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "availability";
    private static final String TAG = "TIME_PICKER_FRAGMENT";
    private NotificationModel model;
    Notification notification;

    private boolean[][] availability =  new boolean[7][28];
    private boolean[][] selected = new boolean[7][28];
    private final MaterialCheckBox[][] buttons = new MaterialCheckBox[7][28];
    private int index;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 1.
     * @return A new instance of fragment TimePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimePickerFragment newInstance(int param1, String param2) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(
                getActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(
                        getActivity().getApplication()
                )).get(NotificationModel.class);
        model.getPickerView().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean.booleanValue()) {
                    openNotifications();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);

        notification = model.getNotifications().getValue().get(model.getPosition());

        availability = parseAvailability(notification.getOverlap());

        //AVAILABILITY
        GridLayout timeSelectGrid = (GridLayout) view.findViewById(R.id.time_select_grid);

        timeSelectGrid.setColumnCount(availability.length + 1);
        timeSelectGrid.setRowCount(availability[0].length + 1);
        timeSelectGrid.setOrientation(GridLayout.VERTICAL);

        TextView t = new TextView(view.getContext());
        t.setText("");
        timeSelectGrid.addView(t);

        for(int j = 0; j < availability[0].length; j++){
            t = new TextView(view.getContext());
            t.setText(SearchModel.getBlocks().get(j));
            timeSelectGrid.addView(t);
        }

        for(int i = 0; i < availability.length; i++){
            t = new TextView(view.getContext());
            t.setWidth(100);
            t.setText(SearchModel.getDays().get(i));
            t.setGravity(Gravity.CENTER);
            timeSelectGrid.addView(t);


            for(int j = 0; j < availability[0].length; j++){

                buttons[i][j] = new MaterialCheckBox(view.getContext());

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                buttons[i][j].setLayoutParams(params);

//               time_toggle[i][j].setGravity(Gravity.CENTER);

                //set green if available during that time
                setButton(i, j);
                timeSelectGrid.addView(buttons[i][j]);
            }

        }

        view.findViewById(R.id.plus_one_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.setOverlap(serializeAvailability(selected));
                pushAvailability();
            }
        });
        return view;
    }

    private void setButton(int i, int j) {
        if(availability[i][j]) {
            //buttons[i][j].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.material_on_primary_emphasis_medium));
            buttons[i][j].setVisibility(View.VISIBLE);
            if (!buttons[i][j].hasOnClickListeners()) {
                buttons[i][j].setOnCheckedChangeListener(new SubmissionListener(i, j));
            }
        }

        //set red otherwise
        else {
            //buttons[i][j].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.design_default_color_error));
            //buttons[i][j].setClickable(false);
            buttons[i][j].setVisibility(View.INVISIBLE);
        }
    }

    //TODO
    public boolean[][] parseAvailability(String availability){
        Log.i(TAG,"Parsing:" + availability);
        if (availability.length() == 196){ //TODO find this length
            Log.i(TAG,"Parsing success");
            for(int i = 0; i < 7; ++i){
                for (int j = 0; j < 28; j++) {
                    this.availability[i][j] =
                            Integer.parseInt(String.valueOf(availability.charAt(i*28+j)))==1;
                }
            }
        }else{
            Log.e(TAG,"Parsing failure");
        }
        return this.availability;
    }

    public String serializeAvailability(boolean[][] availability){
        StringBuilder sb = new StringBuilder();
        for (boolean[] row : availability){
            for(boolean e: row){
                sb.append(e? '1':'0');
            }
        }
        return sb.toString();
    }


    public void updateScreen(){
        notification = model.getNotification();
        String overlap = notification.getOverlap();
        availability = parseAvailability(overlap);
        for (int i =0; i < 7; i++){
            for(int j = 0; j <28; j++){
                setButton(i,j);
            }
        }
    }

    public void pushAvailability(){
        getView().findViewById(R.id.notification_load_accept).setVisibility(View.VISIBLE);

        MutableLiveData<Object> o = new MutableLiveData<>();

        o.observe(getActivity(), new Observer<Object>() {
            @Override
            public void onChanged(Object s) {
                //TODO finished
                model.getPickerView().postValue(false);
                if (s instanceof UserProfile) {
                    UserProfile.setCurrentUser((UserProfile) s);
                }
                openNotifications();
            }
        });

        //TODO not finding notifications....
        WebServiceRepository.getInstance(getContext()).acceptRequest(notification,o);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAvailabilityUpdatedListener {
        void onAvailabilityUpdated(int position,String updated);
    }

    class SubmissionListener implements CompoundButton.OnCheckedChangeListener {
        int i,j;
        public SubmissionListener(int i, int j){
            this.i = i;
            this.j = j;
        }
        /**
         * Called when the checked state of a MaterialButton has changed. Changes state
         *
         * @param button    The MaterialButton whose state has changed.
         * @param isChecked The new checked state of MaterialButton.
         */
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {
            Log.i(TAG, String.format("Clicked: %d %d", i,j));
            synchronized (availability){
                selected[i][j] = isChecked;
            }
        }
    }

    public void openNotifications() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        NotificationFragment notificationFragment = (NotificationFragment)
                manager.findFragmentById(R.id.notification_fragment);

        if (notificationFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            manager.popBackStackImmediate("OPEN_PICKER", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            NotificationFragment newFragment = new NotificationFragment();
            Bundle args = new Bundle();
            newFragment.setArguments(args);

            FragmentTransaction transaction = manager.beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, newFragment, "notifications");
            transaction.addToBackStack("NOTIFICATION_RETURN");

            // Commit the transaction
            transaction.commit();
        }
    }
}


