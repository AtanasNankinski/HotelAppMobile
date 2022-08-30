package com.example.hotelappmobile.ViewModels

import android.util.Patterns
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var errorMessage: String = ""

    fun checkCredentials(email: String, password: String) : Boolean{
        if (email.isEmpty() && password.isEmpty()){
            errorMessage = "Email and password fields are empty!"
            return false
        } else if (email.isEmpty()){
            errorMessage = "Email field is empty!"
            return false
        } else if(password.isEmpty()){
            errorMessage = "Password field is empty!"
            return false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                errorMessage = ""
                return true
            } else {
                errorMessage = "The email is in wrong format!"
                return false
            }
        }
    }
}