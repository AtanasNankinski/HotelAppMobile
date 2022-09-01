package com.example.hotelappmobile.API

import com.example.hotelappmobile.API.APIConstants.Companion.HEADER_AUTH
import com.example.hotelappmobile.Models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPI {
    @POST("login")
    suspend fun login(
        @Body body: LoginBody
    ): Response<LoginResponse>

    @POST("logout")
    suspend fun logout(@Header(HEADER_AUTH) token: String): Response<Any>

    @POST("register_owner")
    suspend fun registerOwner(@Header(HEADER_AUTH) token: String, @Body body: HotelUser): Response<Any>

    @POST("register_manager")
    suspend fun registerManager(@Header(HEADER_AUTH) token: String, @Body body: HotelUser): Response<Any>

    @POST("register_receptionist")
    suspend fun registerReceptionist(@Header(HEADER_AUTH) token: String, @Body body: ReceptionistCall): Response<Any>
}