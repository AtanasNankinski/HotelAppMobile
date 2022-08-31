package com.example.hotelappmobile.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.FullUser
import com.example.hotelappmobile.Repositories.AuthRepository
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    val user: MutableLiveData<FullUser> = MutableLiveData()

    private val authRepository: AuthRepository = AuthRepository()

    fun logout(){
        viewModelScope.launch {
            if (user.value != null) {
                try {
                    val response = authRepository.logout("Bearer ${user.value!!.token}")
                    if (response.isSuccessful){
                        Log.d("RESPONSE", "Logout action successful")
                    }else {
                        Log.d("RESPONSE", response.message())
                    }
                }catch (e: Exception){
                    Log.d("RESPONSE", e.message.toString())
                }
            }
        }
    }
}