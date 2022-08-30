package com.example.hotelappmobile.Repositories

import com.example.hotelappmobile.API.RetrofitInstance
import com.example.hotelappmobile.Models.Hotel

class HotelActionsRepository {
    suspend fun getHotels(): List<Hotel> {
        return RetrofitInstance.hotelActionsApi.getHotels()
    }
}