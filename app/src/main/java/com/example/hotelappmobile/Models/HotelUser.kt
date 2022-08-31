package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class HotelUser(
    @SerializedName("name")
    val userName: String,
    val email: String,
    val password: String,
    @SerializedName("user_type")
    val userType: Int,
    val hotel_name: String
)
