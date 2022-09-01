package com.example.hotelappmobile.API

import com.example.hotelappmobile.API.APIConstants.Companion.HEADER_AUTH
import com.example.hotelappmobile.Models.*
import retrofit2.Response
import retrofit2.http.*

interface HotelActionsAPI {
    @GET("hotels")
    suspend fun getHotels(
        @Header(HEADER_AUTH) token: String
    ): Response<List<Hotel>>

    @GET("get_hotels_owner/{owner_id}")
    suspend fun getHotelsByOwner(
        @Header(HEADER_AUTH) token: String,
        @Path("owner_id") owner: Int
    ): Response<List<Hotel>>

    @GET("get_hotels_manager/{manager_id}")
    suspend fun getHotelByManager(
        @Header(HEADER_AUTH) token: String,
        @Path("manager_id") manager: Int
    ): Response<Hotel>

    @GET("get_hotel_receptionist/{receptionist_id}")
    suspend fun getHotelByReceptionist(
        @Header(HEADER_AUTH) token: String,
        @Path("receptionist_id") receptionistId: Int
    ): Response<Hotel>

    @POST("add_hotel")
    suspend fun addHotel(
        @Header(HEADER_AUTH) token: String,
        @Body hotel: Hotel
    ): Response<ResponseMessage>

    @POST("create_client")
    suspend fun createClient(
        @Header(HEADER_AUTH) token: String,
        @Body client: ClientRequest
    ): Response<ResponseMessage>

    @POST("create_reservation")
    suspend fun createReservation(
        @Header(HEADER_AUTH) token: String,
        @Body reservation: ReservationRequest
    ): Response<ResponseMessage>

    @PUT("set_hotel_owner")
    suspend fun setHotelOwner(
        @Header(HEADER_AUTH) token: String,
        @Body owner: HotelOwner
    ): Response<ResponseMessage>
}