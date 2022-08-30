package com.example.hotelappmobile.API

import retrofit2.Retrofit
import com.example.hotelappmobile.API.APIConstants.Companion.API_URL
import com.example.hotelappmobile.API.APIConstants.Companion.API_URL_IP
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_URL_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val hotelActionsApi: HotelActionsAPI by lazy {
        retrofit.create(HotelActionsAPI::class.java)
    }
}