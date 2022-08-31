package com.example.hotelappmobile.API

import com.example.hotelappmobile.API.APIConstants.Companion.HEADER_AUTH
import com.example.hotelappmobile.Models.Hotel
import com.example.hotelappmobile.Models.HotelOwner
import com.example.hotelappmobile.Models.ResponseMessage
import retrofit2.Response
import retrofit2.http.*

interface HotelActionsAPI {
    @GET("hotels")
    suspend fun getHotels(@Header(HEADER_AUTH) token: String): Response<List<Hotel>>

    @POST("add_hotel")
    suspend fun addHotel(@Header(HEADER_AUTH) token: String, @Body hotel: Hotel): Response<ResponseMessage>

    @PUT("set_hotel_owner")
    suspend fun setHotelOwner(@Header(HEADER_AUTH) token: String, @Body owner: HotelOwner): Response<ResponseMessage>
}