package com.example.hotelappmobile.ViewModels

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Models.HotelOwner
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch

class SetHotelOwnerViewModel: ViewModel() {
    private val hotelRepository: HotelActionsRepository = HotelActionsRepository()
    val hotelNames: MutableLiveData<MutableList<String>> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData("")
    val isSuccessful: MutableLiveData<Boolean> = MutableLiveData(false)

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

    fun validateFields(email: String, hotel_name: String): Boolean{
        if (email.isEmpty()){
            errorMessage.value = "The email field is empty!"
            return false
        }else {
            if (hotel_name == "Choose Hotel" || hotel_name.isEmpty()){
                errorMessage.value = "Choose hotel!"
                return false
            }else {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    errorMessage.value = ""
                    return true
                }else {
                    errorMessage.value = "Email format is wrong!"
                    return false
                }
            }
        }
    }

    fun setHotelOwner(token: String, body: HotelOwner){
        viewModelScope.launch {
            val response = hotelRepository.setHotelOwner("Bearer $token", body)

            if (response.isSuccessful){
                isSuccessful.value = true
            }else {
                errorMessage.value = "Error ${response.code()} - ${response.message()}"
                isSuccessful.value = false
            }
        }
    }
}