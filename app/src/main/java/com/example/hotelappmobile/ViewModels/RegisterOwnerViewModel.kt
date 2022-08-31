package com.example.hotelappmobile.ViewModels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.FullUser
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Models.HotelUser
import com.example.hotelappmobile.Repositories.AuthRepository
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch

class RegisterOwnerViewModel : ViewModel() {
    private val hotelRepository: HotelActionsRepository = HotelActionsRepository()
    private val authRepository: AuthRepository = AuthRepository()
    val hotelNames: MutableLiveData<MutableList<String>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val isCallSuccessfull: MutableLiveData<Boolean> = MutableLiveData(false)


    fun getHotels(viewModel: MainViewModel){
        val hotelNamesTemp: MutableList<String> = mutableListOf()

        viewModelScope.launch {
            val response = hotelRepository.getHotels("Bearer ${viewModel.user.value!!.token}")

            if (response.isSuccessful){
                val hotels: List<Hotel> = response.body()!!

                for (hotel in hotels){
                    hotelNamesTemp.add(hotel.hotelName)
                }
                hotelNames.value = hotelNamesTemp
                errorMessage.value = ""
            }else {
                errorMessage.value = "Error ${response.code()} - ${response.message()}"
            }
        }
    }

    fun registerOwner(body: HotelUser, token: String){
        viewModelScope.launch {
            val response = authRepository.registerOwner("Bearer $token", body)
            if (response.isSuccessful){
                errorMessage.value = ""
                isCallSuccessfull.value = true
            }else {
                errorMessage.value = "Error ${response.code()} - ${response.message()}"
                isCallSuccessfull.value = false
            }
        }
    }

    fun validateFields(name: String, email: String, password: String, hotel: String): Boolean{
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            errorMessage.value = "There are empty fields!"
            return false
        } else if(hotel == "Choose Hotel" || hotel.isEmpty()){
            errorMessage.value = "No hotel is being chosen!"
            return false
        }else {
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