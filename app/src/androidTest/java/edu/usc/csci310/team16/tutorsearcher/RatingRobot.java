package edu.usc.csci310.team16.tutorsearcher;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RatingRobot extends BaseTestRobot {
    public void rate() {
        super.clickButton(R.id.simpleRatingBar);
    }


    //public ViewInteraction fillRatingBar(int resId, Float amt) {
        //return onView(withId(resId)).perform(ViewActions., ViewActions.closeSoftKeyboard());
    //}

    public ViewInteraction getRating(int resId) {
        return onView(withId(resId));
    }

    //public ViewInteraction matchRating(ViewInteraction view, Float amt) {
    //    return view.check(ViewAssertions.matches(ViewMatchers.withId(resId)));
    //}

    //public ViewInteraction matchRating(int resId, Float amt) {
   //     return matchRating(getTextView(resId), amt);
    //}


    public void loadRating() {
        ViewInteraction v = onView(withId(R.id.simpleRatingBar));
       // TextView rating = (TextView)v.findViewById(R.id.rating);
        //rating.setText(Double.toString(user.getRating()));
        //rt.setRating((float)user.getRating());
    }

    public void submitRating(float r) {
       // View v = inflater.inflate(R.layout.tutorprofile_fragment, container, false);
       // RatingBar rt = (RatingBar) v.findViewById(R.id.simpleRatingBar);

        //TextView rating = (TextView)v.findViewById(R.id.rating);
        //rating.setText(Double.toString(user.getRating()));
        //rt.setRating((float)r);
    }

}
