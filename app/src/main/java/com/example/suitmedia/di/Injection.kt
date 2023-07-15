package com.example.suitmediaapp.di

import android.content.Context
import com.example.suitmedia.data.repository.UserRepository
import com.example.suitmediaapp.data.database.UserDatabase
import com.example.suitmediaapp.data.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}