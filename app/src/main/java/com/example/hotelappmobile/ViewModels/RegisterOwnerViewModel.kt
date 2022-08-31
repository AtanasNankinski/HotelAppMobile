package com.example.hotelappmobile.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelappmobile.Models.FullUser
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Repositories.HotelActionsRepository
import kotlinx.coroutines.launch

class RegisterOwnerViewModel : ViewModel() {
    val repository: HotelActionsRepository = HotelActionsRepository()
    val hotelNames: MutableLiveData<MutableList<String>> = MutableLiveData()

    fun getHotels(viewModel: MainViewModel){
        val hotelNamesTemp: MutableList<String> = mutableListOf()

        viewModelScope.launch {
            val response = repository.getHotels("Bearer ${viewModel.user.value!!.token}")

            if (response.isSuccessful){
                val hotels: List<Hotel> = response.body()!!

                for (hotel in hotels){
                    hotelNamesTemp.add(hotel.hotelName)
                }
                hotelNames.value = hotelNamesTemp
            }
        }
    }
}