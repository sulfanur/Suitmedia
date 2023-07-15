package com.example.suitmediaapp.data.network

import com.example.suitmediaapp.data.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUser(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
    ): UserResponse
}