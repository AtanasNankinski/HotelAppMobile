package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class FullUser(
    val id: Int,
    val userName: String,
    val email: String,
    val userType: Int,
    val token: String
)