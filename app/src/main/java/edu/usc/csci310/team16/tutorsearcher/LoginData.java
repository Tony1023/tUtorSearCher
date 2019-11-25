package edu.usc.csci310.team16.tutorsearcher;

import edu.usc.csci310.team16.tutorsearcher.model.RoomDBRepository;

public class LoginData {

    public final String email;
    public final String password;

    LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
