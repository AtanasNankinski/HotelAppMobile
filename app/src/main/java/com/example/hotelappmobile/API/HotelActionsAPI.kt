package com.example.hotelappmobile.API

import com.example.hotelappmobile.Models.Hotel
import retrofit2.Response
import retrofit2.http.GET

interface HotelActionsAPI {
    @GET("hotels")
    suspend fun getHotels(): Response<List<Hotel>>
}