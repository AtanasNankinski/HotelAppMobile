package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class ReceptionistCall(
    @SerializedName("name")
    val userName: String,
    val email: String,
    val password: String,
    @SerializedName("manager_id")
    val managerId: String
)
