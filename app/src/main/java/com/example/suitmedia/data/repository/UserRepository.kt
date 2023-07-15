package com.example.suitmedia.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.suitmediaapp.data.UserResponseItem
import com.example.suitmediaapp.data.database.UserDatabase
import com.example.suitmediaapp.data.network.ApiService


@OptIn(ExperimentalPagingApi::class)
class UserRepository(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    fun getUser(): LiveData<PagingData<UserResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4
            ),
            remoteMediator = RemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
                userDatabase.userDao().getAllUser()
            }
        ).liveData
    }
}