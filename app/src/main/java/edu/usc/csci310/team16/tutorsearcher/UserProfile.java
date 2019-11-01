package edu.usc.csci310.team16.tutorsearcher;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private int id = -1;
    //possible availabilities, corresponding to "when is good" blocks
    private List<Integer> availability = new ArrayList<>();
    private String picture_url = "";
    private String name = "";
    private String grade = "";
    private String email = "";
    //just for tutors
    private List<String> coursesTaken = new ArrayList<>(); //tutor
    private List<String> tutorClasses = new ArrayList<>(); //tutor (which can they teach)
    private double rating = -1;
    //just for tutees
    private List<Integer> previousTutors = new ArrayList<>(); //other tutors this tutee is working with

    //singleton
    private static UserProfile currentUser;

    //SET TO DEFAULT VALUES FOR TESTING
    public UserProfile() {
        name = "Teagan";
        grade = "Junior";
        rating = 4.57;

        coursesTaken.add("CS103");
        coursesTaken.add("CS104");
        coursesTaken.add("CS270");
        coursesTaken.add("CS201");

        tutorClasses.add("CS103");
        tutorClasses.add("CS102");

    }

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

    public double getRating() {
        return rating;
    }

    public List<Integer> getPreviousTutors() {
        return previousTutors;
    }

}
