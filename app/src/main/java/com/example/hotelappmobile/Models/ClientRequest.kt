package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class ClientRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    @SerializedName("receptionist_id")
    val receptionistId: Int
)
