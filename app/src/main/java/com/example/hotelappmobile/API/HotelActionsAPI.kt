package com.example.hotelappmobile.API

import com.example.hotelappmobile.Models.Hotel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HotelActionsAPI {
    @GET("hotels")
    suspend fun getHotels(@Header("Authorization") token: String): Response<List<Hotel>>
}