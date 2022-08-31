package com.example.hotelappmobile.Repositories

import com.example.hotelappmobile.API.RetrofitInstance
import com.example.hotelappmobile.Models.Hotel
import retrofit2.Response

class HotelActionsRepository {
    suspend fun getHotels(): Response<List<Hotel>> {
        return RetrofitInstance.hotelActionsApi.getHotels()
    }
}