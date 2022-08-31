package com.example.hotelappmobile.API

import com.example.hotelappmobile.Models.LoginBody
import com.example.hotelappmobile.Models.LoginResponse
import com.example.hotelappmobile.Models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("login")
    suspend fun login(
        @Body body: LoginBody
    ): Response<LoginResponse>
}