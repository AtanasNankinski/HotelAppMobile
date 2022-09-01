package com.example.hotelappmobile.Repositories

import com.example.hotelappmobile.API.RetrofitInstance
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Models.HotelOwner
import com.example.hotelappmobile.Models.ResponseMessage
import retrofit2.Response

class HotelActionsRepository {
    suspend fun getHotels(token: String): Response<List<Hotel>> {
        return RetrofitInstance.hotelActionsApi.getHotels(token)
    }

    suspend fun getHotelsByOwner(token: String, owner: Int): Response<List<Hotel>>{
        return RetrofitInstance.hotelActionsApi.getHotelsByOwner(token, owner)
    }

    suspend fun getHotelByManager(token: String, manager: Int): Response<Hotel> {
        return RetrofitInstance.hotelActionsApi.getHotelByManager(token, manager)
    }

    suspend fun addHotel(token: String, hotel: Hotel): Response<ResponseMessage> {
        return RetrofitInstance.hotelActionsApi.addHotel(token, hotel)
    }

    suspend fun setHotelOwner(token: String, owner: HotelOwner): Response<ResponseMessage>{
        return RetrofitInstance.hotelActionsApi.setHotelOwner(token, owner)
    }
}