package com.example.hotelappmobile.Repositories

import com.example.hotelappmobile.API.RetrofitInstance
import com.example.hotelappmobile.Models.*
import retrofit2.Response

class AuthRepository {
    suspend fun login(body: LoginBody): Response<LoginResponse> {
        return RetrofitInstance.authApi.login(body)
    }

    suspend fun logout(token: String): Response<Any> {
        return RetrofitInstance.authApi.logout(token)
    }

    suspend fun registerOwner(token: String, body: HotelUser): Response<Any>{
        return RetrofitInstance.authApi.registerOwner(token, body)
    }

    suspend fun registerManager(token: String, body: HotelUser): Response<Any>{
        return RetrofitInstance.authApi.registerManager(token, body)
    }

    suspend fun registerReceptionist(token: String, body: ReceptionistCall): Response<Any>{
        return RetrofitInstance.authApi.registerReceptionist(token, body)
    }
}