package edu.usc.csci310.team16.tutorsearcher

import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

data class Login (var username: String = "", var password: String = ""){
    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }
}