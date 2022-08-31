package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class Hotel(
    val id: Int?,
    @SerializedName("hotel_name")
    val hotelName: String,
    val manager: Int?,
    val owner: Int?,
    val receptionist: Int?
)
