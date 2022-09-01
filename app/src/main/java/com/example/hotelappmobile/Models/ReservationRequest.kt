package com.example.hotelappmobile.Models

import com.google.gson.annotations.SerializedName

data class ReservationRequest(
    @SerializedName("reservation_type")
    val reservationType: String,
    @SerializedName("room_type")
    val roomType: String,
    @SerializedName("room_number")
    val roomNumber: String,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("additional_service")
    val additionalService: String,
    @SerializedName("client_email")
    val clientEmail: String,
    val hotel_id: String

)
