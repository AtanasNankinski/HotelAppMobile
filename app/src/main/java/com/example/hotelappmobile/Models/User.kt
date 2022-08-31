package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("name")
    val userName: String,
    val email: String,
    @SerializedName("user_type")
    val userType: Int,
)
