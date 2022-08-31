package com.example.hotelappmobile.ViewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch

class AddHotelViewModel : ViewModel() {
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val isSuccessful: MutableLiveData<Boolean> = MutableLiveData(false)
    private val repository: HotelActionsRepository = HotelActionsRepository()

    fun isFieldValid(fieldText: String): Boolean {
        if (fieldText.isNotEmpty()){
            errorMessage.value = ""
            return true
        }else {
            errorMessage.value = "Field is empty!"
            return false
        }
    }

    fun addHotel(token: String, hotelName: String){
        val hotel = Hotel(null, hotelName, null, null, null)

        viewModelScope.launch {
            val response = repository.addHotel("Bearer $token", hotel)
            if (response.isSuccessful){
                isSuccessful.value = true
            }else {
                errorMessage.value = "Error ${response.code()} - ${response.message()}"
                isSuccessful.value = false
            }
        }
    }
}