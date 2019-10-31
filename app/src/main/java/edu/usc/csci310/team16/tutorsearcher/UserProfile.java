package edu.usc.csci310.team16.tutorsearcher;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private int id;
    //possible availabilities, corresponding to "when is good" blocks
    private ArrayList<Integer> availability;
    private String picture_url;
    private String name;
    private String grade;

    //just for tutors
    private List<String> coursesTaken; //tutor
    private List<String> tutorClasses; //tutor (which can they teach)
    private double rating;

    //just for tutees
    private ArrayList<Integer> previousTutors; //other tutors this tutee is working with

    public UserProfile() {
        name = "Teagan";
    }

    public String getName() {
        return name;
    }
}
