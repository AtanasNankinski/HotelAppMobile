package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class Client(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val hotel: Int
)
