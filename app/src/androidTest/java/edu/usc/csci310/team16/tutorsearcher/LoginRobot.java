package edu.usc.csci310.team16.tutorsearcher;

public class LoginRobot extends BaseTestRobot {

    public void login(String email, String password) {
        super.fillEditText(R.id.email, email);
        super.fillEditText(R.id.password, password);
        super.clickButton(R.id.email_login_button);
    }

    public void register(String email, String password) {
        super.fillEditText(R.id.email, email);
        super.fillEditText(R.id.password, password);
        super.clickButton(R.id.email_register_button);
    }

}
