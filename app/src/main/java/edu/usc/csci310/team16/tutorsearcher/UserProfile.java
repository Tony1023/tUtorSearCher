package edu.usc.csci310.team16.tutorsearcher;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserProfile {

    private int id = -1;
    private String picture_url = "";
    private String name = "";
    private String grade = "";
    private String email = "";

    //constants for availability array
    private static final String[] days =
            {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private static final String[] blocks =
            {"8:00am", "8:30am", "9:00am", "9:30am", "10:00am", "10:30am", "11:00am",
            "11:30am", "12:00pm", "12:30pm", "1:00pm", "1:30pm", "2:00pm", "2:30pm",
            "3:00pm", "3:30pm", "4:00pm", "4:30pm", "5:00pm", "5:30pm", "6:00pm",
            "6:30pm", "7:00pm", "7:30pm", "8:00pm", "8:30pm", "9:00pm", "9:30pm"};

    //possible availabilities, corresponding to "when is good" blocks
    //8am - 10pm each day in 30 minute blocks
    //28 blocks per day, starting from 0 (8am-8:30am) on Monday and 28 on Tuesday
    //divide by 28 to get day (0: Monday, 1: Tuesday, ...)
    //mod by 28 to get the time block on that day
    private List<Integer> availability = new ArrayList<>();

    //corresponds to ids of availability slots that have a tutor is using to tutor an accepted tutee
    private Set<Integer> disabledSlots = new HashSet<>();

    //just for tutors
    private List<String> coursesTaken = new ArrayList<>(); //tutor
    private List<String> tutorClasses = new ArrayList<>(); //tutor (which can they teach)
    private double rating = 0.0;
    private String bio = "";


    //singleton
    private static UserProfile currentUser;

    public static void setCurrentUser(UserProfile profile) {
        currentUser = profile;
    }

    public static UserProfile getCurrentUser() {
        return currentUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getAvailability() {
        return availability;
    }

    public void setAvailability(ArrayList<Integer> times) {
        availability = times;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public List<String> getCoursesTaken() {
        return coursesTaken;
    }

    public List<String> getTutorClasses() {
        return tutorClasses;
    }

    public void setCoursesTaken(ArrayList<String> list) {
        coursesTaken = list;
    }

    public void setTutorClasses(ArrayList<String> list) {
        tutorClasses = list;
    }

    public double getRating() {
        return rating;
    }

    public Set<Integer> getDisabledSlots() { return disabledSlots; }

    //adds slot to disabled array and removes it from availability array automatically
    public void disableSlot(int availabilityID) {
        disabledSlots.add(availabilityID);
        availability.remove(availabilityID);
    }

}
