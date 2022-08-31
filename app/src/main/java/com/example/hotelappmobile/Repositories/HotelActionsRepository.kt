package com.example.hotelappmobile.Repositories

import com.example.hotelappmobile.API.RetrofitInstance
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Models.ResponseMessage
import retrofit2.Response

class HotelActionsRepository {
    suspend fun getHotels(token: String): Response<List<Hotel>> {
        return RetrofitInstance.hotelActionsApi.getHotels(token)
    }

    suspend fun addHotel(token: String, hotel: Hotel): Response<ResponseMessage> {
        return RetrofitInstance.hotelActionsApi.addHotel(token, hotel)
    }
}