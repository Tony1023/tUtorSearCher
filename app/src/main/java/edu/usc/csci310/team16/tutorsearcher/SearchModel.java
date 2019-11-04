package edu.usc.csci310.team16.tutorsearcher;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

public class SearchModel extends ViewModel {
    private static final String[] days =
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[] blocks =
            {"8:00am", "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am",
                    "11:30am", "12:00pm", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm",
                    "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm",
                    "6:30pm", "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm"};
    private static final String[] courses =
            {"CSCI 103", "CSCI 104", "CSCI 170", "CSCI 201", "CSCI 270", "CSCI 310", "CSCI 350",
                    "CSCI 356", "CSCI 360"};

    private String course;
    private List<Integer> availability;


    public static String[] getDays() {
        return days;
    }

    public static String[] getBlocks() {
        return blocks;
    }

    public static String[] getCourses() {
        return courses;
    }

    public String getCourse() {
        return course;
    }

    public List<Integer> getAvailability() {
        return availability;
    }


    public void setCourse(String course) {
        this.course = course;
    }

    public void setAvailability(List<Integer> availability) {
        this.availability = availability;
    }
}
