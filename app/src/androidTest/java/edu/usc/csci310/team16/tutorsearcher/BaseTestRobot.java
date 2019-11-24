package edu.usc.csci310.team16.tutorsearcher;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class BaseTestRobot {

    public ViewInteraction fillEditText(int resId, String text) {
        return onView(withId(resId)).perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard());
    }

    public ViewInteraction clickButton(int resId) {
        return onView((withId(resId))).perform(ViewActions.click());
    }

    public ViewInteraction getTextView(int resId) {
        return onView(withId(resId));
    }

    public ViewInteraction matchText(ViewInteraction view, String text) {
        return view.check(ViewAssertions.matches(ViewMatchers.withText(text)));
    }

    public ViewInteraction matchText(int resId, String text) {
        return matchText(getTextView(resId), text);
    }


//    fun clickListItem(listRes: Int, position: Int) {
//        onData(anything())
//                .inAdapterView(allOf(withId(listRes)))
//                .atPosition(position).perform(ViewActions.click())
//    }



}
