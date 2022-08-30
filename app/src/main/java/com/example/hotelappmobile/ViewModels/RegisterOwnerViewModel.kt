package com.example.hotelappmobile.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch
import java.lang.Error

class RegisterOwnerViewModel : ViewModel() {
    val repository: HotelActionsRepository = HotelActionsRepository()
    val hotelNames: MutableLiveData<MutableList<String>> = MutableLiveData()

    fun getHotels(){
        val hotelNamesTemp: MutableList<String> = mutableListOf()

        viewModelScope.launch {
            val hotels: List<Hotel> = repository.getHotels()

            for (hotel in hotels){
                hotelNamesTemp.add(hotel.hotelName)
            }
            hotelNames.value = hotelNamesTemp
        }
    }
}