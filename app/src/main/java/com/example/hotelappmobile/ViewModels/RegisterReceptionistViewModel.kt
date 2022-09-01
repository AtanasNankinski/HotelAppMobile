package com.example.hotelappmobile.ViewModels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.ReceptionistCall
import com.example.hotelappmobile.Repositories.AuthRepository
import kotlinx.coroutines.launch

class RegisterReceptionistViewModel : ViewModel() {
    private val authRepository: AuthRepository = AuthRepository()
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val isCallSuccessfull: MutableLiveData<Boolean> = MutableLiveData(false)

    fun registerReceptionist(body: ReceptionistCall, token: String){
        viewModelScope.launch {
            val response = authRepository.registerReceptionist("Bearer $token", body)
            if (response.isSuccessful){
                errorMessage.value = ""
                isCallSuccessfull.value = true
            }else {
                errorMessage.value = "Error ${response.code()} - ${response.message()}"
                isCallSuccessfull.value = false
            }
        }
    }

    fun validateFields(name: String, email: String, password: String): Boolean{
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            errorMessage.value = "There are empty fields!"
            return false
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                errorMessage.value = ""
                return true
            }else {
                errorMessage.value = "Email is in wrong format!"
                return false
            }
        }
    }
}