package com.example.hotelappmobile.API

import com.example.hotelappmobile.Models.Hotel
import retrofit2.http.GET

interface HotelActionsAPI {
    @GET("hotels")
    suspend fun getHotels(): List<Hotel>
}