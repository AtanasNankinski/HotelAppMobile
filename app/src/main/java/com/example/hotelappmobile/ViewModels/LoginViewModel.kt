package com.example.hotelappmobile.ViewModels

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.FullUser
import com.example.hotelappmobile.Models.LoginBody
import com.example.hotelappmobile.Models.User
import com.example.hotelappmobile.Repositories.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    val fullUser: MutableLiveData<FullUser> = MutableLiveData()

    private val authRepository: AuthRepository = AuthRepository()

    fun checkCredentials(email: String, password: String) : Boolean{
        if (email.isEmpty() && password.isEmpty()){
            errorMessage.value = "Email and password fields are empty!"
            return false
        } else if (email.isEmpty()){
            errorMessage.value = "Email field is empty!"
            return false
        } else if(password.isEmpty()){
            errorMessage.value = "Password field is empty!"
            return false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                errorMessage.value = ""
                return true
            } else {
                errorMessage.value = "The email is in wrong format!"
                return false
            }
        }
    }

    fun login(body: LoginBody){
        viewModelScope.launch {
            val response = authRepository.login(body)

            if (response.code() in 200..299){
                if (response.body() != null){
                    val id = response.body()!!.user.id
                    val userName = response.body()!!.user.userName
                    val email = response.body()!!.user.email
                    val userType = response.body()!!.user.userType
                    val token = response.body()!!.token
                    fullUser.value = FullUser(id, userName, email, userType, token)
                    errorMessage.value = ""
                }
            }else if (response.code() == 401){
                errorMessage.value = "Wrong username or password"
            }else if (response.code() in 400..512){
                errorMessage.value = "Error ${response.code()}"
            }
        }
    }
}