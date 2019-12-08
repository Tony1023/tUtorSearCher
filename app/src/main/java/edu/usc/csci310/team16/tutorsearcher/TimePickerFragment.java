package edu.usc.csci310.team16.tutorsearcher;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.*;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.checkbox.MaterialCheckBox;

import static edu.usc.csci310.team16.tutorsearcher.BR.position;

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

    // TODO choose correct lengths
    private boolean[][] availability;
    private final MaterialCheckBox[][] buttons = new MaterialCheckBox[7][28];
    private int index;

    private OnAvailabilityUpdatedListener mListener;

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
        availability = new boolean[7][28];
        if (getArguments() != null) {
            int arg1 = getArguments().getInt(ARG_PARAM1);
            String arg2 = getArguments().getString(ARG_PARAM2);
            if (arg2 != null){
                Log.i(TAG, "Input bundle: "+arg2);
                parseAvailability(arg2);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);

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
                //TODO serialize availibility
                saveAvailability(availability.toString());
            }
        });

        return view;
    }

    private void setButton(int i, int j) {
        if(availability[i][j]) {
            buttons[i][j].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.material_on_primary_emphasis_medium));
            if (!buttons[i][j].hasOnClickListeners()) {
                buttons[i][j].setOnCheckedChangeListener(new SubmissionListener(i, j));
            }
        }

        //set red otherwise
        else {
            buttons[i][j].setBackgroundColor(ContextCompat.getColor(getContext(),R.color.design_default_color_error));
            buttons[i][j].setClickable(false);
        }
    }

    public void updateAvailability(String availabilityString){
        Log.e(TAG, "Availability update: "+ availabilityString);
        this.availability = parseAvailability(availabilityString);

        for(int i = 0; i < availability.length; i++){
            for(int j = 0; j < availability[0].length; j++){

                //set green if available during that time
                setButton(i, j);
            }
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

    //TODO
    public String serializeAvailability(boolean[][] availability){
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void saveAvailability(String update) {
        Log.i(TAG,"Saving preferred times");
        if (mListener != null) {
            mListener.onAvailabilityUpdated(position,update);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAvailabilityUpdatedListener) {
            mListener = (OnAvailabilityUpdatedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateInformation(int position, String availability) {
        index = position;
        this.availability = parseAvailability(availability);
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
                availability[i][j] = isChecked;
            }
        }
    }

}


